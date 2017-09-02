package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.AchivmentName;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "achivments")
public class Achivment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "achivment_name")
    @Enumerated(EnumType.STRING)
    private AchivmentName achivmentName;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
