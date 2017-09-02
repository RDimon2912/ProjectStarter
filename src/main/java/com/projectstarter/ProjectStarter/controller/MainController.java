package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public User findUser() {
        return userService.findUserByEmail("tsyulialisa@gmail.com");
    }

}