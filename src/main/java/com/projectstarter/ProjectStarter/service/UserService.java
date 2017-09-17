package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Biography;
import com.projectstarter.ProjectStarter.model.CreatorRequest;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.BiographyRepository;
import com.projectstarter.ProjectStarter.repository.CreatorRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.service.dto.JsonException;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import com.projectstarter.ProjectStarter.service.dto.login.LoginRequestDto;
import com.projectstarter.ProjectStarter.service.dto.login.LoginResponseDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import com.projectstarter.ProjectStarter.service.dto.user.ChangeUserDto;
import com.projectstarter.ProjectStarter.service.transformer.BiographyTransformer;
import com.projectstarter.ProjectStarter.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.sql.Date;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BiographyRepository biographyRepository;
    private final UserListTransformer userListTransformer;
    private final PasswordEncoder passwordEncoder;
    private final BiographyTransformer biographyTransformer;
    private final CreatorRepository creatorRepository;

    @Transactional()
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRegistrationDate() == null) {
            user.setRegistrationDate(new Date(Calendar.getInstance().getTime().getTime()));
        }
        userRepository.save(user);
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

    @Transactional()
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean setWaitingRole(String email, String image) {
        User user = userRepository.findByEmail(email);
        user.setRole(Role.ROLE_WAIT_CONFIRM);
        CreatorRequest creatorRequest = new CreatorRequest();
        creatorRequest.setImage(image);
        creatorRequest.setUser(user.getId());
        creatorRepository.save(creatorRequest);
        userRepository.save(user);
        return true;
    }
}
