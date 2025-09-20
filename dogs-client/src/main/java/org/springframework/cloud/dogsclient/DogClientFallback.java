package org.springframework.cloud.dogsclient;

import java.util.List;

/**
 * @author Ryan Baxter
 */
public class DogClientFallback implements DogClient{
	@Override
	public List<Dog> getDogs() {
		return List.of( new Dog(1L, "fallback", "fallback", 0, 0, false));
	}

	@Override
	public Dog getDog(Long id) {
		return new Dog(1L, "fallback", "fallback", 0, 0, false);
	}

	@Override
	public void createDog(Dog newDog) {

	}

	@Override
	public void updateDog(Dog updatedDog) {

	}

	@Override
	public void deleteDog(Long id, String deleteToken) {

	}
}
