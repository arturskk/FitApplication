package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.BlogPostDTO;
import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.model.BlogPost;
import com.FitApka.model.ExercisePerformed;

@Component
public final class BlogPostToDTO implements Converter<BlogPost, BlogPostDTO>{

	 @Override
	    @Nullable
	    public BlogPostDTO convert(@Nullable final BlogPost blogPost) {
		 		BlogPostDTO  dto = null;
	        if (blogPost != null) {
	            dto = new BlogPostDTO();
	            dto.setId(blogPost.getId());
	            dto.setContent(blogPost.getContent());
	            dto.setDate(blogPost.getDate());
	            dto.setTitle(blogPost.getTitle());
	            dto.setSummary(blogPost.getSummary());
	            dto.setTags(blogPost.getTags());
	            dto.setCategory(blogPost.getCategory());
	        }
	        return dto;
	    }

}
