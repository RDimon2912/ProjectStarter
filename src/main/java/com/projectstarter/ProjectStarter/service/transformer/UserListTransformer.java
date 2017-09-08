package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.service.dto.UserListDto;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
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
