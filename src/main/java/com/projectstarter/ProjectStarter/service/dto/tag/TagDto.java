package com.projectstarter.ProjectStarter.service.dto.tag;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@EqualsAndHashCode
@Component
public class TagDto {
    private String tagName;
    private int projectCount;
}