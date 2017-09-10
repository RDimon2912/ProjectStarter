package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/create")
    @ResponseStatus(value = HttpStatus.OK)
    public LoginResponseDto create(
            @RequestBody final LoginRequestDto loginRequestDto
    ) {
        return projectService.login(loginRequestDto);
    }
}

