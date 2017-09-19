package com.projectstarter.ProjectStarter.service.dto.project;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class ProjectListDto implements Dto {

    private long id;
    private String user_id;
    private String title;
    private Timestamp start_date;
    private Timestamp end_date;
}