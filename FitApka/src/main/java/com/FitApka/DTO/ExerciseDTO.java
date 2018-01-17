package com.FitApka.DTO;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ExerciseDTO  implements Serializable{

	
	 	private Integer id;
	    private String code;
	    private Double metabolicEquivalent;
	    private String category;
	    private String description;
	
	
	    public ExerciseDTO(
	            @Nullable final Integer id,
	            @Nonnull final String code,
	            final double metabolicEquivalent,
	            @Nonnull final String category,
	            @Nonnull final String description
	    ) {
	        this.id = id;
	        this.code = code;
	        this.metabolicEquivalent = metabolicEquivalent;
	        this.category = category;
	        this.description = description;
	    }

	    public ExerciseDTO() {
	    }
	    
	    @Nullable
	    public Integer getId() {
	        return id;
	    }

	    public void setId(@Nonnull final Integer id) {
	        this.id = id;
	    }

	    @Nonnull
	    public String getCode() {
	        return code;
	    }

	    public void setCode(@Nonnull final String code) {
	        this.code = code;
	    }

	    @Nonnull
	    public Double getMetabolicEquivalent() {
	        return metabolicEquivalent;
	    }

	    public void setMetabolicEquivalent(@Nonnull final Double metabolicEquivalent) {
	        this.metabolicEquivalent = metabolicEquivalent;
	    }

	    @Nonnull
	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(@Nonnull final String category) {
	        this.category = category;
	    }

	    @Nonnull
	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(@Nonnull final String description) {
	        this.description = description;
	    }
	
	
}
