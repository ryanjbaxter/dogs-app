package org.springframework.cloud.dogsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application class for the Dogs Client application.
 * This application provides a React.js UI for managing dogs through the DogClient interface.
 */
@SpringBootApplication
public class DogsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogsClientApplication.class, args);
	}

	/**
	 * Configuration for the DogClient HTTP service.
	 */
	@ImportHttpServices(group = "dogs", types = DogClient.class)
	@Configuration
	static class DogClientConfig {

		@Bean
		public RestClientHttpServiceGroupConfigurer groupConfigurer(@Value("${dog-client.api.base-url}") String baseUrl) {
			return groups ->
				groups.filterByName("dogs").forEachClient((group, clientBuilder) ->
						clientBuilder.baseUrl(baseUrl).apiVersionInserter(ApiVersionInserter.useHeader("X-API-VERSION"))
								.build());
		}
	}


	// Necessary to be able to refresh the web app in the browser to allow react handle the routing
	@RestController
	static class SpaController {
		@GetMapping({"/", "/{path:[^\\.]*}", "/{path:^(?!api$).*?}/{pathTwo:[^\\.]*}/{pathThree:[^\\.]*}"})
		public String forwardToIndex() {
			return "forward:/index.html";
		}
	}
}
