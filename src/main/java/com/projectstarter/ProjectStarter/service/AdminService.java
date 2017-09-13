package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Comments;
import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.repository.CommentRepository;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.admin.BlockDto;
import com.projectstarter.ProjectStarter.service.dto.admin.DeleteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;


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
                    comment.setUser(userRepository.findById(52L));
                    commentRepository.save(comment);
                }
            }
            if (!projects) {
                List<Project> projectList = projectRepository.findAllByUserId(userId);
                for (Project project:
                        projectList) {
                    project.setUser(userRepository.findById(52L));
                    projectRepository.save(project);
                }
            }
            userRepository.delete(curUser);
        }
        return true;
    }
}
