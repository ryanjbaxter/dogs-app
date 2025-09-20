package org.springframework.cloud.dogsclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ryan Baxter
 */
@ConfigurationProperties("dogs")
public class DogsConfigurationProperties {
	private String deleteToken = "";

	public String getDeleteToken() {
		return deleteToken;
	}

	public void setDeleteToken(String deleteToken) {
		this.deleteToken = deleteToken;
	}
}
