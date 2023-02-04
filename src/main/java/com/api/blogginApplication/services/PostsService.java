package com.api.blogginApplication.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.api.blogginApplication.payloads.PostResponse;
import com.api.blogginApplication.utils.PostRequestDto;

@Service
public interface PostsService {
	
	//create 
	PostRequestDto createPost( PostRequestDto dto, Integer userId, Integer catId );
	
	//update 
	PostRequestDto updatePost( PostRequestDto dto, Integer postId );
	
	//delete
	void deletePost( Integer postId );
	//get 
	PostRequestDto getById( Integer postid );
	//getAll
	PostResponse getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//Search
	List<PostRequestDto> getPostBySearch( String keyword );
	
	//get post by category
	List<PostRequestDto> getPostByCategory( Integer categoryId );
	
	//get post by user
	List<PostRequestDto> getPostbyUser( Integer userId );
}
