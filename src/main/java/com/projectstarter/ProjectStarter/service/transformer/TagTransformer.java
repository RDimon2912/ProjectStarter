package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.Tag;
import com.projectstarter.ProjectStarter.service.dto.tag.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagTransformer {
    public TagDto makeDto(final Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setTagName(tag.getTagName());
        tagDto.setProjectCount(tag.getProjects().size());

        return tagDto;
    }
}
