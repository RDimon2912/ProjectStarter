package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "biographies")
@Getter
@Setter
@NoArgsConstructor
public class Biography {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "biography")
    private String biography;

    @Column(name = "location")
    private String location;

    @Column(name = "image_url")
    private String imageUrl;
}
