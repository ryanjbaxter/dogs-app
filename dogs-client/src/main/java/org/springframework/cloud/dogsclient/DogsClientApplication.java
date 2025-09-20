package org.springframework.cloud.dogsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.httpservice.HttpServiceFallback;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
@EnableConfigurationProperties(DogsConfigurationProperties.class)
public class DogsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogsClientApplication.class, args);
	}

	/**
	 * Configuration for the DogClient HTTP service.
	 */
	@ImportHttpServices(group = "dog-app", types = DogClient.class)
//	@HttpServiceFallback(value = DogClientFallback.class, service = DogClient.class, group = "dog-app")
	@Configuration
	static class DogClientConfig {
		@Bean
		public RestClientHttpServiceGroupConfigurer groupConfigurer() {
			return groups ->
				groups.forEachClient((group, clientBuilder) ->
						clientBuilder.apiVersionInserter(ApiVersionInserter.useHeader("X-API-VERSION")).build());
		}
	}
}
