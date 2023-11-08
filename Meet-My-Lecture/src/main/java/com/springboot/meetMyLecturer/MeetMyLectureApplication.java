package com.springboot.meetMyLecturer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
		info = @Info(
				title = "Test API",
				version = "1.0",
				description = "API Information"
		),
		servers = @Server(
				url = "/",
				description = "Default Server URL"
		)
)
@EnableScheduling
@SpringBootApplication
public class MeetMyLectureApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeetMyLectureApplication.class, args);
	}

}
