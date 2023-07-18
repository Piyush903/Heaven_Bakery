package com.bakery.dto;

// DTO is data transfer object. It is used basically to handle the object...

import com.bakery.entities.Category;


public class ProductDTO {

	// Here all the fields are same as mentioned in Product
	
	private Long id;
	private String name;
	private double price;
	private double weight;
	private String description;
	private String imageName;
	
	// Here instead of taking Category object class we take of int type and renamed to categoryId... 
	private int categoryId;

	// Generating constructors with and without fields...
	
	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(Long id, String name, double price, double weight, String description, String imageName,
			int categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.description = description;
		this.imageName = imageName;
		this.categoryId = categoryId;
	}

	// Generating getters and setters for the above menioned fields...
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	// Generating toString() method... 
	
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price + ", weight=" + weight + ", description="
				+ description + ", imageName=" + imageName + ", categoryId=" + categoryId + "]";
	}
	
	
}
