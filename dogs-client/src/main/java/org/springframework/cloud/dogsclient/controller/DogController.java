package org.springframework.cloud.dogsclient.controller;

import org.springframework.cloud.dogsclient.Dog;
import org.springframework.cloud.dogsclient.DogClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(dogClient.getDogs(id).getFirst());
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
        dogClient.createDogs(dog);
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
        dogClient.updateDogs(dog);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a dog by ID.
     *
     * @param id The ID of the dog to delete
     * @return ResponseEntity with status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable String id) {
        dogClient.deleteDogs(id);
        return ResponseEntity.ok().build();
    }
}
