package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author i.katlinsky
 * @since 22.07.2016
 */
@Getter
@Setter
public class AuthUserDto implements Dto {
    private long id;
    private String username;
    private String role;
}
