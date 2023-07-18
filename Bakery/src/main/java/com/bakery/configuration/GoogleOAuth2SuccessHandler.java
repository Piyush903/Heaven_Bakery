package com.bakery.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ***** Correct imports for RedirectStrategy and DefaultRedirectStrategy ***** \\
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.DefaultRedirectStrategy;
// ***** Correct imports for RedirectStrategy and DefaultRedirectStrategy ***** \\

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bakery.entities.Role;
import com.bakery.entities.User;
import com.bakery.repository.RoleRepository;
import com.bakery.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{

	// Used also in SecurityConfig...
	
	// Creating first UserRepository...
	// Then creating RoleRepository...
	
	// Now injecting the UserRepository and RoleRepository via @Autowired
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	// Also implementing the unimplemented methods of AuthenticationSuccessHandler
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// We will be recieving a token from the authentication object...
		// This token is provided by the google saying that it knows the user...
		
		// We will be casting this authentication in the OAuthToken...
		
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		
		// Getting email from the token...
		
		String email = token.getPrincipal().getAttributes().get("email").toString();
		
		if(userRepository.findUserByEmail(email).isPresent()) {
			
		} else {
			
			// If we do not get the email from the userRepository...
			// Means that user does not exists in our database...
			// So, we need to add that user to our database...
			
			// Creating a new user object...			
			User user = new User();
			
			// Setting up details of the user...
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			
			// Here the get attribute key is taken from google...
			
			
			// As we fetched the email already above...
			user.setEmail(email);
						
			// We need not to fetch the password here as we are having Google Authentication
			
			// Setting up the roles...
			// We will set the user role by default...
			// We will be considering such pattern that 1-> Admin and 2-> User...
			
			List<Role> roles = new ArrayList<>();			
			roles.add(roleRepository.findById(2).get()); // Here, 2 means User...
			user.setRoles(roles); 
			
			// saving this user to our database via userRepository...
			
			userRepository.save(user);
					
		}
		
		// Now redirecting our handler to a view...
		
		redirectStrategy.sendRedirect(request, response, "/");				
				
	} 	
	
}
