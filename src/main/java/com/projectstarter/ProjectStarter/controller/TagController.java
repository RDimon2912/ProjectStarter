package com.projectstarter.ProjectStarter.controller;

import com.projectstarter.ProjectStarter.service.TagService;
import com.projectstarter.ProjectStarter.service.dto.tag.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping(value = "/all")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TagDto> findAllTags() {
        return tagService.findAllTags();
    }
}
