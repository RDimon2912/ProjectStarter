package com.projectstarter.ProjectStarter.service.transformer;


import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.service.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectTransformer {
    public ProjectDto makeDto(final Project project) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(project.getId());
        projectDto.setUserId(project.getUser().getId());
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        projectDto.setTargetAmount(project.getTargetAmount());
        projectDto.setCurrentAmount(project.getCurrentAmount());
        projectDto.setRating(project.getRating());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setProjectStatus(project.getStatus().name());

        return projectDto;
    }
}
