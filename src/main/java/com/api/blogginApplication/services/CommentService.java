package com.api.blogginApplication.services;

import org.springframework.stereotype.Service;

import com.api.blogginApplication.utils.CommentRequestDto;

@Service
public interface CommentService {
	
	CommentRequestDto createComment ( CommentRequestDto dto, Integer postId, Integer userId );
	
	void deleteComment( Integer commentId );
	
	

}
