package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.dto.subscribe.SubscribeRequestDto;
import com.projectstarter.ProjectStarter.service.dto.subscribe.SubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping(value = "/info")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectDto findProject(@RequestParam("project_id") Long projectId) {
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
            @RequestBody final NewsDto newsDto,
            HttpServletRequest request
    ) {
        return projectService.createNews(newsDto, request);
    }

    @GetMapping(value = "/news")
    @ResponseStatus(value = HttpStatus.OK)
    public List<NewsDto> findNews(@RequestParam("project_id") Long projectId) {
        return projectService.findNewsByProjectId(projectId);
    }

    @PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
    @PostMapping(value = "/subscribe")
    @ResponseStatus(value = HttpStatus.OK)
    public SubscribeResponseDto subscribe(
            @RequestBody final SubscribeRequestDto subscribeRequestDto
    ) {
        return projectService.subscribe(subscribeRequestDto);
    }

    @PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
    @GetMapping(value = "/subscription")
    @ResponseStatus(value = HttpStatus.OK)
    public SubscribeResponseDto subscription(
            @RequestParam("user_id") Long userId,
            @RequestParam("project_id") Long projectId
    ) {
        return projectService.subscription(userId, projectId);
    }
}

