package org.gmm.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1)
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
	      .antMatchers("/books").permitAll()
	      .antMatchers("/books/*").hasAnyRole("USER", "ADMIN").anyRequest()
	      .authenticated().and().csrf().disable();
	}
	
}