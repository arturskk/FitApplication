package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.CommentDTO;
import com.FitApka.DTO.ExerciseDTO;
import com.FitApka.DTO.PostDTO;
import com.FitApka.model.Comment;
import com.FitApka.model.Exercise;
import com.FitApka.model.Post;

@Component
public class PostToDTO implements Converter<Post, PostDTO> {

	 @Override
	    @Nullable
	    public PostDTO convert(@Nullable final Post post) {
		 PostDTO  dto = null;
	        if (post != null) {
	            dto = new PostDTO();
	            dto.setId(post.getId());
	            dto.setContent(post.getContent());
	            dto.setDate(post.getDate());
	        }
	        return dto;
	    }

}
