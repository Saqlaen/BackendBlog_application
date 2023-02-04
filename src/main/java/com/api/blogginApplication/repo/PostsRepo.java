package com.api.blogginApplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.blogginApplication.modal.Category;
import com.api.blogginApplication.modal.Post;
import com.api.blogginApplication.modal.User;

public interface PostsRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser( User user );
	List<Post> findByCategory( Category category);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
}
