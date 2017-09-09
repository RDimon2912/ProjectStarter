package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.service.dto.ProjectListDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectListTransformer {

    public ProjectListDto makeDto(final Project project) {
        ProjectListDto dto = new ProjectListDto();
        dto.setId(project.getId());
        dto.setEnd_date(project.getEndDate());
        dto.setStart_date(project.getStartDate());
        dto.setTitle(project.getTitle());
        dto.setUser_id(project.getUser().getBiography().getName());
        return dto;
    }
}

