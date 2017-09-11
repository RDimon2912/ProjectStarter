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

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

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

}
