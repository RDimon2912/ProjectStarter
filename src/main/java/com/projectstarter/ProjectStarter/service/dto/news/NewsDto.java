package com.projectstarter.ProjectStarter.service.dto.news;

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
public class NewsDto {
    private Long id;
    private Long projectId;
    private String projectName;
    private String title;
    private String newsText;
    private Timestamp date;
}
