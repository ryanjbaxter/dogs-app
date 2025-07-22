package org.springframework.cloud.dogsclient;

/**
 * @author Ryan Baxter
 */
public record Dog(Long id, String breed, String name, int age, double weight, boolean existingMedicalConditions) {
}
