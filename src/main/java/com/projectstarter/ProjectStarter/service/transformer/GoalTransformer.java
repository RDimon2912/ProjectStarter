package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Goal;
import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.service.dto.goal.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoalTransformer {
    public GoalDto makeDto(final Goal goal) {
        GoalDto goalDto = new GoalDto();

        goalDto.setId(goal.getId());
        goalDto.setProjectId(goal.getProject().getId());
        goalDto.setAmount(goal.getAmount());
        goalDto.setGoalText(goal.getGoalText());

        return goalDto;
    }

    public Goal makeObject(final GoalDto goalDto) {
        Goal goal = new Goal();

        Project project = new Project();
        project.setId(goalDto.getProjectId());

        goal.setProject(project);
        goal.setAmount(goalDto.getAmount());
        goal.setGoalText(goalDto.getGoalText());

        return goal;
    }
}
