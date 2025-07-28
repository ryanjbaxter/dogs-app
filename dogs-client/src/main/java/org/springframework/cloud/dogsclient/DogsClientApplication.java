package org.springframework.cloud.dogsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

/**
 * Main application class for the Dogs Client application.
 * This application provides a React.js UI for managing dogs through the DogClient interface.
 */
@SpringBootApplication
public class DogsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogsClientApplication.class, args);
	}

}
