package com.projectstarter.ProjectStarter.service.dto.donate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateDto {
    private Long id;
    private Long userId;
    private Long projectId;
    private int amount;
}
