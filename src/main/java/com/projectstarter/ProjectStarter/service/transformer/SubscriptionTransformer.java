package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.model.Subscription;
import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.service.dto.subscribe.SubscribeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionTransformer {
    public Subscription makeObject(final SubscribeRequestDto subscribeRequestDto) {
        Subscription subscription = new Subscription();

        User user = new User();
        user.setId(subscribeRequestDto.getUserId());
        subscription.setUser(user);

        Project project = new Project();
        project.setId(subscribeRequestDto.getProjectId());
        subscription.setProject(project);

        return subscription;
    }
}
