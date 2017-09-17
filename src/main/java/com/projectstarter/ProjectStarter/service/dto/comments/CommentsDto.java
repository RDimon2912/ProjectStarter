package com.projectstarter.ProjectStarter.service.dto.comments;


import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto implements Dto {
    private Long id;
    private Long projectId;
    private String username;
    private String comment;
    private Date date;
}