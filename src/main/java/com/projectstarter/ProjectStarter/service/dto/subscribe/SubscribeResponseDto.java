package com.projectstarter.ProjectStarter.service.dto.subscribe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscribeResponseDto {
    private boolean subscribed;

    public SubscribeResponseDto(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
