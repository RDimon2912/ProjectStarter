package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.BlockDto;
import com.projectstarter.ProjectStarter.service.dto.DeleteDto;
import com.projectstarter.ProjectStarter.service.dto.ProjectListDto;
import com.projectstarter.ProjectStarter.service.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/list-of-users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> getListOfUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/list-of-projects")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectListDto> getListOfProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping(value = "/block")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean blockSomeUsers(
            @RequestBody final BlockDto blockDto
    ) {
            return userService.block(blockDto);
    }

    @PostMapping(value = "/unblock")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean unblockSomeUsers(
            @RequestBody final BlockDto unblockDto
    ) {
        return userService.unblock(unblockDto);
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean deleteSomeUsers(
            @RequestBody final DeleteDto deleteDto
    ) {
        return userService.delete(deleteDto);
    }
}
