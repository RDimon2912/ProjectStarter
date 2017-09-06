package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "donates")
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Date date;
}
