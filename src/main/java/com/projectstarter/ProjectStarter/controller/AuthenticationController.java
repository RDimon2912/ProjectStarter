package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.AuthenticationService;
import com.projectstarter.ProjectStarter.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    public LoginResponseDto login(
            @RequestBody final LoginRequestDto loginRequestDto
    ) {
        return authenticationService.login(loginRequestDto);
    }

    @PostMapping(value = "/registration")
    @ResponseStatus(value = HttpStatus.OK)
    public RegistrationResponseDto registration(
            @RequestBody final RegistrationRequestDto registrationRequestDto,
            HttpServletRequest request
    ) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";
        return authenticationService.register(registrationRequestDto, appUrl);
    }

    @PostMapping(value="/confirm")
    @ResponseStatus(value = HttpStatus.OK)
    public RegistrationResponseDto confirm(
            @RequestBody final ConfirmRequestDto confirmRequestDto
    ) {
        return authenticationService.confirm(confirmRequestDto.getEmail());
    }

    @GetMapping(value = "/me")
    @ResponseStatus(value = HttpStatus.OK)
    public AuthUserDto me() {
        return authenticationService.getMe();
    }
}
