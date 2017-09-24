package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.security.model.JwtUserDetails;
import com.projectstarter.ProjectStarter.service.AdminService;
import com.projectstarter.ProjectStarter.service.ProjectService;
import com.projectstarter.ProjectStarter.service.UserService;
import com.projectstarter.ProjectStarter.service.dto.JsonException;
import com.projectstarter.ProjectStarter.service.dto.admin.*;
import com.projectstarter.ProjectStarter.service.dto.project.ProjectListDto;
import com.projectstarter.ProjectStarter.service.dto.registration.ConfirmRequestDto;
import com.projectstarter.ProjectStarter.service.dto.user.RoleDto;
import com.projectstarter.ProjectStarter.service.dto.user.SortByDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/list-of-users")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> getListOfUsers() {
        checkIsFrontUserBlocked();
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/block")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean blockSomeUsers(
            @RequestBody final BlockDto blockDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.block(blockDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/unblock")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean unblockSomeUsers(
            @RequestBody final BlockDto unblockDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.unblock(unblockDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean deleteSomeUsers(
            @RequestBody final DeleteDto deleteDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.delete(deleteDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/sort-by-role")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> sortByRole(
            @RequestBody final RoleDto role
    ) {
        checkIsFrontUserBlocked();
        return adminService.sortByRole(role.theRole);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/sort-by")
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> sortBy(
            @RequestBody final SortByDto sortByDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.sortBy(sortByDto.by, sortByDto.theRole);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/get-passport-scan")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseScanDto getPassportScan(
            @RequestBody final PassportScanDto passportScanDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.getPassportScan(passportScanDto.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/confirm")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean confirm(
            @RequestBody final ConfirmRequestDto confirmRequestDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.confirm(confirmRequestDto.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/dismiss")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean dismiss(
            @RequestBody final ConfirmRequestDto confirmRequestDto
    ) {
        checkIsFrontUserBlocked();
        return adminService.dismiss(confirmRequestDto.getEmail());
    }

    private void checkIsFrontUserBlocked() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findById(userDetails.getId()).getBlockStatus() == BlockStatus.BLOCKED) {
            throw new JsonException("You are Blocked");
        }
    }
}
