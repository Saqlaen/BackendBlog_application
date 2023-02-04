package com.api.blogginApplication.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import com.api.blogginApplication.exceptions.ResourceNotFoundException;
import com.api.blogginApplication.modal.Category;
import com.api.blogginApplication.modal.Post;
import com.api.blogginApplication.modal.User;
import com.api.blogginApplication.payloads.PostResponse;
import com.api.blogginApplication.repo.CategoryRepo;
import com.api.blogginApplication.repo.PostsRepo;
import com.api.blogginApplication.repo.UserRepo;
import com.api.blogginApplication.services.PostsService;
import com.api.blogginApplication.utils.PostRequestDto;

@Service
public class PostServiceImpl implements PostsService {
	
	@Autowired
	private PostsRepo postsRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public PostRequestDto createPost(PostRequestDto dto, Integer userId, Integer catId) {
		
		User user = userRepo.findById( userId )
			    .orElseThrow( () -> new ResourceNotFoundException("User", "Userid", userId ) );
		
		Category cat = categoryRepo.findById(catId)
				.orElseThrow( () -> new ResourceNotFoundException("Category", "Categoryid", catId ) );
		
		Post post = this.modelMapper.map( dto, Post.class );
		post.setImageUrl("default.png");
		post.setDate( new Date() );
		
		post.setUser(user);
		post.setCategory(cat);
		
		Post newPost =  this.postsRepo.save( post );
		
		return this.modelMapper.map(newPost, PostRequestDto.class );
	}


	@Override 
	public PostRequestDto updatePost(PostRequestDto dto, Integer postId) {
		Post post = this.postsRepo.findById(postId).orElseThrow( () -> new ResourceNotFoundException("Post", "postId", postId) );
		
		post.setTitle( dto.getTitle() );
		post.setContent( dto.getContent() );
		post.setImageUrl( dto.getImgUrl() );
		
		Post updatedPost = this.postsRepo.save( post );
		
		return this.modelMapper.map(updatedPost, PostRequestDto.class );
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postsRepo.findById(postId)
				.orElseThrow( () -> new ResourceNotFoundException("Post", "postId", postId )  );
		this.postsRepo.delete(post);
		
	}

	@Override
	public PostRequestDto getById(Integer postid) {
		Post post = this.postsRepo.findById(postid)
				.orElseThrow( () -> new ResourceNotFoundException("Post", "postId", postid )  );
		return this.modelMapper.map(post, PostRequestDto.class);
	}

	@Override
	public PostResponse getAll(Integer pageNumber, Integer pageSize, 
			                    String sortby ,String sortDir) {
		
		Sort sort = ( sortDir.equalsIgnoreCase("asc") ) ? Sort.by(sortby).ascending() : Sort.by( sortby ).descending()  ;
		
		PageRequest p = PageRequest.of(pageNumber, pageSize, sort );
		Page<Post> pagePost = this.postsRepo.findAll( p );
		
		
		List<Post> postList = pagePost.getContent();
		List<PostRequestDto> dtoList = postList.stream().map( (post) -> this.modelMapper.map( post, PostRequestDto.class)  )
											   .collect( Collectors.toList() );
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(dtoList);
		postResponse.setPageNumber( pagePost.getNumber() );
		postResponse.setPageSize( pagePost.getSize() );
		postResponse.setTotalElements( pagePost.getTotalElements() );
		postResponse.setTotalPages( pagePost.getTotalPages() );
		postResponse.setLastPage( pagePost.isLast());
		 
		return postResponse; 
	}

	@Override
	public List<PostRequestDto> getPostBySearch(String keyword) {
		List<Post> listOfPosts = this.postsRepo.searchByTitle( keyword);
		List<PostRequestDto> dtoLists = listOfPosts.stream().map( post -> this.modelMapper.map( post, PostRequestDto.class))
										.collect( Collectors.toList() );
		return dtoLists;
	}


	@Override
	public List<PostRequestDto> getPostByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow( () -> new ResourceNotFoundException("Category", "CategoryId", categoryId) );
		List<Post> listOfPostByCategory = this.postsRepo.findByCategory(cat);
		List<PostRequestDto> dtoList = listOfPostByCategory.stream().map( post -> this.modelMapper.map(post, PostRequestDto.class ))
									   .collect( Collectors.toList() );
		return dtoList;
	}


	@Override
	public List<PostRequestDto> getPostbyUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				    .orElseThrow( () -> new ResourceNotFoundException("User", "Userid", userId) );
		List<Post> postByUser = this.postsRepo.findByUser(user);
		List<PostRequestDto> dtoList = postByUser.stream().map( post -> this.modelMapper.map(post, PostRequestDto.class ))
				   .collect( Collectors.toList() );
		return dtoList;
	}


	
}
