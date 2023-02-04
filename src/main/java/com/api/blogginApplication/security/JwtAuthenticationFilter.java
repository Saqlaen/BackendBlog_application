package com.api.blogginApplication.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomerUserDetailService customerUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// extract the token 
		String requestToken   = request.getHeader("Authorization"); // Authorization is the key
		
		// Bearer 2352523sdgsg
		System.out.println( requestToken );

		String username = null;
		String token = null;
		
		if( requestToken != null && requestToken.startsWith("Bearer ") ) {
			token = requestToken.substring(7);
			username = jwtTokenHelper.extractUsernameFromToken( token );
			
			if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
				
				UserDetails userDetails = customerUserDetailService.loadUserByUsername( username );
				
				if( jwtTokenHelper.validateToken( token, userDetails ) ) {
					// userpassword auth token 
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken( userDetails, null,  userDetails.getAuthorities() );
					
					SecurityContextHolder.getContext().setAuthentication( authToken );
					
				}
				else {
					System.out.println("invalid token" );
				}
			}
			else {
				System.out.println(" username or authentication not correct");
			}
		}
		else {
			System.out.println("problem in auth header" );
		}
		
		
		filterChain.doFilter(request, response);
		
		
		
	}

}
