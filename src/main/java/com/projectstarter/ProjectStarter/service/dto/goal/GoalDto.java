package com.projectstarter.ProjectStarter.service.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDto {
    private Long id;
    private Long projectId;
    private int amount;
    private String goalText;
}
