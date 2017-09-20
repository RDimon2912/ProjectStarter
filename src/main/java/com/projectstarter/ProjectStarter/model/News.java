package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "news")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "title")
    @Field
    private String title;

    @Column(name = "news_text")
    @Field
    private String newsText;

    @Column(name = "date")
    private Timestamp date;
}
