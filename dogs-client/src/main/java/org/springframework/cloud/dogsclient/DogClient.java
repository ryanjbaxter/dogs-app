package org.springframework.cloud.dogsclient;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

/**
 * @author Ryan Baxter
 */
@HttpExchange(url = "/dogs", accept = "application/json")
public interface DogClient {

	@GetExchange()
	List<Dog> getDogs();

	@GetExchange(value = "/{id}", version = "0.0.2")
	Dog getDog(@PathVariable Long id);

	@PostExchange
	void createDog(@RequestBody Dog newDog);

	@PutExchange
	void updateDog(@RequestBody Dog updatedDog);

	@DeleteExchange("/{id}")
	void deleteDog(@PathVariable Long id);

}
