package com.projectstarter.ProjectStarter.service.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private int targetAmount;
    private int currentAmount;
    private double rating;
    private Timestamp startDate;
    private Timestamp endDate;
    private String projectStatus;
    private String imageUrl;
}
