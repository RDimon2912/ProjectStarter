package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.DonateService;
import com.projectstarter.ProjectStarter.service.dto.donate.DonateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/donate", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DonateController {
    private final DonateService donateService;

    @GetMapping(value = "/the-biggest")
    @ResponseStatus(value = HttpStatus.OK)
    public List<DonateDto> findTheBiggestDonations() {
        return donateService.findTheBiggestDonations();
    }
}
