package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.Tag;
import com.projectstarter.ProjectStarter.repository.TagRepository;
import com.projectstarter.ProjectStarter.service.dto.tag.TagDto;
import com.projectstarter.ProjectStarter.service.transformer.TagTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagTransformer tagTransformer;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<TagDto> findAllTags() {
        List<Tag> tagList = tagRepository.findAll();
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag: tagList) {
            tagDtoList.add(tagTransformer.makeDto(tag));
        }
        return tagDtoList;
    }
}
