package com.api.blogginApplication.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.blogginApplication.utils.UserRequestDto;

@Service
public interface UserService {
	
	public void createUser( UserRequestDto user);
	public UserRequestDto updateUser( UserRequestDto user, Integer userId );
	public UserRequestDto getUserById( Integer userId );
	public List<UserRequestDto> getAllUsers();
	public void deleteuser( Integer userid );
}
