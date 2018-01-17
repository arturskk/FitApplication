package com.FitApka.model;



import java.sql.Timestamp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
		name = "food"
		)
	
public class Food {
	
	public enum ServingType {

		CUP(8), KILOGRAMS(35.27), TABLESPOON(0.5), TEASPOON(0.1667), GRAM(0.03527), CUSTOM(0);

        private double value;

        private ServingType(final double value) {
            this.value = value;
        }

        @Nullable
        public static ServingType fromValue(final double value) {
            ServingType match = null;
            for (final ServingType servingType : ServingType.values()) {
                if (servingType.getValue() == value) {
                    match = servingType;
                }
            }
            return match;
        }

        @Nullable
        public static ServingType fromString(@Nonnull final String s) {
            ServingType match = null;
            for (final ServingType servingType : ServingType.values()) {
                if (servingType.toString().equalsIgnoreCase(s)) {
                    match = servingType;
                }
            }
            return match;
        }

        public final double getValue() {
            return this.value;
        }

    }
	
	

	
	
	
	
		@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id", columnDefinition = "INT")
		private Integer id;
	
	 	@ManyToOne
	    @JoinColumn(name = "owner_id", nullable = true)
	    private FitnessUser owner;

	    @Column(name = "name", length = 50, nullable = false)
	    private String name;
	    

	    @Column(name = "default_serving_type", length = 10, nullable = false)
	    @Enumerated(EnumType.STRING)
	    private ServingType defaultServingType;

	    @Column(name = "serving_type_qty", nullable = false)
	    private Double servingTypeQty;

	    @Column(name = "calories", nullable = false)
	    private Integer calories;

	    @Column(name = "fat", nullable = false)
	    private Double fat;

	    @Column(name = "saturated_fat", nullable = false)
	    private Double saturatedFat;

	    @Column(name = "carbs", nullable = false)
	    private Double carbs;

	    @Column(name = "fiber", nullable = false)
	    private Double fiber;

	    @Column(name = "sugar", nullable = false)
	    private Double sugar;

	    @Column(name = "protein", nullable = false)
	    private Double protein;

	    @Column(name = "sodium", nullable = false)
	    private Double sodium;

	    @Column(name = "created_time", nullable = false)
	    private Timestamp createdTime = new Timestamp(new java.util.Date().getTime());

	    @Column(name = "last_updated_time", nullable = false)
	    private Timestamp lastUpdatedTime = new Timestamp(new java.util.Date().getTime());

	
	    
	    
	    
	    @Nullable
	public Integer getId() {
			return id;
		}



		public void setId(@Nonnull final Integer id) {
			this.id = id;
		}


		@Nonnull
		public FitnessUser getOwner() {
			return owner;
		}



		public void setOwner(@Nonnull final FitnessUser owner) {
			this.owner = owner;
		}


		@Nonnull
		public String getName() {
			return name;
		}



		public void setName(@Nonnull final String name) {
			this.name = name;
		}


		@Nonnull
		public Integer getCalories() {
			return calories;
		}



		public void setCalories(final Integer calories) {
			this.calories = calories;
		}


		@Nonnull
		public Double getFat() {
			return fat;
		}



		public void setFat(final Double fat) {
			this.fat = fat;
		}


		@Nonnull
		public Double getSaturatedFat() {
			return saturatedFat;
		}



		public void setSaturatedFat(final Double saturatedFat) {
			this.saturatedFat = saturatedFat;
		}


		@Nonnull
		public Double getCarbs() {
			return carbs;
		}



		public void setCarbs(@Nonnull final Double carbs) {
			this.carbs = carbs;
		}


		@Nonnull
		public Double getFiber() {
			return fiber;
		}



		public void setFiber(final Double fiber) {
			this.fiber = fiber;
		}


		@Nonnull
		public Double getSugar() {
			return sugar;
		}



		public void setSugar(final Double sugar) {
			this.sugar = sugar;
		}



		public Double getProtein() {
			return protein;
		}



		public void setProtein(final Double protein) {
			this.protein = protein;
		}



		public Double getSodium() {
			return sodium;
		}



		public void setSodium(final Double sodium) {
			this.sodium = sodium;
		}


		@Nonnull
		public Timestamp getCreatedTime() {
			return createdTime;
		}



		public void setCreatedTime(final Timestamp createdTime) {
			this.createdTime = createdTime;
		}


		@Nonnull
		public Timestamp getLastUpdatedTime() {
			return lastUpdatedTime;
		}



		public void setLastUpdatedTime(final Timestamp lastUpdatedTime) {
			this.lastUpdatedTime = lastUpdatedTime;
		}


		@Nonnull
	public ServingType getDefaultServingType() {
		return defaultServingType;
	}



	public void setDefaultServingType(ServingType defaultServingType) {
		this.defaultServingType = defaultServingType;
	}


	@Nonnull
	public Double getServingTypeQty() {
		return servingTypeQty;
	}



	public void setServingTypeQty(Double servingTypeQty) {
		this.servingTypeQty = servingTypeQty;
	}
	
	 public double getPoints() {
	        final double fiber = (this.fiber <= 4) ? this.fiber : 4;
	        final double points = (calories / 50.0) + (fat / 12.0) - (fiber / 5.0);
	        return (points > 0) ? points : 0;
	    }
	
	}
		