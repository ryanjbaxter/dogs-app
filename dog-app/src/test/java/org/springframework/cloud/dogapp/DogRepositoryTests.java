package org.springframework.cloud.dogapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DogRepositoryTests {

    @Autowired
    private DogRepository dogRepository;

    @Test
    void testAutoGenerateId() {
        // Create a new Dog with null ID
        Dog dog = new Dog(null, "Test Breed", "Test Name", 1, 10.0, false);

        // Save the Dog
        Dog savedDog = dogRepository.save(dog);

        // Verify that an ID was generated
        assertThat(savedDog.id()).isNotNull();
        System.out.println("[DEBUG_LOG] Generated ID: " + savedDog.id());

        // Verify that other properties were saved correctly
        assertThat(savedDog.breed()).isEqualTo("Test Breed");
        assertThat(savedDog.name()).isEqualTo("Test Name");
        assertThat(savedDog.age()).isEqualTo(1);
        assertThat(savedDog.weight()).isEqualTo(10.0);
        assertThat(savedDog.existingMedicalConditions()).isFalse();
    }
}
