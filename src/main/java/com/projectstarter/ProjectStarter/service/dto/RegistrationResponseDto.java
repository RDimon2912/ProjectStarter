package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponseDto implements Dto {
    private String token;

    public RegistrationResponseDto(final String token) {
        this.token = token;
    }
}
