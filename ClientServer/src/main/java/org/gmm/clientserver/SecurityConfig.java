package org.gmm.clientserver;

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
       auth.inMemoryAuthentication().withUser("discUser")
         .password("discPassword").roles("SYSTEM");
       
       auth.inMemoryAuthentication().withUser("admin")
       .password("admin").roles("ADMIN");
       
       
       
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
         .and().requestMatchers().antMatchers("/eureka/**")
         .and().authorizeRequests().antMatchers("/eureka/**")
         .hasRole("SYSTEM").anyRequest().denyAll().and()
         .httpBasic().and().csrf().disable();
   }
   
   @Configuration
   public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().httpBasic().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/").hasRole("ADMIN")
        .antMatchers("/info", "/health").authenticated().anyRequest() 
        .denyAll().and().csrf().disable();
      }
   }
}
