package org.springframework.cloud.dogapp;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Baxter
 */
@RestController
@RequestMapping("/dogs")
public class DogController {

	private final DogRepository dogRepository;

	public DogController(DogRepository dogRepository) {
		this.dogRepository = dogRepository;
	}


	@GetMapping
	public List<Dog> dogs() {
		return dogRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public List<Dog> dogs(@PathVariable Long id) {
		return List.of(dogRepository.findById(id).get());
	}

	@PostMapping
	public Dog createDog(@RequestBody Dog dog) {
		return dogRepository.save(dog);
	}

	@PutMapping
	public Dog updateDog(@RequestBody Dog dog) {
		return dogRepository.save(dog);
	}

	@DeleteMapping("/{id}")
	public void deleteDog(@PathVariable Long id) {
		dogRepository.deleteById(id);
	}
}
