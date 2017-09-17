package com.projectstarter.ProjectStarter.repository;

import com.projectstarter.ProjectStarter.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubscribeRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByProjectId(Long projectId);
    Subscription findFirstByUserIdAndProjectId(Long userId, Long projectId);
    void deleteByUserIdAndProjectId(Long userId, Long projectId);
}
