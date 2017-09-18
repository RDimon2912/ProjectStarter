package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Donate;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.payment.PaymentRequestDto;
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
}
