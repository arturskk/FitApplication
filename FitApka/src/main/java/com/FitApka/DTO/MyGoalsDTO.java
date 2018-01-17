package com.FitApka.DTO;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.FitApka.model.FitnessUser;

public final class MyGoalsDTO implements Serializable {

	   private Integer id;
	   private Double kilograms;
	   private Double carbohydrates;
	   private Double fat;
	   private Double protein;
	   
	   
	   public MyGoalsDTO(
			    @Nullable final Integer id,
				@Nonnull final Double kilograms,
				@Nonnull final Double carbohydrates,
				@Nonnull final Double fat,
				@Nonnull final Double protein,
				@Nonnull final FitnessUser user
				) 
		{
			this.id = id;
			this.kilograms = kilograms;
			this.carbohydrates = carbohydrates;
			this.fat = fat;
			this.protein = protein;
			
		}
		
		public MyGoalsDTO() {}

		
		@Nullable
		public Integer getId() {
			return id;
		}

		public void setId(@Nonnull final Integer id) {
			this.id = id;
		}

		@Nonnull
		public Double getKilograms() {
			return kilograms;
		}

		public void setKilograms(@Nonnull final Double kilograms) {
			this.kilograms = kilograms;
		}

		@Nonnull
		public Double getCarbohydrates() {
			return carbohydrates;
		}

		public void setCarbohydrates(@Nonnull final Double carbohydrates) {
			this.carbohydrates = carbohydrates;
		}

		@Nonnull
		public Double getFat() {
			return fat;
		}

		public void setFat(@Nonnull final Double fat) {
			this.fat = fat;
		}

		@Nonnull
		public Double getProtein() {
			return protein;
		}

		public void setProtein(@Nonnull final Double protein) {
			this.protein = protein;
		}
		
		
		
		

	
	
}
