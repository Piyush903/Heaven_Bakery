package com.bakery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// Creating a custom method. Here we need to write just the method name by following the pattern and Jpa will automatically create that method...
	
	Optional<User> findUserByEmail(String email);
	
}
