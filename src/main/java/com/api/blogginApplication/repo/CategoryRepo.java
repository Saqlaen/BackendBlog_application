package com.api.blogginApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blogginApplication.modal.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
