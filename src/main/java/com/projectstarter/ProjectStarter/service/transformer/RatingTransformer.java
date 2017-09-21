package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Rating;
import com.projectstarter.ProjectStarter.repository.ProjectRepository;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.rating.RatingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingTransformer {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Rating makeObject(RatingRequestDto ratingRequestDto) {
        Rating rating = new Rating();
        rating.setProject(projectRepository.findById(ratingRequestDto.getProjectId()));
        rating.setUser(userRepository.findById(ratingRequestDto.getUserId()));
        rating.setScore(ratingRequestDto.getScore());
        return rating;
    }
}
