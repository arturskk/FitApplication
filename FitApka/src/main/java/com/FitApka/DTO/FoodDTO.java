package com.FitApka.DTO;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.FitApka.model.Food;

public final class FoodDTO implements Serializable {

	 	private Integer id;
	    private Integer ownerId;
	    private String name;
	    private Food.ServingType defaultServingType;
	    private double servingTypeQty;
	    private int calories;
	    private double fat;
	    private double saturatedFat;
	    private double carbs;
	    private double fiber;
	    private double sugar;
	    private double protein;
	    private double sodium;
	
	
	    public FoodDTO(
	            @Nullable final Integer id, // will be null if this represents a new Food which hasn't yet been persisted
	            @Nullable final Integer ownerId, // will be null if this represents a "global" Food, usable by all users
	            @Nonnull final String name,
	            @Nonnull final Food.ServingType defaultServingType,
	            final double servingTypeQty,
	            final int calories,
	            final double fat,
	            final double saturatedFat,
	            final double carbs,
	            final double fiber,
	            final double sugar,
	            final double protein,
	            final double sodium
	    ) {
	        this.id = id;
	        this.ownerId = ownerId;
	        this.name = name;
	        this.defaultServingType = defaultServingType;
	        this.servingTypeQty = servingTypeQty;
	        this.calories = calories;
	        this.fat = fat;
	        this.saturatedFat = saturatedFat;
	        this.carbs = carbs;
	        this.fiber = fiber;
	        this.sugar = sugar;
	        this.protein = protein;
	        this.sodium = sodium;
	    }

	    public FoodDTO() {
	    }

	    @Nullable
	    public Integer getId() {
	        return id;
	    }

	    public void setId(@Nonnull final Integer id) {
	        this.id = id;
	    }

	    @Nullable
	    public Integer getOwnerId() {
	        return ownerId;
	    }

	    public void setOwnerId(@Nonnull final Integer ownerId) {
	        this.ownerId = ownerId;
	    }

	    @Nonnull
	    public String getName() {
	        return name;
	    }

	    public void setName(@Nonnull final String name) {
	        this.name = name;
	    }

	    @Nonnull
	    public Food.ServingType getDefaultServingType() {
	        return defaultServingType;
	    }

	    public void setDefaultServingType(@Nonnull final Food.ServingType defaultServingType) {
	        this.defaultServingType = defaultServingType;
	    }

	    public double getServingTypeQty() {
	        return servingTypeQty;
	    }

	    public void setServingTypeQty(final double servingTypeQty) {
	        this.servingTypeQty = servingTypeQty;
	    }

	    public int getCalories() {
	        return calories;
	    }

	    public void setCalories(final int calories) {
	        this.calories = calories;
	    }

	    public double getFat() {
	        return fat;
	    }

	    public void setFat(final double fat) {
	        this.fat = fat;
	    }

	    public double getSaturatedFat() {
	        return saturatedFat;
	    }

	    public void setSaturatedFat(final double saturatedFat) {
	        this.saturatedFat = saturatedFat;
	    }

	    public double getCarbs() {
	        return carbs;
	    }

	    public void setCarbs(final double carbs) {
	        this.carbs = carbs;
	    }

	    public double getFiber() {
	        return fiber;
	    }

	    public void setFiber(final double fiber) {
	        this.fiber = fiber;
	    }

	    public double getSugar() {
	        return sugar;
	    }

	    public void setSugar(final double sugar) {
	        this.sugar = sugar;
	    }

	    public double getProtein() {
	        return protein;
	    }

	    public void setProtein(final double protein) {
	        this.protein = protein;
	    }

	    public double getSodium() {
	        return sodium;
	    }

	    public void setSodium(final double sodium) {
	        this.sodium = sodium;
	    }
	
}
