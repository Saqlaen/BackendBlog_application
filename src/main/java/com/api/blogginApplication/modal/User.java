package com.api.blogginApplication.modal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private int id;
	
	@Column( name="user_name", nullable=false, length=100)
	private String name;
	
	@Column( nullable=false, unique=true)
	private String email;
	
	@Column( nullable=false)
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Post> posts  = new ArrayList<>();
	

	@OneToMany(mappedBy = "user")
	private List<Comments> comments = new ArrayList<>();
	
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable( name="user_role", 
				joinColumns = @JoinColumn( name="user", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "roleId")
			)
	private Set<Role> roles = new HashSet<>();


}
