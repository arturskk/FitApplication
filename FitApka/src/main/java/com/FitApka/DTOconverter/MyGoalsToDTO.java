package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.ExerciseDTO;
import com.FitApka.DTO.ExerciseFoodnotesDTO;
import com.FitApka.DTO.MyGoalsDTO;
import com.FitApka.model.Exercise;
import com.FitApka.model.ExerciseFoodnotes;
import com.FitApka.model.MyGoals;

@Component
public class MyGoalsToDTO implements Converter<MyGoals, MyGoalsDTO> {

	 @Override
	    @Nullable
	    public  MyGoalsDTO convert(@Nullable final  MyGoals myGoals) {
		 		MyGoalsDTO  dto = null;
	        if (myGoals != null) {
	            dto = new  MyGoalsDTO();
	            dto.setId(myGoals.getId());
	            dto.setKilograms(myGoals.getKilograms());
	            dto.setCarbohydrates(myGoals.getCarbohydrates());
	            dto.setFat(myGoals.getFat());
	            dto.setProtein(myGoals.getProtein());
	        }
	        return dto;
	    }

}
