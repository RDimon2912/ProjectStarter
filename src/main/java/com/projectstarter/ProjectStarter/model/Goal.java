package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}
