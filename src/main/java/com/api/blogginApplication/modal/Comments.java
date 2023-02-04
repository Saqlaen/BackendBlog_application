package com.api.blogginApplication.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer commentId;
	private String content;
	
	@ManyToOne
	private User user;

	@ManyToOne
	private Post post;
	
}
