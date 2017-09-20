package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByProjectIdOrderByDateDesc(Long projectId);
    List<News> findAllByProjectId(Long projectId);
}
