package com.bakery.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bakery.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity	
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Injecting GoogleOAuth2SuccessHandler via @Autowired...
	
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
	// Injecting CustomUserDetailService via @Autowired...
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	// Overriding the configure method...
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		// Authentication Regarding Routes...
		// Then Authentication regarding our form logins...
		// Then Authentication regarding google form logins...
		// Then Authentication regarding our form logouts...
		// Then Handling the exceptions...
		// Then disabling the csrf... 
		
		http 
				.authorizeRequests()
				.antMatchers("/","/shop/**","/register","/forgot","/send-otp","/verify-otp","/change-password","/about/**","/contact/**","/processContactQuery/**","/h2-console/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.failureUrl("/login?error= true") 
				.defaultSuccessUrl("/")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.successHandler(googleOAuth2SuccessHandler)
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.exceptionHandling()
				.and()
				.csrf()
				.disable(); 
		
		// Creating the googleOAuth2SuccessHandler in this package only...
		
		// Adding this line currently for our H2 database...
		// But when we use any actual database in future, we will remove this below statement... 
		
		//http.headers().frameOptions().disable();		
		
	}
	
	// Now to handle our password in encrypted form we will declare a bean of BCryptPasswordEncoder...
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Overriding another method...
	// It is also a configure method, the difference can be observed in parameters... 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Need to create customUserDetailService now so as to use it below...
		auth.userDetailsService(customUserDetailService);
		
	}
	
	// Overriding another method...
	// It is also a configure method, the difference can be observed in parameters... 
	// Here it is also observed by access modifiers...
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		
		// Adding an additional security check for our static content...
		
		// Ignoring the specific routes...
		
		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
		
	} 
	
	
	
}
