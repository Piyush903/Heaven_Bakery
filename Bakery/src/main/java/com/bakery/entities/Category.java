package com.bakery.entities;

// Models are basically Entities...

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
public class Category {
	
	/* @Data */
	/*
	  We have been using @Data which is comming from lombok which helps in giving necessary
	  things for our data fields, like we need not create the getters and setters for each
	  and every field and many more things... 
	 */
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private int id;
	
	private String name;

	// Creating Getters and Setters for the above fields...

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Generating Constructors with and without fields...
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	// Generating toString()
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
		
	
	
}
