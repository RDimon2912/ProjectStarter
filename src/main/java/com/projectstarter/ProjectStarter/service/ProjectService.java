package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project saveAndFlush(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public ProjectCreateResponseDto create(ProjectCreateRequestDto projectCreateRequestDto) {
        Project project = new Project();

        project.setTitle(projectCreateRequestDto.getTitle());

        System.out.println(project);
//        project = projectRepository.saveAndFlush(project);

        return new ProjectCreateResponseDto(1L);
//        return new ProjectCreateResponseDto(project.getId());
    }

//    public Project create(String name, String password, String email) {
//        Project newProject = new Project();
//
//        return newProject;
//    }

}
