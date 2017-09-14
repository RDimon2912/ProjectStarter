package com.projectstarter.ProjectStarter.repository;


import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(Long id);

    List<User> findAllByRoleEquals(Role roleAdmin);
    List<User> findAllByOrderByRegistrationDateDesc();
    List<User> findAllByOrderByBlockStatus();
    List<User> findAllByOrderByLastLogInDesc();
}
