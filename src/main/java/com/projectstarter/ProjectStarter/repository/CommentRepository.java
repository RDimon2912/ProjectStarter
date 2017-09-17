package com.projectstarter.ProjectStarter.repository;
import com.projectstarter.ProjectStarter.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    Comments findById(Long id);
    List<Comments> findAllByUserId(long userId);
    List<Comments> findAllByProjectIdOrderByDateDesc(long projectId);
}
