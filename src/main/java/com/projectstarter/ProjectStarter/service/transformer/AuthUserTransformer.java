package com.projectstarter.ProjectStarter.service.transformer;


import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.dto.AuthUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserTransformer {

    public AuthUserDto makeDto(final User user) {
        AuthUserDto authUserDto = new AuthUserDto();

        authUserDto.setId(user.getId());
        authUserDto.setUsername(user.getEmail());
        authUserDto.setRole(user.getRole().name());

        return authUserDto;
    }

}
