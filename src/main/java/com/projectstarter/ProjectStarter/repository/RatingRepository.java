package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByProjectId(long projectId);
    Rating findByUserIdAndProjectId(long userId, long projectId);
}
