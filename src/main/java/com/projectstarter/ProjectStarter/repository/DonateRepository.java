package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Donate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DonateRepository extends JpaRepository<Donate, Long> {
}
