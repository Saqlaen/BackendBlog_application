package com.api.blogginApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogginApplication.modal.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
