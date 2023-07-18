package com.bakery.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersQuery")
public class UserQuery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	private String email;
	private String number;
	private String query;
	
	// Generating constructors with and without Fields
	
	public UserQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserQuery(int id, String name, String email, String number, String query) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.number = number;
		this.query = query;
	}
	
	// Generating Getters and Setters
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	// Generating toString()
	
	@Override
	public String toString() {
		return "UserQuery [id=" + id + ", name=" + name + ", email=" + email + ", number=" + number + ", query=" + query
				+ "]";
	}		
	
}
