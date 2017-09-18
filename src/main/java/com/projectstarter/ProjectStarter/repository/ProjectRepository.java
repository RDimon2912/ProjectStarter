package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findById(Long id);
    List<Project> findAllByUserId(Long userId);
    int countProjectByUser(User user);
    List<Project> findAllByUserIdOrderByStartDateDesc(Long userId);

    @Query(value = "SELECT * FROM projects ORDER BY start_date DESC limit 8", nativeQuery = true)
    List<Project> findAllOrderByStartDateDescLimit8();
}
