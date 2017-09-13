package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.AuthenticationService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.login.LoginRequestDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import com.projectstarter.ProjectStarter.service.dto.user.ChangeUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/user-info/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BiographyDto getUserInfo(@PathVariable Long id) {
        return userService.findUserInfo(id);
    }

    @PostMapping(value = "/user-info/save-changes")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean getUserInfo(@RequestBody final ChangeUserDto changeUserDto) {
        authenticationService.checkUser(changeUserDto);
        return true;
    }
}
