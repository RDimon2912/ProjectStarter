package com.projectstarter.ProjectStarter.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProjectListDto implements Dto {

    private long id;
    private String user_id;
    private String title;
    private Date start_date;
    private Date end_date;
}