package com.api.blogginApplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.api.blogginApplication.security.CustomerUserDetailService;
import com.api.blogginApplication.security.JwtAuthenticationEntryPoint;
import com.api.blogginApplication.security.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomerUserDetailService customerUserDetailService;
	
	@Autowired 
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private static final String[] APIS = { 
			"/user/add-user" ,
			"/swagger-resources",
			"/api/v1/auth/login",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**",
			"/webjars/**",
			"/user/welcome"
			
	};
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
		
		return http
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers( APIS ).permitAll()
			.and()
			.authorizeHttpRequests()
			.requestMatchers(HttpMethod.GET).permitAll()
			.and()
			.authorizeHttpRequests()
			// .requestMatchers("/user/**","/post/**","/comment/**","/category/**")
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint( jwtAuthenticationEntryPoint )
			.and()
			.sessionManagement()
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS )
			.and()
			.authenticationProvider( authenticationProvider() )
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
			.build();
	}	
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService( userDetailsService()  );
		provider.setPasswordEncoder( passwordEncoder() );
		return provider;
	}
		
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailService();
	}
	
	@Bean
	public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration ) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
