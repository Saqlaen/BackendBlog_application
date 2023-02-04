package com.api.blogginApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogginApplication.services.UserService;
import com.api.blogginApplication.utils.UserRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/welcome")
	public ResponseEntity<?> welcome(){
		return new ResponseEntity("this endpoint is not secure", HttpStatus.OK );
	}
	
	//post 
	@PostMapping("/add-user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto userRequestDto ){
		this.userService.createUser(userRequestDto);		
		return new ResponseEntity<>("user had been added", HttpStatus.CREATED );
	}
	
	
	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers(){
		List<UserRequestDto> users = this.userService.getAllUsers();
		return new ResponseEntity<>( users, HttpStatus.OK );
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updatExistingUser(@Valid @RequestBody UserRequestDto userRequestDto ,@PathVariable("userid") Integer userId ){
		UserRequestDto userDto = this.userService.updateUser(userRequestDto, userId );
		return new ResponseEntity<>( userDto, HttpStatus.CREATED );
	}
	
	@GetMapping("/get-user/{userId}")
	public ResponseEntity<?> findUserById (@PathVariable("userId") Integer userid  ){
		UserRequestDto userdto = this.userService.getUserById(userid);
		return new ResponseEntity<>( userdto, HttpStatus.FOUND  );
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser (@PathVariable("userId") Integer userid  ){
		this.userService.deleteuser(userid);
		return new ResponseEntity<>( "user has been removed", HttpStatus.ACCEPTED  );
	}
	
	

}
