package com.projectstarter.ProjectStarter.service.dto.rating;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseRatingDto implements Dto {
    private double rating;
    private int amountOfPeople;
}
