package com.projectstarter.ProjectStarter.service.dto.subscribe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscribeResponseDto {
    private boolean isSubscribed;

    public SubscribeResponseDto(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}
