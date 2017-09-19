package com.projectstarter.ProjectStarter.service.dto.admin;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto implements Dto, Comparable {

    private String email;
    private Role role;
    private BlockStatus block_status;
    private Timestamp registration_date;
    private Timestamp last_log_in;
    private int amountOfProjects;

    @Override
    public int compareTo(Object userListDto) {
        int compareAmountOfProjects=((UserListDto)userListDto).getAmountOfProjects();
        return compareAmountOfProjects-this.amountOfProjects;
    }
}
