package com.projectstarter.ProjectStarter.service.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRequestDto {
    private Long userId;
    private Long projectId;
    private boolean needToSubscribe;
}
