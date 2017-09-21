package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.DonateService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.donate.DonateDto;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/donate", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DonateController {
    private final DonateService donateService;
    private final UserService userService;

    @GetMapping(value = "/the-biggest")
    @ResponseStatus(value = HttpStatus.OK)
    public List<DonateDto> findTheBiggestDonations() {
        return donateService.findTheBiggestDonations();
    }

    @GetMapping(value = "/user-biography")
    @ResponseStatus(value = HttpStatus.OK)
    public BiographyDto findUserBiography(
            @RequestParam("user_id") Long userId
    ) {
        return userService.findUserBiography(userId);
    }
}
