package com.bakery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bakery.entities.Role;
import com.bakery.entities.User;
import com.bakery.global.GlobalData;
import com.bakery.repository.RoleRepository;
import com.bakery.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	// Handler for login page...
	
	@GetMapping("/login")
	public String login() {
	
		// To clear the cart when the user logins 
		// ArrayList<>() clear function is used here...
		GlobalData.cart.clear();
		
		return "login";

	}
	
	// Handler for register page... 
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	// Handler for processing the register page...
	
	@PostMapping("/register")
	public String saveUser(@ModelAttribute("user") User user,HttpServletRequest request) throws ServletException {
		
		// We get the entire user data in user object via @ModelAttribute...
		// Now we need to store it in our database...
		// Before that as we know that password of user will be saved in encrypted form in database...
		// So, we need to first encode the password of user and then set it in the user object and then only proceed...
		// Also, the user role will be inserted herein only and will not come from our register form...
		
		// Password 
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		
		// Role
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		
		// Then finally saving our user to our database via userRepository...
		
		userRepository.save(user);
		
		// After this we will automatically login the user and move to home page...
		// HttpServletRequest will help us to login the user... 
		
		request.login(user.getEmail(), password);
		
		// NOTE -> We will enter the normal password while login in as we can see in arguments above...
		
		return "redirect:/";
		
	}	
	
	
	
	
	
	
}
