package org.springframework.cloud.dogsclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DogsClientApplicationTests {

	@Container
	static MockServerContainer mockServerContainer =
			new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.15.0"));

	static MockServerClient mockServerClient;

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		mockServerClient = new MockServerClient(
				mockServerContainer.getHost(),
				mockServerContainer.getServerPort()
		);
		registry.add("photos.api.base-url", mockServerContainer::getEndpoint);
	}

	@BeforeEach
	void setUp() {
//		RestAssured.port = port;
		mockServerClient.reset();
		String dogsJson = "[{\"id\":1,\"breed\":\"Labrador Retriever\",\"name\":\"Max\",\"age\":3,\"weight\":65.5,\"existingMedicalConditions\":false},{\"id\":2,\"breed\":\"German Shepherd\",\"name\":\"Luna\",\"age\":5,\"weight\":75.2,\"existingMedicalConditions\":true},{\"id\":3,\"breed\":\"Golden Retriever\",\"name\":\"Buddy\",\"age\":2,\"weight\":60.0,\"existingMedicalConditions\":false},{\"id\":4,\"breed\":\"Beagle\",\"name\":\"Charlie\",\"age\":4,\"weight\":22.5,\"existingMedicalConditions\":false},{\"id\":5,\"breed\":\"Poodle\",\"name\":\"Daisy\",\"age\":6,\"weight\":45.8,\"existingMedicalConditions\":true},{\"id\":6,\"breed\":\"Bulldog\",\"name\":\"Rocky\",\"age\":3,\"weight\":50.2,\"existingMedicalConditions\":true},{\"id\":7,\"breed\":\"Siberian Husky\",\"name\":\"Bella\",\"age\":4,\"weight\":55.0,\"existingMedicalConditions\":false},{\"id\":8,\"breed\":\"Dachshund\",\"name\":\"Cooper\",\"age\":2,\"weight\":12.3,\"existingMedicalConditions\":false},{\"id\":9,\"breed\":\"Boxer\",\"name\":\"Sadie\",\"age\":5,\"weight\":65.7,\"existingMedicalConditions\":true},{\"id\":10,\"breed\":\"Shih Tzu\",\"name\":\"Oliver\",\"age\":7,\"weight\":16.4,\"existingMedicalConditions\":true}]";
		mockServerClient.when(request().withMethod("GET").withPath("/dogs")).respond(response().withStatusCode(200).withHeader(Header.header("Content-Type", "application/json")).withBody(dogsJson));
	}

	@Test
	void contextLoads() {
	}

}
