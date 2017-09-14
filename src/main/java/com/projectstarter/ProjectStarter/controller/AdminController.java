package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.AdminService;
import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.admin.BlockDto;
import com.projectstarter.ProjectStarter.service.dto.admin.DeleteDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectListDto;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import com.projectstarter.ProjectStarter.service.dto.user.RoleDto;
import com.projectstarter.ProjectStarter.service.dto.user.SortByDto;
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
    @Autowired
    private AdminService adminService;

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
            return adminService.block(blockDto);
    }

    @PostMapping(value = "/unblock")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean unblockSomeUsers(
            @RequestBody final BlockDto unblockDto
    ) {
        return adminService.unblock(unblockDto);
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean deleteSomeUsers(
            @RequestBody final DeleteDto deleteDto
    ) {
        return adminService.delete(deleteDto);
    }

    @PostMapping(value = "/sort-by-role")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> sortByRole(
            @RequestBody final RoleDto role
    ) {
        return adminService.sortByRole(role.theRole);
    }

    @PostMapping(value = "/sort-by")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> sortBy(
            @RequestBody final SortByDto sortByDto
    ) {
        return adminService.sortBy(sortByDto.by, sortByDto.theRole);
    }
}
