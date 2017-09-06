package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.AchievementName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
public class Achievement {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "achievement_name")
    @Enumerated(EnumType.STRING)
    private AchievementName achievementName;

    @Column(name = "date")
    private Date date;
}
