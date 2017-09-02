package com.projectstarter.ProjectStarter.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "donate_system")
public class DonateSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int amount;

    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
