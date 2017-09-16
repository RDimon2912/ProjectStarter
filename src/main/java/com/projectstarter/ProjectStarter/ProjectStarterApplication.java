package com.projectstarter.ProjectStarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import({ SecurityConfiguration.class, WebConfiguration.class })
@PropertySource({"classpath:application.properties"})
public class ProjectStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectStarterApplication.class, args);
	}
}
