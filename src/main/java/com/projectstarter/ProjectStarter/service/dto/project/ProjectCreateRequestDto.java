package com.projectstarter.ProjectStarter.service.dto.project;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequestDto implements Dto {
    private String title;
    private Long userId;
}