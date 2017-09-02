package com.projectstarter.ProjectStarter.model;

import javax.persistence.*;
import java.sql.Date;

public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
