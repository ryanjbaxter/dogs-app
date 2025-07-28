package org.springframework.cloud.dogsclient.controller;

import java.util.List;

import org.springframework.cloud.dogsclient.Dog;
import org.springframework.cloud.dogsclient.DogClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the DogClient functionality to the React.js frontend.
 */
@RestController
@RequestMapping("/api/dogs")
@CrossOrigin(origins = "*")
public class DogController {

    private final DogClient dogClient;

    public DogController(DogClient dogClient) {
        this.dogClient = dogClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDog(@PathVariable Long id) {
        return ResponseEntity.ok(dogClient.getDog(id).getFirst());
    }

    /**
     * Get all dogs.
     *
     * @return List of all dogs
     */
    @GetMapping
    public ResponseEntity<List<Dog>> getAllDogs() {
        return ResponseEntity.ok(dogClient.getDogs());
    }

    /**
     * Create a new dog.
     *
     * @param dog The dog to create
     * @return ResponseEntity with status code
     */
    @PostMapping
    public ResponseEntity<Void> createDog(@RequestBody Dog dog) {
        dogClient.createDog(dog);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update an existing dog.
     *
     * @param dog The dog to update
     * @return ResponseEntity with status code
     */
    @PutMapping
    public ResponseEntity<Void> updateDog(@RequestBody Dog dog) {
        dogClient.updateDog(dog);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a dog by ID.
     *
     * @param id The ID of the dog to delete
     * @return ResponseEntity with status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        dogClient.deleteDog(id);
        return ResponseEntity.ok().build();
    }
}
