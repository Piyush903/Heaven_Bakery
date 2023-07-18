package com.bakery.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail extends User implements UserDetails {

	// ** Now uncommented all the remaining dependencies of security in our pom.xml to get UserDetails...
	// ** Now, simply clicked the bulb and added all the unimplemented methods...
	// ** Also, wherever return is false making it true...
	// ** Wherever null is there, return the get method as desired inside that...
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		// Creating List of type GrantedAuthority
		
		List<GrantedAuthority> authorityList = new ArrayList<>();
		
		// Adding the roles from the role entity of the user as SimpleGrantedAuthority in our authorityList array.
		
		super.getRoles().forEach(role -> {
			authorityList.add(new SimpleGrantedAuthority(role.getName()));
		});
				
		return authorityList;
		
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		
		// Here we will be using Username as our email...
		
		return super.getEmail(); 

	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}		
	
	// Adding manually one more method...
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}
	
	// Also, creating the constructor with fields manually...
	
	public CustomUserDetail(User user) {
		
		// Passing the user object to the parent constructor...
		
		super(user);
		
	}
	
	
}
