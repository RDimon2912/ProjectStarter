package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Biography;
import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.BiographyRepository;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.BlockDto;
import com.projectstarter.ProjectStarter.service.dto.DeleteDto;
import com.projectstarter.ProjectStarter.service.dto.UserListDto;
import com.projectstarter.ProjectStarter.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BiographyRepository biographyRepository;
    private final ProjectRepository projectRepository;
    private final UserListTransformer userListTransformer;
    private final PasswordEncoder passwordEncoder;

    @Transactional()
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User create(String username, String password, String email) {
        User newUser = new User();
        newUser.setPassword(password);
        newUser.setRole(Role.ROLE_USER);
        newUser.setEmail(email);
        newUser.setBlockStatus(BlockStatus.ACTIVE);
        Biography biography = new Biography();
        biography.setName(username);
        biographyRepository.save(biography);
        newUser.setBiography(biography);
        return newUser;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserListDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserListDto dto = this.userListTransformer.makeDto(user);
            userDtoList.add(dto);
        }

        return userDtoList;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional()
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
    public boolean delete(DeleteDto deleteDto) {
        boolean comments = deleteDto.checkboxSettings[0];
        boolean projects = deleteDto.checkboxSettings[1];
        boolean ratings = deleteDto.checkboxSettings[2];
        for (String email:
                deleteDto.emails) {
            User curUser = userRepository.findByEmail(email);
            if (projects) {
                List<Project> projectList = projectRepository.findAllByUserId(curUser.getId());
                for (Project project:
                        projectList) {
                    projectRepository.delete(project.getId());
                }
            }
            userRepository.delete(curUser);
        }
        return true;
    }
}
