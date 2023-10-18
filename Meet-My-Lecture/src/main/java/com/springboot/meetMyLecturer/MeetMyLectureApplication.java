package com.springboot.meetMyLecturer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
public class MeetMyLectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetMyLectureApplication.class, args);
	}

}
