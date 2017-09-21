package com.projectstarter.ProjectStarter.service.transformer;


import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.Tag;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.repository.TagRepository;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ProjectTransformer {
    private final TagRepository tagRepository;

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
        projectDto.setImageUrl(project.getImageUrl());
        projectDto.setTags(getTagNamesSet(project.getTags()));

        return projectDto;
    }

    private Set<String> getTagNamesSet(Set<Tag> tags) {
        Set<String> tagsNames = new HashSet<>();
        for (Tag tag: tags) {
            tagsNames.add(tag.getTagName());
        }
        return tagsNames;
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
        project.setImageUrl(projectDto.getImageUrl());
        setProjectTags(project, projectDto);

        return project;
    }

    private Set<Tag> getTagsSet(Set<String> tagsNames) {
        Set<Tag> tags = new HashSet<>();
        for (String tagName: tagsNames) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tags.add(tag);
        }
        return tags;
    }

    private void setProjectTags(Project project, ProjectDto projectDto) {
        Set<Tag> tags = getTagsSet(projectDto.getTags());
        tags = switchExistTags(tags);
        project.setTags(tags);
    }

    private Set<Tag> switchExistTags(Set<Tag> tags) {
        List<Tag> tagsDB = tagRepository.findAll();
        Set<Tag> result = new HashSet<>();
        for (Tag tag : tags) {
            boolean flag = false;
            for (Tag tagDB : tagsDB) {
                if (tag.getTagName().equals(tagDB.getTagName())) {
                    flag = true;
                    result.add(tagDB);
                    break;
                }
            }
            if (!flag) {
                result.add(tag);
            }
        }
        return result.size() == 0 ? tags : result;
    }
}
