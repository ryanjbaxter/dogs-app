package org.springframework.cloud.dogapp;

import java.time.ZonedDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.StandardApiVersionDeprecationHandler;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogAppApplication.class, args);
	}

	@Configuration
    static class WebMvcConfigurerAdapter implements WebMvcConfigurer {
	}
}
