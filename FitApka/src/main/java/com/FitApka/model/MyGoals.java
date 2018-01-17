package com.FitApka.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
        name = "my_goals"
)
public final class MyGoals {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
	
	@Column(name = "kilograms", columnDefinition = "double", nullable = false)
	private Double kilograms;
	
	@Column(name = "carbohydrates", columnDefinition = "double", nullable = false)
	private Double carbohydrates;
	
	@Column(name = "fat", columnDefinition = "double", nullable = false)
	private Double fat;
	
	@Column(name = "protein", columnDefinition = "double", nullable = false)
	private Double protein;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private FitnessUser user;
	
	public MyGoals(
			@Nonnull Double kilograms,
			@Nonnull Double carbohydrates,
			@Nonnull Double fat,
			@Nonnull Double protein,
			@Nonnull FitnessUser user
			) 
	{
		
		this.kilograms = kilograms;
		this.carbohydrates = carbohydrates;
		this.fat = fat;
		this.protein = protein;
		this.user = user;
		
	}
	
	public MyGoals() {}

	@Nonnull
	public Double getKilograms() {
		return kilograms;
	}

	public void setKilograms(@Nonnull Double kilograms) {
		this.kilograms = kilograms;
	}
	@Nonnull
	public Double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(@Nonnull Double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	@Nonnull
	public Double getFat() {
		return fat;
	}
	@Nullable
	public Integer getId() {
		return id;
	}

	public void setId(@Nonnull Integer id) {
		this.id = id;
	}

	public void setFat(@Nonnull Double fat) {
		this.fat = fat;
	}
	@Nonnull
	public Double getProtein() {
		return protein;
	}

	public void setProtein(@Nonnull Double protein) {
		this.protein = protein;
	}
	@Nonnull
	public FitnessUser getUser() {
		return user;
	}

	public void setUser(@Nonnull FitnessUser user) {
		this.user = user;
	}

	
	
}

