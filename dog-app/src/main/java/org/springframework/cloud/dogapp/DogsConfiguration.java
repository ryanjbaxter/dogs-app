package org.springframework.cloud.dogapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ryan Baxter
 */
@ConfigurationProperties("dogs")
public class DogsConfiguration {
	private boolean showMedicalConditions;

	private String deleteToken;

	public boolean isShowMedicalConditions() {
		return showMedicalConditions;
	}

	public void setShowMedicalConditions(boolean showMedicalConditions) {
		this.showMedicalConditions = showMedicalConditions;
	}
	public String getDeleteToken() {
		return deleteToken;
	}

	public void setDeleteToken(String deleteToken) {
		this.deleteToken = deleteToken;
	}
}
