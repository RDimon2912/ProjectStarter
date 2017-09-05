package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Dto for user list item.
 * @author d.krivenky
 * @since 27.08.2016
 */
@Getter
@Setter
public class UserListDto implements Dto {

    private long id;
    private String username;
    private String role;

}
