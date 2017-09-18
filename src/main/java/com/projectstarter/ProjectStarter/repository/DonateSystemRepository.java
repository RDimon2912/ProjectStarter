package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.DonateSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateSystemRepository extends JpaRepository<DonateSystem, Long> {
    List<DonateSystem> findAllByProjectId(long projectId);
}
