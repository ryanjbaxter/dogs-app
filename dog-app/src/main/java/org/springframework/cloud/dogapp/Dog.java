package org.springframework.cloud.dogapp;

import org.springframework.data.annotation.Id;

/**
 * @author Ryan Baxter
 */
public record Dog(@Id Long id, String breed, String name, int age, double weight, boolean existingMedicalConditions) {
}
