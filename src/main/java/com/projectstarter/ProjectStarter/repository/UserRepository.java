package com.projectstarter.ProjectStarter.repository;


import com.projectstarter.ProjectStarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(Long id);

}
