package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.service.AuthenticationService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.JsonException;
import com.projectstarter.ProjectStarter.service.dto.achievements.AchievementDto;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.dto.CreatorDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import com.projectstarter.ProjectStarter.service.dto.user.ChangeUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user-info/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BiographyDto getUserInfo(@PathVariable Long id) {
        checkIsFrontUserBlocked();
        return userService.findUserInfo(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping(value = "/user-info/save-changes")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean getUserInfo(@RequestBody final ChangeUserDto changeUserDto) {
        checkIsFrontUserBlocked();
        authenticationService.checkUser(changeUserDto);
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user-projects")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectDto> userProjects(@RequestParam("user_id") Long userId) {
        checkIsFrontUserBlocked();
        return userService.findAllUserProjects(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user-achievements")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AchievementDto> userAchievements(@RequestParam("user_id") Long userId) {
        checkIsFrontUserBlocked();
        return userService.findAllUserAchievements(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user-project-news")
    @ResponseStatus(value = HttpStatus.OK)
    public List<NewsDto> findAllUserSubscribedProjectsNews(@RequestParam("user_id") Long userId) {
        checkIsFrontUserBlocked();
        return userService.findAllUserSubscribedProjectsNews(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/user-rating")
    @ResponseStatus(value = HttpStatus.OK)
    public int findUserRating( @RequestParam("project_id") Long projectId) {
        return userService.findUserRating(projectId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/subscribed-projects")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectDto> subscribedProjects(@RequestParam("user_id") Long userId) {
        checkIsFrontUserBlocked();
        return userService.findAllSubscribedProjectsByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/send-to-confirm")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean sendToConfirm(@RequestBody final CreatorDto creatorDto) {
        checkIsFrontUserBlocked();
        userService.setWaitingRole(creatorDto.getEmail(), creatorDto.getImage());
        return true;
    }

    private void checkIsFrontUserBlocked() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findById(userDetails.getId()).getBlockStatus() == BlockStatus.BLOCKED) {
            throw new JsonException("You are Blocked");
        }
    }
}
