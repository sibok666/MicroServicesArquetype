package org.gmm.ratingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	   @Autowired
	   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		   auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		   auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		   auth.inMemoryAuthentication().withUser("discUser").password("discPassword").roles("SYSTEM");
	   }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().disable().authorizeRequests()
	      .antMatchers("/ratings").hasRole("USER")
	      .antMatchers("/ratings/all").hasAnyRole("USER", "ADMIN").anyRequest()
	      .authenticated().and().csrf().disable();
	}
}