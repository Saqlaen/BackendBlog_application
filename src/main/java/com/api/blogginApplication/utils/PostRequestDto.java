package com.api.blogginApplication.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

	private Integer postId;
	private String title;
	private String content;
	private String imgUrl;
	private Date date;
	private UserRequestDto user;
	private CategoryRequestDto category;
	private Set<CommentRequestDto> comments = new HashSet<>();
	
}
