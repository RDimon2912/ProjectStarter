package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.DonateSystem;
import com.projectstarter.ProjectStarter.service.dto.rewards.RewardsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RewardTransformer {
    public RewardsDto makeDto(final DonateSystem reward) {
        RewardsDto rewardsDto = new RewardsDto();
        rewardsDto.setAmount(reward.getAmount());
        rewardsDto.setDescription(reward.getDescription());
        rewardsDto.setId(reward.getId());
        rewardsDto.setProjectId(reward.getProject().getId());
        return rewardsDto;
    }
}
