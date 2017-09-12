package com.projectstarter.ProjectStarter.service.transformer;


import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
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

    public Project makeObject(final ProjectDto projectDto) {
        Project project = new Project();

        project.setId(projectDto.getId());

        User user = new User();
        user.setId(projectDto.getUserId());
        project.setUser(user);

        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setTargetAmount(projectDto.getTargetAmount());
        project.setCurrentAmount(projectDto.getCurrentAmount());
        project.setRating(projectDto.getRating());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setStatus(ProjectStatus.valueOf(projectDto.getProjectStatus()));

        return project;
    }
}
