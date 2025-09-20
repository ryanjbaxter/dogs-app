package org.springframework.cloud.dogapp;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Baxter
 */
@RestController
@RequestMapping("/dogs")
public class DogController {

	private final DogRepository dogRepository;
	private final DogsConfiguration dogsConfiguration;

	public DogController(DogRepository dogRepository, DogsConfiguration dogsConfiguration) {
		this.dogRepository = dogRepository;
		this.dogsConfiguration = dogsConfiguration;
	}


	@GetMapping
	public List<Dog> dogs() {
		if (dogsConfiguration.isShowMedicalConditions()) {
			return dogRepository.findDogsByExistingMedicalConditions(true);
		}
		else {
			return dogRepository.findAll();
		}
//		return dogRepository.findAll();
	}

	@GetMapping(value = "/{id}", version = "0.0.2")
	public Dog dog(@PathVariable Long id) {
		return dogRepository.findById(id).get();
	}

	// I don't think a version here should be necessary
	@GetMapping(value = "/{id}", version = "0.0.1")
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
	public ResponseEntity<String> deleteDog(@PathVariable Long id, @RequestHeader(name = "X-DELETE-TOKEN", required = false) String deleteToken) {
		if (!StringUtils.hasText(deleteToken) || !deleteToken.equals(dogsConfiguration.getDeleteToken())) {
			return new ResponseEntity<>("Delete token is invalid", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		dogRepository.deleteById(id);
		return new ResponseEntity<>("Deleted", HttpStatus.OK);
	}
}
