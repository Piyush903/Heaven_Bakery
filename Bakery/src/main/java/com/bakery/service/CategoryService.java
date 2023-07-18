package com.bakery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.entities.Category;
import com.bakery.repository.CategoryRepository;

@Service
public class CategoryService {

	// Injecting our CategoryRepository
	
	@Autowired	
	CategoryRepository categoryRepository;
	
	// When we not used to use the service we did all implementation in our controllers using entities and repositories only...
	// Now, we will do via service, will ease in coding...
	
	// For adding (saving) a category...
	
	public void addCategory(Category category) {
		categoryRepository.save(category);
	}
	
	// For getting all the list of categories...
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	// For removing the category by id...
	
	public void removeCategoryById(int id) {
		categoryRepository.deleteById(id);
	}
	
	// For getting a category...
	// We are mentioning optional because it might be posiible that there is a category or may be not...
	
	public Optional<Category> getCategoryById(int id){
		return categoryRepository.findById(id);		
	}  
	
	
	
	
	
}
