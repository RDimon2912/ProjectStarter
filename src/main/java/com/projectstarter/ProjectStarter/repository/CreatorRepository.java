package com.projectstarter.ProjectStarter.repository;


import com.projectstarter.ProjectStarter.model.CreatorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<CreatorRequest, Long> {
    public CreatorRequest findByUser(Long id);
}
