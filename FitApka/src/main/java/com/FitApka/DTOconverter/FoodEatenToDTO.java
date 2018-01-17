package com.FitApka.DTOconverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.FoodEatenDTO;
import com.FitApka.model.FoodEaten;



@Component
public final class FoodEatenToDTO implements Converter<FoodEaten, FoodEatenDTO>  {

	  private final FoodToDTO foodDTOConverter;

	    @Autowired
	    public FoodEatenToDTO(@Nonnull final FoodToDTO foodDTOConverter) {
	        this.foodDTOConverter = foodDTOConverter;
	    }

	    @Override
	    @Nullable
	    public FoodEatenDTO convert(@Nullable final FoodEaten foodEaten) {
	        FoodEatenDTO dto = null;
	        if (foodEaten != null) {
	            dto = new FoodEatenDTO(
	                    foodEaten.getId(),
	                    foodEaten.getUser().getId(),
	                    foodDTOConverter.convert(foodEaten.getFood()),
	                    foodEaten.getDate(),
	                    foodEaten.getServingType(),
	                    foodEaten.getServingQty(),
	                    foodEaten.getCalories(),
	                    foodEaten.getFat(),
	                    foodEaten.getSaturatedFat(),
	                    foodEaten.getSodium(),
	                    foodEaten.getCarbs(),
	                    foodEaten.getFiber(),
	                    foodEaten.getSugar(),
	                    foodEaten.getProtein(),
	                    foodEaten.getPoints()
	            );
	        }
	        return dto;
	    }
   
    
	
}
