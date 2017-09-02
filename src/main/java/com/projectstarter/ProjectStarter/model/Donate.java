package com.projectstarter.ProjectStarter.model;

import javax.persistence.*;

@Entity
@Table(name = "donates")
public class Donate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
