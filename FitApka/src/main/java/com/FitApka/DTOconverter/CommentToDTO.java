package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.FitApka.DTO.CommentDTO;
import com.FitApka.model.Comment;

@Component
public final class CommentToDTO implements Converter<Comment, CommentDTO> {

	BlogPostToDTO blogPostConverterDTO;
	
	@Autowired
	public CommentToDTO(
			BlogPostToDTO blogPostConverterDTO
			)
	{
		this.blogPostConverterDTO = blogPostConverterDTO;
	}
		
	 @Override
	    @Nullable
	    public CommentDTO convert(@Nullable final Comment comment) {
		 		CommentDTO  dto = null;
	        if (comment != null) {
	            dto = new CommentDTO();
	            dto.setId(comment.getId());
	            dto.setContent(comment.getContent());
	            dto.setDate(comment.getDate());
	            dto.setBlogPostDTO(blogPostConverterDTO.convert(comment.getBlogPost()));
	        }
	        return dto;
	    }

}
