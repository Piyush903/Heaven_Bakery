package com.bakery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findAllByCategory_Id(int id);
	
	// Magic of JPA as we need to give names in specific pattern and we get our methods pre-written...
	
}
