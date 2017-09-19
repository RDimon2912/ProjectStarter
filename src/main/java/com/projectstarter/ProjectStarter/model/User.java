package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.BlockStatus;
import com.projectstarter.ProjectStarter.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
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
}
