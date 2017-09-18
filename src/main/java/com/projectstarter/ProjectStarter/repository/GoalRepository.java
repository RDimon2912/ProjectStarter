package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
