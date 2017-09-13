package com.projectstarter.ProjectStarter.service.dto.user;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserDto implements Dto {
    private String email;
    private String password;
    private String name;
    private String biography;
    private String location;
    private String imageurl;
}
