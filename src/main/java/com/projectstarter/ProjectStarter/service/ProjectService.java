package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.News;
import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.NewsRepository;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.service.dto.*;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectListDto;
import com.projectstarter.ProjectStarter.service.transformer.NewsTransformer;
import com.projectstarter.ProjectStarter.service.transformer.ProjectListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateRequestDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectCreateResponseDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.transformer.ProjectTransformer;

import java.sql.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final NewsRepository newsRepository;

    private final ProjectTransformer projectTransformer;
    private final NewsTransformer newsTransformer;
    private final UserService userService;

    @Autowired
    private ProjectListTransformer projectListTransformer;

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



    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project saveAndFlush(Project project) {
        return projectRepository.saveAndFlush(project);
    }

    public ProjectCreateResponseDto create(ProjectCreateRequestDto projectCreateRequestDto) {
        Project project = new Project();

        User user = new User();
        user.setId(projectCreateRequestDto.getUserId());
        project.setUser(user);

        project.setTitle(projectCreateRequestDto.getTitle());
        project.setStartDate(new Date((new java.util.Date()).getTime()));
        project.setEndDate(projectCreateRequestDto.getEndDate());
        project.setStatus(ProjectStatus.IN_PROGRESS);

        project = projectRepository.saveAndFlush(project);

        return new ProjectCreateResponseDto(project.getId());
    }

    public ProjectDto findProject(Long projectId) {
        Project project = projectRepository.findById(projectId);
        return projectTransformer.makeDto(project);
    }

    public List<NewsDto> findNewsByProjectId(Long projectId) {
        List<News> newsList = newsRepository.findAllByProjectIdOrderByDateDesc(projectId);
        List<NewsDto> newsDtoList = new ArrayList<>();
        for (News news: newsList) {
            newsDtoList.add(newsTransformer.makeDto(news));
        }
        return newsDtoList;
    }

    public ProjectDto update(ProjectDto projectDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (GrantedAuthority authoritie : userDetails.getAuthorities()) {
            if (authoritie.getAuthority().equals(Role.ROLE_CONFIRMED_USER.name()) &&
                    userDetails.getId() != projectDto.getUserId()) {
                throw new JsonException("You don't have permission for editing this project.");
            }
        }

        Project project = projectTransformer.makeObject(projectDto);

        project = projectRepository.saveAndFlush(project);

        return projectTransformer.makeDto(project);
    }

    public NewsDto createNews(NewsDto newsDto) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (GrantedAuthority authoritie : userDetails.getAuthorities()) {
            if (authoritie.getAuthority().equals(Role.ROLE_CONFIRMED_USER.name()) &&
                    userDetails.getId() != projectRepository.findById(newsDto.getProjectId()).getUser().getId()) {
                throw new JsonException("You don't have permission for adding news to this project.");
            }
        }

        News news = newsTransformer.makeObject(newsDto);
        news.setDate(new Date(Calendar.getInstance().getTime().getTime()));

        news = newsRepository.saveAndFlush(news);
        sendNewsToSubscribedUsers(news);

        return newsTransformer.makeDto(news);
    }

    public void sendNewsToSubscribedUsers(News news) {

    }
}
