package org.springframework.demo;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testcontainers.localstack.LocalStackContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static software.amazon.awssdk.core.SdkSystemSetting.AWS_ACCESS_KEY_ID;
import static software.amazon.awssdk.core.SdkSystemSetting.AWS_SECRET_ACCESS_KEY;

/**
 * @author Ryan Baxter
 */
public class TestConfigServerApplication {
	private static final Log LOG = LogFactory.getLog(TestConfigServerApplication.class);

	public static void main(String[] args) {
		LocalStackContainer localStackContainer = new LocalStackContainer("localstack/localstack:4.7.0")
				.withServices("s3").withServices("secretsmanager");
		localStackContainer.start();
		List<String> awsArgs = new ArrayList<>();
		URI endpoint = localStackContainer.getEndpoint();
		String key = localStackContainer.getAccessKey();
		String secret = localStackContainer.getSecretKey();
		String region = localStackContainer.getRegion();
		awsArgs.add("--spring.cloud.config.server.awss3.endpoint="+endpoint.toString());
		awsArgs.add("--spring.cloud.config.server.awss3.region="+localStackContainer.getRegion());
		awsArgs.add("--spring.cloud.config.server.awss3.bucket=dogs");
		awsArgs.add("--spring.cloud.config.server.aws-secretsmanager.endpoint="+endpoint.toString());
		awsArgs.add("--spring.cloud.config.server.aws-secretsmanager.region="+localStackContainer.getRegion());
		awsArgs.add("--spring.cloud.config.server.aws-secretsmanager.prefix=/secret");
		awsArgs.addAll(Arrays.asList(args));
		System.setProperty(AWS_ACCESS_KEY_ID.property(), key);
		System.setProperty(AWS_SECRET_ACCESS_KEY.property(), secret);
		try (S3Client s3Client = createS3Client(endpoint, key, secret, region)) {
			createBucket(s3Client);
			uploadTestFiles(s3Client);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		try (SecretsManagerClient secretsManagerClient = createSecretsManagerClient(endpoint, key, secret, region)) {
			addSecrets(secretsManagerClient);
		}

		SpringApplication.from(ConfigServerApplication::main).run(awsArgs.toArray(new String[0]));
	}

	private static void addSecrets(SecretsManagerClient secretsManagerClient) {
		CreateSecretResponse resp = secretsManagerClient.createSecret(CreateSecretRequest.builder()
				.name("/secret/dog-app/")         // Secret name (path-like names are common)
				.description("App credentials")     // Optional
				.secretString("{\"dogs.delete-token\":\"secrettoken2\",\"password\":\"s3cr3t\"}") // JSON string is typical
				// .kmsKeyId("arn:aws:kms:us-east-1:123456789012:key/....")     // Optional custom KMS key
				.build());
		System.out.printf(resp.toString());

		resp = secretsManagerClient.createSecret(CreateSecretRequest.builder()
				.name("/secret/dogs-client/")         // Secret name (path-like names are common)
				.description("App credentials")     // Optional
				.secretString("{\"dogs.delete-token\":\"secrettoken\",\"password\":\"s3cr3t\"}") // JSON string is typical
				// .kmsKeyId("arn:aws:kms:us-east-1:123456789012:key/....")     // Optional custom KMS key
				.build());
		System.out.printf(resp.toString());
	}

	private static void uploadTestFiles(S3Client s3Client) throws IOException {
		Resource resource = new ClassPathResource("s3-application.yaml");
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket("dogs")
					.key("application.yaml")
					.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromFile(resource.getFile()));
			System.out.println("File " + resource.getFile().getAbsolutePath() + " uploaded successfully to dogs/" + resource.getFilename());

		resource = new ClassPathResource("s3-dog-app.yaml");
		putObjectRequest = PutObjectRequest.builder()
				.bucket("dogs")
				.key("dog-app.yaml")
				.build();

		s3Client.putObject(putObjectRequest, RequestBody.fromFile(resource.getFile()));
		System.out.println("File " + resource.getFile().getAbsolutePath() + " uploaded successfully to dogs/" + resource.getFilename());

		resource = new ClassPathResource("s3-dogs-client.yaml");
		putObjectRequest = PutObjectRequest.builder()
				.bucket("dogs")
				.key("dogs-client.yaml")
				.build();

		s3Client.putObject(putObjectRequest, RequestBody.fromFile(resource.getFile()));
		System.out.println("File " + resource.getFile().getAbsolutePath() + " uploaded successfully to dogs/" + resource.getFilename());
	}

	private static S3Client createS3Client(URI s3Endpoint, String key, String secret, String region) {
		return S3Client.builder()
				.endpointOverride(s3Endpoint)
				.credentialsProvider(StaticCredentialsProvider.create(
						AwsBasicCredentials.create(key, secret)))
				.region(Region.of(region))
				.build();
	}

	private static void createBucket(S3Client s3Client) {
		CreateBucketResponse response = s3Client.createBucket(CreateBucketRequest.builder().bucket("dogs").build());

		LOG.info(response);
	}

	private static SecretsManagerClient createSecretsManagerClient(URI secretManagerEndpoint, String key, String secret, String region) {
		return SecretsManagerClient.builder().endpointOverride(secretManagerEndpoint).credentialsProvider(StaticCredentialsProvider.create(
						AwsBasicCredentials.create(key, secret)))
				.region(Region.of(region))
				.build();
	}
}
