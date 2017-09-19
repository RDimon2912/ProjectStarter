package com.projectstarter.ProjectStarter.service.dto.rewards;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardsDto implements Dto {
    private Long id;
    private Long projectId;
    private int amount;
    private String description;
}
