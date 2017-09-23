package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByProjectIdOrderByDateDesc(Long projectId);
    List<News> findAllByProjectId(Long projectId);


    @Query(value = "SELECT * " +
            "FROM news n " +
            "JOIN subscribed_projects sp on n.project_id=sp.project_id " +
            "WHERE sp.user_id = :userId " +
            "ORDER BY n.date DESC ", nativeQuery = true)
    List<News> findAllUserSubscribedProjectsNews(@Param("userId") Long userId);
}
