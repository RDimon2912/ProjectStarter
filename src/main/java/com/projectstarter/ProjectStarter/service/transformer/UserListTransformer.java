package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.service.dto.admin.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListTransformer {

    @Autowired
    private ProjectRepository projectRepository;

    public UserListDto makeDto(final User user) {
        UserListDto dto = new UserListDto();
        dto.setEmail(user.getEmail());
        dto.setRegistration_date(user.getRegistrationDate());
        dto.setLast_log_in(user.getLastLogIn());
        dto.setAmountOfProjects(projectRepository.countProjectByUser(user));
        dto.setRole(user.getRole());
        dto.setBlock_status(user.getBlockStatus());

        return dto;
    }

}
