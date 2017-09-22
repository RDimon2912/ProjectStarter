package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Achievement;
import com.projectstarter.ProjectStarter.service.dto.achievements.AchievementDto;
import org.springframework.stereotype.Component;

@Component
public class AchievementTransformer {
    public AchievementDto makeDto(Achievement achievement) {
        AchievementDto achievementDto = new AchievementDto();
        achievementDto.setDate(achievement.getDate());
        String achievementName = achievement.getAchievementName().toString();
        achievementName = achievementName.replace('_', ' ');
        achievementName = achievementName.toLowerCase();
        achievementName = achievementName.substring(0, 1).toUpperCase() + achievementName.substring(1);
        achievementDto.setAchievementName(achievementName);
        achievementDto.setUserId(achievement.getUser().getId());
        achievement.setId(achievement.getId());
        return achievementDto;
    }
}
