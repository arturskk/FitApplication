package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.BlogPostDTO;
import com.FitApka.DTO.CommentDTO;
import com.FitApka.DTO.ExerciseFoodnotesDTO;
import com.FitApka.model.BlogPost;
import com.FitApka.model.Comment;
import com.FitApka.model.ExerciseFoodnotes;

@Component
public final class ExerciseFoodnotesToDTO  implements Converter<ExerciseFoodnotes, ExerciseFoodnotesDTO>{


	 @Override
	    @Nullable
	    public ExerciseFoodnotesDTO convert(@Nullable final ExerciseFoodnotes exerciseFoodnotes) {
		 		ExerciseFoodnotesDTO  dto = null;
	        if (exerciseFoodnotes != null) {
	            dto = new ExerciseFoodnotesDTO();
	            dto.setId(exerciseFoodnotes.getId());
	            dto.setDate(exerciseFoodnotes.getDate());
	            dto.setContent(exerciseFoodnotes.getContent());
	            dto.setType(exerciseFoodnotes.getType());
	        }
	        return dto;
	    }

}
