package com.api.blogginApplication.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blogginApplication.exceptions.ResourceNotFoundException;
import com.api.blogginApplication.modal.Comments;
import com.api.blogginApplication.modal.Post;
import com.api.blogginApplication.modal.User;
import com.api.blogginApplication.repo.CommentsRepo;
import com.api.blogginApplication.repo.PostsRepo;
import com.api.blogginApplication.repo.UserRepo;
import com.api.blogginApplication.services.CommentService;
import com.api.blogginApplication.utils.CommentRequestDto;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostsRepo postsRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentsRepo commentsRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentRequestDto createComment(CommentRequestDto dto ,Integer postId, Integer userId) {
		Post post = this.postsRepo.findById( postId )
				        .orElseThrow( () -> new ResourceNotFoundException("Post", "postid", postId ) );
		User user = this.userRepo.findById( userId )
		        .orElseThrow( () -> new ResourceNotFoundException("User", "userid", userId ) );
		Comments comment = this.modelMapper.map( dto, Comments.class );
		comment.setPost(post);
		comment.setUser(user);
		
		this.commentsRepo.save( comment );
		return this.modelMapper.map(comment, CommentRequestDto.class );
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comment  = this.commentsRepo.findById(commentId)
							.orElseThrow( () -> new ResourceNotFoundException("comment", "commentId", commentId ) );
		this.commentsRepo.delete(comment);
	}
	
	
}
