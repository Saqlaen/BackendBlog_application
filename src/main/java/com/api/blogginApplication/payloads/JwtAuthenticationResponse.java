package com.api.blogginApplication.payloads;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	
	private String token;
}
