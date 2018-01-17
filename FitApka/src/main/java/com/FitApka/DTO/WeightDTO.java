package com.FitApka.DTO;

import java.sql.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class WeightDTO {

	private Integer id;
	private Date date;
	private Double kilograms;
	
	
	public WeightDTO(
			@Nullable final Integer id,
			@Nonnull final Date date,
			@Nonnull final double kilograms
			)
	{
		this.id = id;
		this.date = (Date) date.clone();
		this.kilograms = kilograms;
		
	}
	
	
	
	public WeightDTO() 
	{}

	@Nullable
	public Integer getId() {
		return id;
	}

	public void setId(@Nonnull final Integer id) {
		this.id = id;
	}

	@Nonnull
	public Date getDate() {
		return date;
	}

	public void setDate(@Nonnull final Date date) {
		this.date = date;
	}

	@Nonnull
	public Double getKilograms() {
		return kilograms;
	}

	public void setKilograms(@Nonnull final Double kilograms) {
		this.kilograms = kilograms;
	}
	
	
	
	
}
