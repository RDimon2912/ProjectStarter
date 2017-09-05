package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.AchievementName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "achivments")
@Getter
@Setter
@NoArgsConstructor
public class Achivment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "achievement_name")
    @Enumerated(EnumType.STRING)
    private AchievementName achievementName;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
