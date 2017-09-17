package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "creator_requests")
@Getter
@Setter
@NoArgsConstructor
public class CreatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private long user;

    @Column(name = "image")
    private String image;
}
