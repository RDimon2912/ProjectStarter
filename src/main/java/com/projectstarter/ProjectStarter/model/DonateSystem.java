package com.projectstarter.ProjectStarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

@Entity
@Table(name = "donate_system")
@Getter
@Setter
@NoArgsConstructor
public class DonateSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "amount")
    private int amount;

    @Column(name = "description")
    @Field
    @Type(type = "text")
    private String description;
}
