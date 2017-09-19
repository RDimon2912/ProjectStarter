package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Donate;
import com.projectstarter.ProjectStarter.model.DonateSystem;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.donate.DonateDto;
import com.projectstarter.ProjectStarter.service.dto.payment.PaymentRequestDto;
import com.projectstarter.ProjectStarter.service.dto.rewards.RewardsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DonateTransformer {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Donate makeObject(final PaymentRequestDto paymentRequestDto) {
        Donate donate = new Donate();
        donate.setAmount(paymentRequestDto.getAmount());
        donate.setProject(projectRepository.findById(paymentRequestDto.getProjectId()));
        donate.setUser(userRepository.findById(paymentRequestDto.getUserId()));
        return donate;
    }

    public DonateSystem makeObjectDS(final RewardsDto rewardsDto) {
        DonateSystem donateSystem = new DonateSystem();
        donateSystem.setAmount(rewardsDto.getAmount());
        donateSystem.setProject(projectRepository.findById(rewardsDto.getProjectId()));
        donateSystem.setDescription(rewardsDto.getDescription());
        return donateSystem;
    }

    public RewardsDto makeDto(DonateSystem donateSystem) {
        RewardsDto rewardsDto = new RewardsDto();
        rewardsDto.setProjectId(donateSystem.getProject().getId());
        rewardsDto.setDescription(donateSystem.getDescription());
        rewardsDto.setAmount(donateSystem.getAmount());
        rewardsDto.setId(donateSystem.getId());
        return rewardsDto;
    }

    public DonateDto makeObjectDonateDto(final Donate donate) {
        DonateDto donateDto = new DonateDto();
        donateDto.setId(donate.getId());
        donateDto.setUserId(donate.getUser().getId());
        donateDto.setProjectId(donate.getProject().getId());
        donateDto.setAmount(donate.getAmount());
        return donateDto;
    }
}
