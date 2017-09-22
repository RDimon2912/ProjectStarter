package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.*;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.*;
import com.projectstarter.ProjectStarter.service.dto.achievements.AchievementDto;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import com.projectstarter.ProjectStarter.service.transformer.AchievementTransformer;
import com.projectstarter.ProjectStarter.service.transformer.BiographyTransformer;
import com.projectstarter.ProjectStarter.service.transformer.ProjectTransformer;
import com.projectstarter.ProjectStarter.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.sql.Date;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BiographyRepository biographyRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementTransformer achievementTransformer;
    private final UserListTransformer userListTransformer;
    private final BiographyTransformer biographyTransformer;
    private final CreatorRepository creatorRepository;

    private final ProjectRepository projectRepository;
    private final ProjectTransformer projectTransformer;

    private final SubscribeRepository subscribeRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional()
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRegistrationDate() == null) {
            user.setRegistrationDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        }
        user = userRepository.saveAndFlush(user);
        Biography biography = biographyRepository.findById(user.getBiography().getId());
        biography.setUser(user);
        biographyRepository.save(biography);
    }

    @Transactional()
    public void confirm(User user) {
        user.setConfirmed(true);
        userRepository.save(user);
    }

    public User create(String name, String password, String email) {
        User newUser = new User();

        newUser.setPassword(password);
        newUser.setRole(Role.ROLE_USER);
        newUser.setEmail(email);
        newUser.setBlockStatus(BlockStatus.ACTIVE);
        newUser.setConfirmed(false);

        Biography biography = new Biography();
        biography.setName(name);
        biographyRepository.save(biography);
        newUser.setBiography(biography);
        return newUser;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserListDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserListDto dto = this.userListTransformer.makeDto(user);
            userDtoList.add(dto);
        }

        return userDtoList;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public BiographyDto findUserInfo(long id) {
        User user = userRepository.findById(id);
        Biography biography = user.getBiography();
        if (biography != null){
            BiographyDto dto = this.biographyTransformer.makeDto(biography);
            return dto;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findAllUserProjects(Long userId) {
        List<Project> projectList = projectRepository.findAllByUserIdOrderByStartDateDesc(userId);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Project project: projectList) {
            projectDtoList.add(projectTransformer.makeDto(project));
        }
        return projectDtoList;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findAllSubscribedProjectsByUserId(Long userId) {
        List<Subscription> subscriptions = subscribeRepository.findAllByUserId(userId);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for (Subscription subscription: subscriptions) {
            projectDtoList.add(projectTransformer.makeDto(
                    projectRepository.findById(subscription.getProject().getId())
            ));
        }
        return projectDtoList;
    }

    @Transactional()
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean setWaitingRole(String email, String image) {
        User user = userRepository.findByEmail(email);
        user.setRole(Role.ROLE_WAIT_CONFIRM);
        CreatorRequest creatorRequest = new CreatorRequest();
        creatorRequest.setImage(image);
        creatorRequest.setUser(user);
        creatorRepository.save(creatorRequest);
        userRepository.save(user);
        return true;
    }

    public BiographyDto findUserBiography(Long userId) {
        return biographyTransformer.makeDto(biographyRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> findAllUserAchievements(Long userId) {
        List<Achievement> achievements = achievementRepository.findAllByUserId(userId);
        List<AchievementDto> achievementDtoList = new ArrayList<>();
        for (Achievement achievement:
             achievements) {
            achievementDtoList.add(achievementTransformer.makeDto(achievement));
        }
        return achievementDtoList;
    }
}
