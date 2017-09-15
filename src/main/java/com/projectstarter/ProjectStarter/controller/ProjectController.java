package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectCreateResponseDto create(
            @RequestBody final ProjectCreateRequestDto projectCreateRequestDto
    ) {
        return projectService.create(projectCreateRequestDto);
    }

    @GetMapping(value = "/{projectId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectDto findProject(@PathVariable("projectId") Long projectId) {
        return projectService.findProject(projectId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectDto update(
            @RequestBody final ProjectDto projectDto
    ) {
        return projectService.update(projectDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/createNews")
    @ResponseStatus(value = HttpStatus.OK)
    public NewsDto createNews(
            @RequestBody final NewsDto newsDto
    ) {
        return projectService.createNews(newsDto);
    }
}

