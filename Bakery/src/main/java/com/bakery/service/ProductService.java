package com.bakery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.entities.Product;
import com.bakery.repository.ProductRepository;

@Service
public class ProductService {

	// Injecting our ProductRepository
	
	@Autowired
	ProductRepository productRepository;
	
	// For getting all the list of products...
	
	public List<Product> getAllProduct(){
		return productRepository.findAll(); 
	}
	
	// For adding the product...
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	// For deleting the product by id...
	
	public void removeProductById(long id) {
		productRepository.deleteById(id);
	}
	
	// For finding a particular product by id...
	
	// Used optional because the product may be present or may not be present...
	
	public Optional<Product> getProductById(long id){
		return productRepository.findById(id);
	}
	
	// Finding all products by category id...
	
	public List<Product> getAllProductsByCategoryId(int id){
		
		/*
			Notice that below we wrote findAllByCategory_Id(id), in that Category_Id is 
			the column name which needs to written exactly in Pascal Case. 
		*/
		
		// As this is our custom method, so we need to define it in our ProductRepository...
		
		return productRepository.findAllByCategory_Id(id);
	
	}
	
	
}
