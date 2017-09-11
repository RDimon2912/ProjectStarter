package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.service.dto.ProjectListDto;
import com.projectstarter.ProjectStarter.service.transformer.ProjectListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectDto;
import com.projectstarter.ProjectStarter.service.transformer.ProjectTransformer;

import java.sql.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTransformer projectTransformer;
    private final UserService userService;

    @Autowired
    private ProjectListTransformer projectListTransformer;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProjectListDto> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectListDto> projectListDto = new ArrayList<>();

        for (Project project : projects) {
            ProjectListDto dto = this.projectListTransformer.makeDto(project);
            projectListDto.add(dto);
        }

        return projectListDto;
    }



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

    public ProjectDto update(ProjectDto projectDto) {
        Project project = projectTransformer.makeObject(projectDto);

        project = projectRepository.saveAndFlush(project);

        return projectTransformer.makeDto(project);
    }
}
