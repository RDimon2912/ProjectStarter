package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.security.SecurityHelper;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.security.service.AuthenticationHelper;
import com.projectstarter.ProjectStarter.service.dto.*;
import com.projectstarter.ProjectStarter.service.transformer.AuthUserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private JavaMailSender sender;

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthUserTransformer authUserTransformer;
    private final AuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            String email = Optional.ofNullable(loginRequestDto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Username should be passed."));

            String password = Optional.ofNullable(loginRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed."));

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email,
                    password);

            // Try to authenticate with this token
            final Authentication authResult = this.authenticationManager.authenticate(authRequest);

            // Set generated JWT token to response header
            if (authResult.isAuthenticated()) {
                JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();

                User user = userRepository.findOne(userDetails.getId());
                if (Objects.isNull(user)) {
                    throw new JsonException("User not exist in system.");
                } else if (!user.isConfirmed()) {
                    throw new JsonException("Email is not confirmed.");
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

    public RegistrationResponseDto register(
            RegistrationRequestDto registrationRequestDto,
            String appUrl
    ) {
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

            try {
                sendEmail(newUser, appUrl, token);
            } catch (SendFailedException e) {
                throw new JsonException("Email address is incorrect.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new RegistrationResponseDto(token);
        } catch (BadCredentialsException exception) {
            throw new JsonException("Unable to register. Please try again.", exception);
        }
    }

    private void sendEmail(User user, String appUrl, String token) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(user.getEmail());
        helper.setSubject("ProjectStarter registration confirmation");
        helper.setText("Hi " + user.getBiography().getName() + ",\n\n" +
                        "To confirm your e-mail address, please click the link below:\n" +
                        appUrl + "/registration/confirm?token=" + token +
                        "&email=" + user.getEmail());
        sender.send(message);
    }

    public RegistrationResponseDto confirm(String email) {
        userService.confirm(userService.findByEmail(email));
        return new RegistrationResponseDto(email);
    }
}
