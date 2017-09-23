package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.*;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.*;
import com.projectstarter.ProjectStarter.service.dto.admin.BlockDto;
import com.projectstarter.ProjectStarter.service.dto.admin.DeleteDto;
import com.projectstarter.ProjectStarter.service.dto.admin.ResponseScanDto;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import com.projectstarter.ProjectStarter.service.dto.user.SortByDto;
import com.projectstarter.ProjectStarter.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final UserListTransformer userListTransformer;
    private final CreatorRepository creatorRepository;
    private final RatingRepository ratingRepository;
    private final JavaMailSender mailSender;

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean block(BlockDto blockDto) {
        for (String email:
                blockDto.emails) {
            User curUser = userRepository.findByEmail(email);
            curUser.setBlockStatus(BlockStatus.BLOCKED);
            userRepository.save(curUser);
        }
        return true;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean unblock(BlockDto unblockDto) {
        for (String email:
                unblockDto.emails) {
            User curUser = userRepository.findByEmail(email);
            curUser.setBlockStatus(BlockStatus.ACTIVE);
            userRepository.save(curUser);
        }
        return true;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean delete(DeleteDto deleteDto) {
        boolean comments = deleteDto.checkboxSettings[0];
        boolean projects = deleteDto.checkboxSettings[1];
        boolean ratings = deleteDto.checkboxSettings[2];
        for (String email:
                deleteDto.emails) {
            User curUser = userRepository.findByEmail(email);
            Long userId = curUser.getId();

            if (!comments) {
                List<Comments> commentsList = commentRepository.findAllByUserId(userId);
                for (Comments comment:
                        commentsList) {
                    comment.setUser(userRepository.findById(2L));
                    commentRepository.save(comment);
                }
            } else {
                List<Comments> commentsList = commentRepository.findAllByUserId(userId);
                for (Comments comment:
                        commentsList) {
                    commentRepository.delete(comment);
                }
            }
            if (!projects) {
                List<Project> projectList = projectRepository.findAllByUserId(userId);
                for (Project project:
                        projectList) {
                    project.setUser(userRepository.findById(2L));
                    projectRepository.save(project);
                }
            } else {
                List<Project> projectList = projectRepository.findAllByUserId(userId);
                for (Project project:
                        projectList) {
                    projectRepository.delete(project);
                }
            }
            if (!ratings) {
                List<Rating> ratingList = ratingRepository.findAllByUserId(userId);
                for (Rating rating:
                        ratingList) {
                    rating.setUser(null);
                    ratingRepository.save(rating);
                }
            } else {
                List<Rating> ratingList = ratingRepository.findAllByUserId(userId);
                for (Rating rating:
                        ratingList) {
                    ratingRepository.delete(rating);
                }
            }

            userRepository.delete(curUser);
        }
        return true;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> sortByRole(Role role) {
        List<UserListDto> userListDto = new ArrayList<>();
        List<User> userList;
        userList = userRepository.findAllByRoleEquals(role);
        for (User user : userList) {
            UserListDto dto = this.userListTransformer.makeDto(user);
            userListDto.add(dto);
        }
        return userListDto;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> sortBy(String by, Role role) {
        List<UserListDto> userListDto = new ArrayList<>();
        List<User> userList = null;
        if (by.equals("Registration Date")) {
            if (role != null) {
                userList = userRepository.findAllByRoleOrderByRegistrationDateDesc(role);
            }
            else {
                userList = userRepository.findAllByOrderByRegistrationDateDesc();
            }
        } else if (by.equals("Last Login")) {
            if (role != null) {
                userList = userRepository.findAllByRoleOrderByLastLogInDesc(role);
            }
            else {
                userList = userRepository.findAllByOrderByLastLogInDesc();
            }
        } else if (by.equals("Status")) {
            if (role != null) {
                userList = userRepository.findAllByRoleOrderByBlockStatus(role);
            }
            else {
                userList = userRepository.findAllByOrderByBlockStatus();
            }
        } else {
            if (role != null) {
                userList = userRepository.findAllByRoleEquals(role);
            }
            else {
                userList = userRepository.findAll();
            }
        }
        for (User user : userList) {
            UserListDto dto = this.userListTransformer.makeDto(user);
            userListDto.add(dto);
        }
        if (by.equals("Amount Of Projects")) {
            Collections.sort(userListDto);
        }
        return userListDto;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseScanDto getPassportScan(String email) {
        CreatorRequest creatorRequest = creatorRepository.findByUser(userRepository.findByEmail(email));
        ResponseScanDto responseScanDto = new ResponseScanDto();
        responseScanDto.setPassportScan(creatorRequest.getImage());
        return responseScanDto;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean confirm(String email) {
        User user = userRepository.findByEmail(email);
        user.setRole(Role.ROLE_CONFIRMED_USER);
        userRepository.save(user);
        CreatorRequest creatorRequest = creatorRepository.findByUser(user);
        creatorRepository.delete(creatorRequest);
        sendConfirmEmail(user);
        return true;
    }

    private void sendConfirmEmail(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendEmailToUser(user);
            }
        }).start();
    }

    private void sendEmailToUser(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(user.getEmail());
            helper.setSubject("Your request was confirmed");
            helper.setText("Hi " + user.getBiography().getName() + ",\n\n" +
                    "Your request to become a creator was accepted. Now you can create new projects! Congrats!!!\n" +
                    "Kind regards,\nTeam ProjectStarter");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean dismiss(String email) {
        User user = userRepository.findByEmail(email);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        CreatorRequest creatorRequest = creatorRepository.findByUser(user);
        creatorRepository.delete(creatorRequest);
        return true;
    }
}
