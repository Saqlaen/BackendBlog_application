package com.api.blogginApplication.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4, message="Username must be min of 4 charactters")
	private String name;
	
	@NotEmpty
	@Size( min=3, max=10, message = "password must be min of 3 char and max of 10 chars")
//	@Pattern(regexp = )
	private String password;
	
	@Email(message = "email address is not valid ")
	private String email;
}
