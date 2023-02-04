package com.api.blogginApplication.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.blogginApplication.exceptions.ResourceNotFoundException;
import com.api.blogginApplication.modal.User;
import com.api.blogginApplication.repo.UserRepo;
import com.api.blogginApplication.services.UserService;
import com.api.blogginApplication.utils.UserRequestDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void createUser(UserRequestDto userdto) {
		User user = this.modelMapper.map( userdto, User.class );
		user.setPassword( passwordEncoder.encode( userdto.getPassword() ));
		this.userRepository.save( user );
	}

	@Override
	public UserRequestDto updateUser(UserRequestDto userdto, Integer userId) {
		
		User user = this.userRepository.findById(userId)
						.orElseThrow( () -> new ResourceNotFoundException( "user", "id" , userId) );
		
		user.setName( userdto.getName() );
		user.setEmail( userdto.getEmail() );
		user.setPassword( userdto.getPassword());
		
		User updatedUser = this.userRepository.save( user );
		
		return this.modelMapper.map(updatedUser, UserRequestDto.class );
	}

	@Override
	public UserRequestDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException( "user", "id" , userId) );

		return this.modelMapper.map(user, UserRequestDto.class );
	}

	@Override
	public List<UserRequestDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		
		List<UserRequestDto> usersDto = users.stream()
											 .map( user -> this.modelMapper.map(user, UserRequestDto.class ) )
											 .collect( Collectors.toList() );
		
		return usersDto;
	}

	@Override
	public void deleteuser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException( "user", "id" , userId) );

		this.userRepository.delete( user );
	}

}
