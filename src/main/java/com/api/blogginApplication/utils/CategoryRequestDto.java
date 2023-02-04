package com.api.blogginApplication.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
	
	
	private int categoryId;
	@NotBlank
	@Size( min=4, message = "min size of the title should be 4")
	private String categoryTitle;
	@NotBlank
	@Size( min=10, message = "min size of the description should be 10")
	private String categoryDescription;
	
	
}
