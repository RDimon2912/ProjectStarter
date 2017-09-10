package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.dto.UserListDto;
import org.springframework.stereotype.Component;


@Component
public class UserListTransformer {

    public UserListDto makeDto(final User user) {
        UserListDto dto = new UserListDto();
        dto.setId(user.getId());
        dto.setUsername(user.getEmail());
        dto.setRole(user.getRole().name());

        return dto;
    }
}
