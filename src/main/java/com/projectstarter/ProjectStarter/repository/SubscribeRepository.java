package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscribeRepository extends JpaRepository<Subscription, Long> {
    void deleteByUserIdAndProjectId(Long userId, Long projectId);
}
