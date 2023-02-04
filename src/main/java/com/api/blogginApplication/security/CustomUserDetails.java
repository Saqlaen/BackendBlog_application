package com.api.blogginApplication.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.blogginApplication.modal.Role;
import com.api.blogginApplication.modal.User;

public class CustomUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 692577257630506501L;
	private String name;
	private String password;
	private Set<Role> roles = new HashSet<>();
	private List<GrantedAuthority> authorites = new ArrayList<>();
	
	public CustomUserDetails(User user ) {
		this.name = user.getEmail();
		this.password = user.getPassword();
		this.roles  = user.getRoles();
		this.authorites = this.roles.stream().map( 
				(role) -> new SimpleGrantedAuthority( role.getName() ) 
				               )
				   .collect( Collectors.toList() );
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
