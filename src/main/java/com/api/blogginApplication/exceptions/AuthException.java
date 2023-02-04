package com.api.blogginApplication.exceptions;

public class AuthException extends RuntimeException {
	
	private String message;
	private boolean success;
	
	public AuthException(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	public AuthException() {
		super();
	}
	
	

}
