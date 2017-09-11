package com.projectstarter.ProjectStarter.service.dto;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Dto for user list item.
 * @author d.krivenky
 * @since 27.08.2016
 */
@Getter
@Setter
public class UserListDto implements Dto {

    private String email;
    private Role role;
    private BlockStatus block_status;
    private Date registration_date;
    private Date last_log_in;
    private int amountOfProjects;

}
