package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Biography;
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

    public Subscription copyForSendingEmail(Subscription subscription) {
        Subscription newSubscription = new Subscription();
        newSubscription.setId(subscription.getId());

        User user = new User();
        user.setEmail(subscription.getUser().getEmail());

        Biography biography = new Biography();
        biography.setName(subscription.getUser().getBiography().getName());
        user.setBiography(biography);
        newSubscription.setUser(user);

        Project project = new Project();
        project.setId(subscription.getProject().getId());
        project.setTitle(subscription.getProject().getTitle());
        newSubscription.setProject(project);

        return newSubscription;
    }
}
