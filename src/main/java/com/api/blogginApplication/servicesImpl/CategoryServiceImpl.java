package com.api.blogginApplication.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blogginApplication.exceptions.ResourceNotFoundException;
import com.api.blogginApplication.modal.Category;
import com.api.blogginApplication.payloads.CategoryConverter;
import com.api.blogginApplication.repo.CategoryRepo;
import com.api.blogginApplication.services.CategoryService;
import com.api.blogginApplication.utils.CategoryRequestDto;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryRequestDto createCategory(CategoryRequestDto dto) {
		Category category =  CategoryConverter.convertDtotoEntity(dto);
		Category addedCategory = this.categoryRepo.save( category );
		return CategoryConverter.convertEntityToDto(addedCategory);
	}
	
	@Override
	public CategoryRequestDto updateCategory(CategoryRequestDto dto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
							            .orElseThrow( () -> new ResourceNotFoundException("Category", "CategoryId", categoryId) );
		return CategoryConverter.convertEntityToDto(cat);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
					  .orElseThrow( () -> new ResourceNotFoundException("category", "categoryId", categoryId  ));
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryRequestDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				  .orElseThrow( () -> new ResourceNotFoundException("category", "categoryId", categoryId  ));
		return CategoryConverter.convertEntityToDto(cat);
	}

	@Override
	public List<CategoryRequestDto> getAllCategory() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryRequestDto> dtoList = categoryList.stream()
										   .map( category -> CategoryConverter.convertEntityToDto(category) )
										   .collect( Collectors.toList() );
		return dtoList;
	}


}
