package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.User;

public interface UserService {
    User findUserByEmail(String email);
    User findUserById(Long id);
}
