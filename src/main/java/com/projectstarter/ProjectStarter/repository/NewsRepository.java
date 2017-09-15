package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
