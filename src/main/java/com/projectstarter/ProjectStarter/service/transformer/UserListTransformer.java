package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import org.springframework.stereotype.Component;

@Component
public class UserListTransformer {

    public UserListDto makeDto(final User user) {
        UserListDto dto = new UserListDto();
        dto.setEmail(user.getEmail());
        dto.setRegistration_date(user.getRegistrationDate());
        dto.setAmountOfProjects(5);
        dto.setRole(user.getRole());
        dto.setBlock_status(user.getBlockStatus());

        return dto;
    }
}
