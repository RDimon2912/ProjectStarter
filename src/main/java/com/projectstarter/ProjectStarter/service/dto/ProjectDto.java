package com.projectstarter.ProjectStarter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private Long userId;
    private String title;
//    private String imageUrl;
    private String description;
    private int targetAmount;
    private int currentAmount;
    private double rating;
    private Date startDate;
    private Date endDate;
    private String projectStatus;
}
