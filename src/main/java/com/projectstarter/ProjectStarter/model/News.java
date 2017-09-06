package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
public class News {
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "news_text")
    private String newsText;

    @Column(name = "date")
    private Date date;
}
