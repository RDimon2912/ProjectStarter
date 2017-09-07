package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Biography;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.repository.BiographyRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.security.SecurityHelper;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.security.service.AuthenticationHelper;
import com.projectstarter.ProjectStarter.service.dto.*;
import com.projectstarter.ProjectStarter.service.transformer.AuthUserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final BiographyRepository biographyRepository;
    private final UserRepository userRepository;
    private final AuthUserTransformer authUserTransformer;
    private final AuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            String username = Optional.ofNullable(loginRequestDto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Username should be passed."));

            String password = Optional.ofNullable(loginRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed."));

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                    password);

            // Try to authenticate with this token
            final Authentication authResult = this.authenticationManager.authenticate(authRequest);

            // Set generated JWT token to response header
            if (authResult.isAuthenticated()) {
                JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();

                User user = userRepository.findOne(userDetails.getId());
                if (Objects.isNull(user)) {
                    throw new JsonException("User not exist in system.");
                }

                String token = this.authenticationHelper.generateToken(userDetails.getId());

                return new LoginResponseDto(token);
            } else {
                throw new JsonException("Authentication failed.");
            }

        } catch (BadCredentialsException exception) {
            throw new JsonException("Username or password was incorrect. Please try again.", exception);
        }
    }

    /**
     * Get user info.
     * @return user info.
     */
    @Transactional(readOnly = true)
    public AuthUserDto getMe() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        User byUsername = userRepository.findByEmail(authentication.getName());
        return authUserTransformer.makeDto(byUsername);
    }

    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {
        try {
            String username = Optional.ofNullable(registrationRequestDto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Username should be passed."));

            String password = Optional.ofNullable(registrationRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed."));

            String email = Optional.ofNullable(registrationRequestDto.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email should be passed."));

            User user = userRepository.findByEmail(email);
            if (!Objects.isNull(user)) {
                throw new JsonException("Email is already in use.");
            }

            User newUser = userService.create(username, password, email);
            userService.save(newUser);

            String token = this.authenticationHelper.
                    generateToken(userService.findByEmail(newUser.getEmail()).getId());

            return new RegistrationResponseDto(token);
        } catch (BadCredentialsException exception) {
            throw new JsonException("Unable to register. Please try again.", exception);
        }
    }
}
