package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectCreateResponseDto {
    private Long project_id;

    public ProjectCreateResponseDto(final Long project_id) {
        this.project_id = project_id;
    }
}
