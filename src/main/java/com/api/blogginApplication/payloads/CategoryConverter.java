package com.api.blogginApplication.payloads;

import com.api.blogginApplication.modal.Category;
import com.api.blogginApplication.utils.CategoryRequestDto;

public class CategoryConverter {
	
	public static Category convertDtotoEntity(CategoryRequestDto dto ) {
		Category cat = Category.builder().categoryId( dto.getCategoryId() )
										 .categoryTitle( dto.getCategoryTitle() )
										 .categoryDescription( dto.getCategoryDescription() )
										 .build();
		return cat;
	}
	
	public static CategoryRequestDto convertEntityToDto( Category obj ) {
		CategoryRequestDto dto = CategoryRequestDto.builder().categoryId( obj.getCategoryId() )
													.categoryTitle( obj.getCategoryTitle() )
													.categoryDescription( obj.getCategoryDescription() )
													.build();
		return dto;
	}

}
