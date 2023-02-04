package com.api.blogginApplication.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.blogginApplication.utils.CategoryRequestDto;

@Service
public interface CategoryService {

	//create
	CategoryRequestDto createCategory( CategoryRequestDto dto );
	//update
	CategoryRequestDto updateCategory( CategoryRequestDto dto, Integer categoryId );
	//delete
	void deleteCategory( Integer categoryId );
	//getbyid
	CategoryRequestDto getCategory( Integer categoryId );
	//get all
	List<CategoryRequestDto> getAllCategory();
	
	
}
