package com.api.blogginApplication.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.blogginApplication.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler( ResourceNotFoundException ex ){
		String message = ex.getMessage();
		ApiResponse apiresponse = new ApiResponse();
		apiresponse.setMessage(message);
		apiresponse.setSuccess(false);
		return new ResponseEntity<>( apiresponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler( MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String> > handleMethodArgNotValidExeption( MethodArgumentNotValidException ex){
		Map<String, String > response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach( (error) -> {
			String fieldname = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			response.put( fieldname, errorMessage );
		});
		
		return new ResponseEntity<>( response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler( AuthException.class )
	public ResponseEntity<?> handleAuthException( AuthException ex  ){
		String message = ex.getMessage();
		ApiResponse apiresponse = new ApiResponse();
		apiresponse.setMessage(message);
		apiresponse.setSuccess(true);
		return new ResponseEntity<>( apiresponse, HttpStatus.BAD_REQUEST);
	}
}
 