package com.api.blogginApplication.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.blogginApplication.config.AppConstants;
import com.api.blogginApplication.payloads.ApiResponse;
import com.api.blogginApplication.payloads.PostResponse;
import com.api.blogginApplication.services.FileService;
import com.api.blogginApplication.services.PostsService;
import com.api.blogginApplication.utils.PostRequestDto;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/add-post")
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto , @PathVariable Integer userId, 
			 							@PathVariable("categoryId") Integer categoryId){
		
		PostRequestDto postDto = this.postsService.createPost(dto, userId, categoryId);
		return new ResponseEntity< PostRequestDto >( postDto,HttpStatus.CREATED );
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> createPost(@PathVariable Integer postId){
		
		PostRequestDto postDto = this.postsService.getById(postId);
		return new ResponseEntity< PostRequestDto >( postDto,HttpStatus.OK );
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<?> getByUser(@PathVariable Integer userId){
		
		List<PostRequestDto> dtoList = this.postsService.getPostbyUser(userId);
		return new ResponseEntity<List<PostRequestDto>>( dtoList, HttpStatus.FOUND);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<?> getByCategory(@PathVariable Integer catId){
		
		List<PostRequestDto> dtoList = this.postsService.getPostByCategory( catId );
		return new ResponseEntity<List<PostRequestDto>>( dtoList, HttpStatus.FOUND);
	}
	
	@GetMapping("/all-posts")
	public ResponseEntity<?> getAll(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required =false) Integer pageNumber,
			@RequestParam( value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam( value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value ="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
		
		PostResponse dtoList = this.postsService.getAll( pageNumber, pageSize, sortBy, sortDir );
		return new ResponseEntity<PostResponse >( dtoList, HttpStatus.FOUND);
	}
	
	@DeleteMapping("delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId ){
		 
		this.postsService.deletePost(postId);
		return new ResponseEntity<ApiResponse>( new ApiResponse("deleted the post from the database", true), HttpStatus.FOUND);
	}
	
	@GetMapping("/post-by-title/{keyword}")
	public ResponseEntity<?> searchByTitle(@PathVariable String keyword ){
		 
		List<PostRequestDto> dtoPostLists =  this.postsService.getPostBySearch("%"+keyword+"%");
		return new ResponseEntity< List<PostRequestDto> >( dtoPostLists, HttpStatus.FOUND);
	}
	
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<?> uploadImg(@RequestParam("image") MultipartFile image,
								   	   @PathVariable Integer postId) throws IOException{
		
		PostRequestDto dto = this.postsService.getById(postId);
		String fileName =  this.fileService.uploadImage( path, image );
		dto.setImgUrl(fileName);
		
		PostRequestDto updatedPostDto = this.postsService.updatePost(dto, postId);
		
		return new ResponseEntity< PostRequestDto >( updatedPostDto, HttpStatus.OK );
	}
	
	@GetMapping( value = "/getImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage( @PathVariable String imageName,
			         		   HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType( MediaType.IMAGE_JPEG_VALUE );
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream() );
	}
	
	
	
	
}
 