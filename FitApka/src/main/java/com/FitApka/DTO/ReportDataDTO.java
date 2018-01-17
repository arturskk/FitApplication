package com.FitApka.DTO;

import java.sql.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class ReportDataDTO {

	   private Integer id;
	   private Integer userId;
	   private Date date;
	   private Integer netCalories;
	   private Double netPoints;
	   private Double kilograms;
	
	
	   public ReportDataDTO(
	            @Nullable final Integer id,
	            @Nullable final Integer userid,
	            @Nonnull final Date date,
	            final double kilograms,
	            final int netCalories,
	            final double netPoints
	    ) {
	        this.id = id;
	        this.userId = userid;
	        this.date = (Date) date.clone();
	        this.kilograms = kilograms;
	        this.netCalories =  netCalories;
	        this.netPoints = netPoints;
	    }
		
		public ReportDataDTO() {
	    }

		
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
		public Integer getNetCalories() {
			return netCalories;
		}

		public void setNetCalories(@Nonnull final Integer netCalories) {
			this.netCalories = netCalories;
		}

		@Nonnull 
		public Double getNetPoints() {
			return netPoints;
		}

		public void setNetPoints(@Nonnull final Double netPoints) {
			this.netPoints = netPoints;
		}

		@Nonnull 
		public Double getKilograms() {
			return kilograms;
		}

		public void setKilograms(@Nonnull final Double kilograms) {
			this.kilograms = kilograms;
		}

		@Nonnull 
		public Integer getUserId() {
			return userId;
		}

		public void setUserId(@Nullable final Integer userId) {
			this.userId = userId;
		}
		
		
		
		
	   
	   
	   
	   
	   
}
