package com.bakery.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotEmpty
	@Column(nullable = false)
	private String firstName;
	
	private String lastName;
	
	@NotEmpty
	@Column(nullable = false, unique = true)	
	@Email(message = "{errors.invalid_email}")
	private String email;
	
	/* @NotEmpty */
	// Commenting out now because we are also including google authentication which will not require the password...
	
	
	private String password;
	
	
	
	// Creating roles field 
	
	// Many to Many mapping will be done as many users can have many roles and many roles can be assigned to many users... 
	// Cascading means that is we delete a Role then the users associated with the roles will also be removed... 
	// @JoinTable -> On the basis of primary key of one table and the foreign key of the other table, it merges the two tables... 
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			   joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName = "ID")},
			   inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName = "ID")} 
	)
	private List<Role> roles;

	// Generating constructor with and without fields...
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Note, that here we have manipulated our automatically generated constructor with fields... 
	
	public User(User user) {
		super();		
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	// Now generating our getters and setters...
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
