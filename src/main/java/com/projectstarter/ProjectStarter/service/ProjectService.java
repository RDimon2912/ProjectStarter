package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    public Project create(String name, String password, String email) {
        Project newProject = new Project();

        return newProject;
    }

}
