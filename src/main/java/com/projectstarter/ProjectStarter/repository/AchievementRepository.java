package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByUserId(long userId);
}
