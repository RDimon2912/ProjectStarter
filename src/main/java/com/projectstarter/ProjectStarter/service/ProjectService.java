package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.*;
import com.projectstarter.ProjectStarter.model.DonateSystem;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.*;
import com.projectstarter.ProjectStarter.repository.fulltextsearch.FullTextReposiroty;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.service.dto.*;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentsDto;
import com.projectstarter.ProjectStarter.service.dto.goal.GoalDto;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.payment.PaymentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectListDto;
import com.projectstarter.ProjectStarter.service.dto.rewards.RewardsDto;
import com.projectstarter.ProjectStarter.service.dto.subscribe.SubscribeRequestDto;
import com.projectstarter.ProjectStarter.service.dto.subscribe.SubscribeResponseDto;
import com.projectstarter.ProjectStarter.service.transformer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private static final String DEAULT_IMAGE_PROPERTY = "default.image";

    private final JavaMailSender mailSender;
    private final Environment environment;

    private final FullTextReposiroty fullTextReposiroty;
    private final ProjectRepository projectRepository;
    private final NewsRepository newsRepository;
    private final GoalRepository goalRepository;
    private final SubscribeRepository subscribeRepository;
    private final CommentRepository commentRepository;
    private final DonateSystemRepository donateSystemRepository;
    private final DonateRepository donateRepository;
    private final UserRepository userRepository;

    private final ProjectTransformer projectTransformer;
    private final NewsTransformer newsTransformer;
    private final GoalTransformer goalTransformer;
    private final CommentTransformer commentTransformer;
    private final SubscriptionTransformer subscriptionTransformer;
    private final RewardTransformer rewardTransformer;
    private final DonateTransformer donateTransformer;

    private final ProjectListTransformer projectListTransformer;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProjectListDto> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectListDto> projectListDto = new ArrayList<>();

        for (Project project : projects) {
            ProjectListDto dto = this.projectListTransformer.makeDto(project);
            projectListDto.add(dto);
        }

        return projectListDto;
    }

    public ProjectCreateResponseDto create(ProjectCreateRequestDto projectCreateRequestDto) {
        Project project = new Project();

        User user = new User();
        user.setId(projectCreateRequestDto.getUserId());
        project.setUser(user);

        project.setTitle(projectCreateRequestDto.getTitle());
        project.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        project.setEndDate(projectCreateRequestDto.getEndDate());
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setTargetAmount(projectCreateRequestDto.getTargetAmount());
        project.setImageUrl(environment.getProperty(DEAULT_IMAGE_PROPERTY));

        project = projectRepository.saveAndFlush(project);

        return new ProjectCreateResponseDto(project.getId());
    }

    @Transactional(readOnly = true)
    public ProjectDto findProject(Long projectId) {
        Project project = projectRepository.findById(projectId);
        return projectTransformer.makeDto(project);
    }

    @Transactional(readOnly = true)
    public List<GoalDto> findGoalsByProjectId(Long projectId) {
        List<Goal> goalList = goalRepository.findAllByProjectId(projectId);
        List<GoalDto> goalDtoList = new ArrayList<>();
        for (Goal goal: goalList) {
            goalDtoList.add(goalTransformer.makeDto(goal));
        }
        return goalDtoList;
    }

    @Transactional(readOnly = true)
    public List<NewsDto> findNewsByProjectId(Long projectId) {
        List<News> newsList = newsRepository.findAllByProjectIdOrderByDateDesc(projectId);
        List<NewsDto> newsDtoList = new ArrayList<>();
        for (News news: newsList) {
            newsDtoList.add(newsTransformer.makeDto(news));
        }
        return newsDtoList;
    }

    public List<CommentsDto> findCommentsByProjectId(Long projectId) {
        List<Comments> commentsList = commentRepository.findAllByProjectIdOrderByDateDesc(projectId);
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (Comments comment: commentsList) {
            commentsDtoList.add(commentTransformer.makeDto(comment));
        }
        return commentsDtoList;
    }

    public List<RewardsDto> findRewardsByProjectId(Long projectId) {
        List<DonateSystem> rewardsList = donateSystemRepository.findAllByProjectId(projectId);
        List<RewardsDto> rewardsDtoList = new ArrayList<>();
        for (DonateSystem reward: rewardsList) {
            rewardsDtoList.add(rewardTransformer.makeDto(reward));
        }
        return rewardsDtoList;
    }

    public ProjectDto update(ProjectDto projectDto) {
        checkIsFrontUserServerUser(
                Role.ROLE_CONFIRMED_USER,
                projectDto.getUserId(),
                "You don't have permission for editing this project."
        );

        Project project = projectTransformer.makeObject(projectDto);
        project = projectRepository.saveAndFlush(project);

        return projectTransformer.makeDto(project);
    }

    public NewsDto createNews(NewsDto newsDto, HttpServletRequest request) {
        checkIsFrontUserServerUser(
                Role.ROLE_CONFIRMED_USER,
                projectRepository.findById(newsDto.getProjectId()).getUser().getId(),
                "You don't have permission for adding news to this project."
                );

        News news = newsTransformer.makeObject(newsDto);
        news.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        news = newsRepository.saveAndFlush(news);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";
        sendNewsToSubscribedUsers(news, appUrl);

        return newsTransformer.makeDto(news);
    }

    public RewardsDto createReward(RewardsDto rewardsDto, HttpServletRequest request) {
        checkIsFrontUserServerUser(
                Role.ROLE_CONFIRMED_USER,
                projectRepository.findById(rewardsDto.getProjectId()).getUser().getId(),
                "You don't have permission for adding news to this project."
        );
        DonateSystem donateSystem = donateTransformer.makeObjectDS(rewardsDto);
        donateSystem = donateSystemRepository.saveAndFlush(donateSystem);
        return donateTransformer.makeDto(donateSystem);
    }

    private void sendNewsToSubscribedUsers(News news, String appUrl) {
        List<Subscription> subscriptions = subscribeRepository.findAllByProjectId(news.getProject().getId());
        List<Subscription> goodSubscriptions = new ArrayList<>();
        for (Subscription subscription: subscriptions) {
            goodSubscriptions.add(subscriptionTransformer.copyForSendingEmail(subscription));
        }
        createSendThreads(goodSubscriptions, appUrl);
    }

    private void createSendThreads(List<Subscription> subscriptions, String appUrl) {
        for (Subscription subscription: subscriptions) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendEmail(subscription.getUser(), subscription.getProject(), appUrl);
                }
            }).start();
        }
    }

    private void sendEmail(User user, Project project, String appUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(user.getEmail());
            helper.setSubject("News on subscribed project");
            helper.setText("Hi " + user.getBiography().getName() + ",\n\n" +
                    "Project \'" + project.getTitle() + "\' has news for you. Click link below and check it.\n" +
                    appUrl + "/project-info/" + project.getId() + "\n\n" +
                    "Kind regards,\nTeam ProjectStarter");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public GoalDto createGoal(GoalDto goalDto) {
        checkIsFrontUserServerUser(
                Role.ROLE_CONFIRMED_USER,
                projectRepository.findById(goalDto.getProjectId()).getUser().getId(),
                "You don't have permission for adding goals to this project."
        );

        Goal goal = goalTransformer.makeObject(goalDto);
        goal = goalRepository.saveAndFlush(goal);

        return goalTransformer.makeDto(goal);
    }

    public SubscribeResponseDto subscribe(SubscribeRequestDto subscribeRequestDto) {
        boolean isSubscribed;

        if (subscribeRequestDto.isNeedToSubscribe()) {
            Subscription subscription = subscriptionTransformer.makeObject(subscribeRequestDto);
            subscribeRepository.save(subscription);
            isSubscribed = true;
        } else {
            subscribeRepository.deleteByUserIdAndProjectId(
                    subscribeRequestDto.getUserId(),
                    subscribeRequestDto.getProjectId()
            );
            isSubscribed = false;
        }

        return new SubscribeResponseDto(isSubscribed);
    }

    @Transactional(readOnly = true)
    public SubscribeResponseDto subscription(Long userId, Long projectId) {
        Subscription subscription = subscribeRepository.findFirstByUserIdAndProjectId(userId, projectId);
        return new SubscribeResponseDto(subscription != null);
    }

    private void checkIsFrontUserServerUser(Role role, Long userId, String errorMessage) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (GrantedAuthority authoritie : userDetails.getAuthorities()) {
            if (authoritie.getAuthority().equals(role.name()) &&
                    userDetails.getId() != userId) {
                throw new JsonException(errorMessage);
            }
        }
    }

    public boolean addComment(CommentRequestDto commentRequestDto) {
        Comments comment = commentTransformer.makeObject(commentRequestDto);
        commentRepository.save(comment);
        return true;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findLastCreatedProjects() {
        List<Project> projectList = projectRepository.findAllOrderByStartDateDescLimitN(8);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project: projectList) {
            projectDtoList.add(projectTransformer.makeDto(project));
        }
        return projectDtoList;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findSuccessfullyFinancedProjects() {
        List<Project> projectList = projectRepository.findAllByStatusNameOrderByEndDateDescLimitN(
                ProjectStatus.FINANCED.name(), 8);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project: projectList) {
            projectDtoList.add(projectTransformer.makeDto(project));
        }
        return projectDtoList;
    }

    public boolean pay(PaymentRequestDto paymentRequestDto) {
        Donate donate = donateTransformer.makeObject(paymentRequestDto);
        donateRepository.save(donate);
        return true;
    }

    public List<ProjectDto> search(String requestString, int offset) {
        List<Project> projectList = fullTextReposiroty.fullTextSearch(requestString, offset);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project: projectList) {
            projectDtoList.add(projectTransformer.makeDto(project));
        }
        return projectDtoList;
    }
}
