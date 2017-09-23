package com.projectstarter.ProjectStarter.service.dto.achievements;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AchievementDto implements Dto {
    private Long id;
    private Long userId;
    private String achievementName;
    private Timestamp date;
    private boolean achieved;

}
