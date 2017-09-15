package com.projectstarter.ProjectStarter.service.transformer;

import com.projectstarter.ProjectStarter.model.News;
import com.projectstarter.ProjectStarter.model.Project;
import com.projectstarter.ProjectStarter.service.dto.news.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsTransformer {
    public NewsDto makeDto(final News news) {
        NewsDto newsDto = new NewsDto();

        newsDto.setId(news.getId());
        newsDto.setProjectId(news.getProject().getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setNewsText(news.getNewsText());
        newsDto.setDate(news.getDate());

        return newsDto;
    }

    public News makeObject(final NewsDto newsDto) {
        News news = new News();

        Project project = new Project();
        project.setId(newsDto.getProjectId());

        news.setProject(project);
        news.setTitle(newsDto.getTitle());
        news.setNewsText(newsDto.getNewsText());

        return news;
    }
}
