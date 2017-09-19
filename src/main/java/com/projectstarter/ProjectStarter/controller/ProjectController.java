package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentsDto;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.payment.PaymentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.dto.rewards.RewardsDto;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CONFIRMED_USER')")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public ProjectDto update(
            @RequestBody final ProjectDto projectDto
    ) {
        return projectService.update(projectDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/createNews")
    @ResponseStatus(value = HttpStatus.OK)
    public NewsDto createNews(
            @RequestBody final NewsDto newsDto,
            HttpServletRequest request
    ) {
        return projectService.createNews(newsDto, request);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CONFIRMED_USER')")
    @PostMapping(value = "/createReward")
    @ResponseStatus(value = HttpStatus.OK)
    public RewardsDto createReward(
            @RequestBody final RewardsDto rewardsDto,
            HttpServletRequest request
    ) {
        return projectService.createReward(rewardsDto, request);
    }

    @GetMapping(value = "/news")
    @ResponseStatus(value = HttpStatus.OK)
    public List<NewsDto> findNews(@RequestParam("project_id") Long projectId) {
        return projectService.findNewsByProjectId(projectId);
    }


    @GetMapping(value = "/comments")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentsDto> findComments(@RequestParam("project_id") Long projectId) {
        return projectService.findCommentsByProjectId(projectId);
    }

    @GetMapping(value = "/rewards")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RewardsDto> findRewards(@RequestParam("project_id") Long projectId) {
        return projectService.findRewardsByProjectId(projectId);
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @PostMapping(value = "/subscribe")
    @ResponseStatus(value = HttpStatus.OK)
    public SubscribeResponseDto subscribe(
            @RequestBody final SubscribeRequestDto subscribeRequestDto
    ) {
        return projectService.subscribe(subscribeRequestDto);
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @PostMapping(value = "/addComment")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean addComment(
            @RequestBody final CommentRequestDto commentRequestDto
    ) {
        return projectService.addComment(commentRequestDto);
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/subscription")
    @ResponseStatus(value = HttpStatus.OK)
    public SubscribeResponseDto subscription(
            @RequestParam("user_id") Long userId,
            @RequestParam("project_id") Long projectId
    ) {
        return projectService.subscription(userId, projectId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WAIT_CONFIRM', 'ROLE_CONFIRMED_USER', 'ROLE_ADMIN')")
    @PostMapping(value = "/payment")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean payment(
            @RequestBody final PaymentRequestDto paymentRequestDto
    ) {
        return projectService.pay(paymentRequestDto);
    }
}

