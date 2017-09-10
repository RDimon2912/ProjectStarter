package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.dto.*;
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectCreateResponseDto create(
            @RequestBody final ProjectCreateRequestDto projectCreateRequestDto
    ) {
        return projectService.create(projectCreateRequestDto);
    }
}

