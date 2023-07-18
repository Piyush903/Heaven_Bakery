package com.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.Category;

// Repositories are basically DAO...

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	
}
