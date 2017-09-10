package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
