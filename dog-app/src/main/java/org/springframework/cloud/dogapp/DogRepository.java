package org.springframework.cloud.dogapp;

import org.springframework.data.repository.ListCrudRepository;

/**
 * @author Ryan Baxter
 */
public interface DogRepository extends ListCrudRepository<Dog, Long> {
}
