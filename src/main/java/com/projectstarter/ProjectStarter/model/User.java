package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToOne()
    @JoinColumn(name = "biography_id")
    private Biography biography;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "block_status")
    @Enumerated(EnumType.STRING)
    private BlockStatus blockStatus;

    @Column(name = "last_log_in")
    private Timestamp lastLogIn;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @Column(name = "confirmed")
    private boolean confirmed;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Achievement> achievementList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Biography> biographyList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptionList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Donate> donateList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Project> projectList;
}
