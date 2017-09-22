package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.AuthenticationService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.achievements.AchievementDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.dto.CreatorDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import com.projectstarter.ProjectStarter.service.dto.user.ChangeUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

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

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user_projects")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectDto> userProjects(@RequestParam("user_id") Long userId) {
        return userService.findAllUserProjects(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user_achievements")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AchievementDto> userAchievements(@RequestParam("user_id") Long userId) {
        return userService.findAllUserAchievements(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/subscribed_projects")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectDto> subscribedProjects(@RequestParam("user_id") Long userId) {
        return userService.findAllSubscribedProjectsByUserId(userId);
    }

    @PostMapping(value = "/send-to-confirm")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean sendToConfirm(@RequestBody final CreatorDto creatorDto) {
        userService.setWaitingRole(creatorDto.getEmail(), creatorDto.getImage());
        return true;
    }
}
