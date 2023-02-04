package com.api.blogginApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogginApplication.payloads.ApiResponse;
import com.api.blogginApplication.services.CommentService;
import com.api.blogginApplication.utils.CommentRequestDto;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{userId}/post/{postId}/add-comment")
	public ResponseEntity<?> createComment( @RequestBody CommentRequestDto dto,
											@PathVariable Integer userId,
											@PathVariable Integer postId){
		CommentRequestDto commnentsDto =  this.commentService.createComment(dto, postId, userId);
		return new ResponseEntity<CommentRequestDto>( commnentsDto , HttpStatus.CREATED );
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<?> createComment( @PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>( new ApiResponse( "the comment has been removed from the db", true ) , HttpStatus.CREATED );
	}
	
}
