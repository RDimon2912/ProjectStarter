package com.projectstarter.ProjectStarter.service.dto.comments;

import com.projectstarter.ProjectStarter.service.dto.Dto;

public class CommentRequestDto implements Dto{
    public long userId;
    public long projectId;
    public String commentText;
}
