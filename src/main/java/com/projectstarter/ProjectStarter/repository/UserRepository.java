package com.projectstarter.ProjectStarter.repository;


import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(Long id);

    List<User> findAllByRoleEquals(Role roleAdmin);
    List<User> findAllByRoleOrderByBlockStatus(Role role);
    List<User> findAllByRoleOrderByLastLogInDesc(Role role);
    List<User> findAllByRoleOrderByRegistrationDateDesc(Role role);
    List<User> findAllByOrderByBlockStatus();
    List<User> findAllByOrderByLastLogInDesc();
    List<User> findAllByOrderByRegistrationDateDesc();
}
