package com.projectstarter.ProjectStarter.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "news_text")
    private String newsText;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
