package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectDto;
import com.projectstarter.ProjectStarter.service.transformer.ProjectTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTransformer projectTransformer;
    private final UserService userService;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project saveAndFlush(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public ProjectCreateResponseDto create(ProjectCreateRequestDto projectCreateRequestDto) {
        Project project = new Project();

        User user = new User();
        user.setId(projectCreateRequestDto.getUserId());
        project.setUser(user);

        project.setTitle(projectCreateRequestDto.getTitle());
        project.setStartDate(new Date((new java.util.Date()).getTime()));
        project.setStatus(ProjectStatus.IN_PROGRESS);

        project = projectRepository.saveAndFlush(project);

        return new ProjectCreateResponseDto(project.getId());
    }

    public ProjectDto findProject(Long projectId) {
        Project project = projectRepository.findById(projectId);
        return projectTransformer.makeDto(project);
    }
}
