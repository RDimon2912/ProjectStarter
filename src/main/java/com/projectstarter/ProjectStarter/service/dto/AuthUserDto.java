package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDto implements Dto {
    private long id;
    private String username;
    private String role;
    private String status;
}
