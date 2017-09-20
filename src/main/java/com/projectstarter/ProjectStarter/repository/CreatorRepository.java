package com.projectstarter.ProjectStarter.repository;


import com.projectstarter.ProjectStarter.model.CreatorRequest;
import com.projectstarter.ProjectStarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<CreatorRequest, Long> {
    public CreatorRequest findByUser(User user);
}
