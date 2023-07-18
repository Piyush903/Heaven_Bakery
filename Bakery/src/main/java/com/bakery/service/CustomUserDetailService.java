package com.bakery.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bakery.entities.CustomUserDetail;
import com.bakery.entities.User;
import com.bakery.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	// Used also in SecurityConfig...	
	
	// Now adding all the unimplemented methods...
	// Just getting by mouse click over bulb...
	
	// Injecting UserRepository via @Autowired...
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		// Fetching the user by user name via userRepository...
		// Here our username is email...
		
		// Optional used because we might or might not recieve the user...
		Optional<User> user = userRepository.findUserByEmail(email);
		
		//Handling Exception if we do not get user...
		user.orElseThrow(() -> new UsernameNotFoundException("We did not found the user..."));
		
		// But if we get User we will return...
		// Our SpringSecurity focuses on two things the username which is here our email and password...
		return user.map(CustomUserDetail::new).get();		
	
	}	
	
}
