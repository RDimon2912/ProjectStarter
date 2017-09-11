package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Biography;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiographyRepository extends JpaRepository<Biography, Long> {
    Biography findById(Long id);
}
