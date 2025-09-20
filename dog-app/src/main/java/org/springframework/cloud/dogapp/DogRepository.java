package org.springframework.cloud.dogapp;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

/**
 * @author Ryan Baxter
 */
public interface DogRepository extends ListCrudRepository<Dog, Long> {

	List<Dog> findDogsByExistingMedicalConditions(boolean existingMedicalConditions);

}
