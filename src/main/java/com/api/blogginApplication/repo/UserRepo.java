package com.api.blogginApplication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogginApplication.modal.User;

public interface UserRepo extends JpaRepository<User,Integer> {
	
	Optional<User> findByEmail(String email);

}
