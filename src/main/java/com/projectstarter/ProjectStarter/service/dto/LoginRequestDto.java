package com.projectstarter.ProjectStarter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author i.katlinsky
 * @since 21.07.2016
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto implements Dto {
    private String username;
    private String password;
}
