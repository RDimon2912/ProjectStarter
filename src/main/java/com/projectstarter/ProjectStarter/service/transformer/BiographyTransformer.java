package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Biography;
import com.projectstarter.ProjectStarter.service.dto.user.BiographyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BiographyTransformer {
    public BiographyDto makeDto(final Biography biography) {
        BiographyDto biographyDto = new BiographyDto();

        biographyDto.setBiography(biography.getBiography());
        biographyDto.setImageUrl(biography.getImageUrl());
        biographyDto.setLocation(biography.getLocation());
        biographyDto.setName(biography.getName());

        return biographyDto;
    }
}
