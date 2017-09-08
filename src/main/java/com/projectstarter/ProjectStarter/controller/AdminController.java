package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.UserService;
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

    @GetMapping(value = "/list-of-users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> getListOfUsers() {
        return userService.findAll();
    }
}
