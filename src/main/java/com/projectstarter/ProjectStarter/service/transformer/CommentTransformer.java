package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Comments;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.comments.CommentsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;


@Component
@RequiredArgsConstructor
public class CommentTransformer {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CommentsDto makeDto(final Comments comment) {
        CommentsDto commentsDto = new CommentsDto();

        commentsDto.setId(comment.getId());
        commentsDto.setProjectId(comment.getProject().getId());
        commentsDto.setUsername(comment.getUser().getEmail());
        commentsDto.setComment(comment.getComment());
        commentsDto.setDate(comment.getDate());

        return commentsDto;
    }

    public Comments makeObject(CommentRequestDto commentRequestDto) {
        Comments comments = new Comments();
        comments.setUser(userRepository.findById(commentRequestDto.userId));
        comments.setComment(commentRequestDto.commentText);
        comments.setProject(projectRepository.findById(commentRequestDto.projectId));

        comments.setDate(new java.sql.Timestamp(new java.util.Date().getTime()));

//        java.sql.Date ourJavaDateObject = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//        comments.setDate(ourJavaDateObject);
        return comments;
    }
}
