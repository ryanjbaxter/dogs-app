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

	@GetExchange
	public List<Dog> getDogs();

	@PostExchange
	public void createDogs(@RequestBody Dog newDog);

	@PutExchange
	public void updateDogs(@RequestBody Dog updatedDog);

	@DeleteExchange("/{id}")
	public void deleteDogs(@PathVariable String id);

}
