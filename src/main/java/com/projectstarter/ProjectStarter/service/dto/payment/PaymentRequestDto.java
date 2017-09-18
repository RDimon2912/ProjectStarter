package com.projectstarter.ProjectStarter.service.dto.payment;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto implements Dto {
    private long userId;
    private long projectId;
    private int amount;
}
