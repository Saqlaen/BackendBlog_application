package com.api.blogginApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blogginApplication.payloads.ApiResponse;
import com.api.blogginApplication.services.CategoryService;
import com.api.blogginApplication.utils.CategoryRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add-category")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto dto ){
		CategoryRequestDto catDto = this.categoryService.createCategory(dto);
		return new ResponseEntity<CategoryRequestDto>( catDto, HttpStatus.CREATED );
	}
	
	@PutMapping("/update-category/{catId}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRequestDto dto, @PathVariable Integer catId ){
		CategoryRequestDto catDto =	this.categoryService.updateCategory(dto, catId);
		return new ResponseEntity<CategoryRequestDto>( catDto, HttpStatus.OK );
	}
	
	@DeleteMapping("/delete/{catId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer catId ){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>( new ApiResponse("user deleted from the records", true), HttpStatus.OK );
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryRequestDto> dtoLists =  this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryRequestDto>>( dtoLists, HttpStatus.OK );
	}
	
	@GetMapping("/get/{catId}")
	public ResponseEntity<?> getCategory(@PathVariable Integer catId){
		CategoryRequestDto dto =  this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryRequestDto>( dto, HttpStatus.OK );
	}
}
