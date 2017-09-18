package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByProjectId(Long projectId);
}
