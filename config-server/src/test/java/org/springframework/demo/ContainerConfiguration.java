package org.springframework.demo;


import org.testcontainers.containers.localstack.LocalStackContainer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Ryan Baxter
 */
@TestConfiguration
public class ContainerConfiguration {

	@Bean
	LocalStackContainer localStackContainer() {
		return new LocalStackContainer("4.7.0");
	}
}
