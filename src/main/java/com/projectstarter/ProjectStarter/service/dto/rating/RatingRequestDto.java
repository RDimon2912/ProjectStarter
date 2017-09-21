package com.projectstarter.ProjectStarter.service.dto.rating;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDto implements Dto{
    private long id;
    private long userId;
    private long projectId;
    private int score;
}
