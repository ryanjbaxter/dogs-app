package org.springframework.cloud.dogsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
public class DogsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogsClientApplication.class, args);
	}

	@ImportHttpServices(group = "dogs", types = DogClient.class)
	@Configuration
	static class DogClientConfig {

		@Bean
		public RestClientHttpServiceGroupConfigurer groupConfigurer(@Value("${dog-client.api.base-url}") String baseUrl) {
			return groups ->
				groups.filterByName("dogs").forEachClient((group, clientBuilder) -> clientBuilder.baseUrl(baseUrl));
		}
	}

	@Component
	static class Runner implements CommandLineRunner {

		private final DogClient dogClient;

		Runner(DogClient dogClient) {
			this.dogClient = dogClient;
		}

		@Override
		public void run(String... args) throws Exception {
			System.out.println(dogClient.getDogs());
		}
	}



}
