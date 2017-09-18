package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.AchievementName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "achievement_name")
    @Enumerated(EnumType.STRING)
    private AchievementName achievementName;

    @Column(name = "date")
    private Timestamp date;
}
