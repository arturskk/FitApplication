package com.FitApka.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FitApka.model.FitnessUser;
import com.FitApka.model.Food;



public interface FoodRepository extends CrudRepository<Food, Integer> {

	
	
	 @Nonnull
	    public List<Food> findByOwnerIsNull();
	 
	 
	 @Nonnull
	    public List<Food> findByOwner(@Nonnull FitnessUser owner);
	 
	 
	 
	 @Query(
	            "SELECT food FROM Food food WHERE food.owner = :owner "
	                    + "OR ("
	                    + "food.owner IS NULL "
	                    + "AND NOT EXISTS (SELECT subFood FROM Food subFood WHERE subFood.owner = :owner AND subFood.name = food.name)"
	                    + ") ORDER BY food.name ASC"
	    )
	    @Nonnull
	    public List<Food> findVisibleByOwner(@Nonnull @Param("owner") FitnessUser owner);
	
	
	 
	 @Query(
	            "SELECT food FROM Food food "
	                    + "WHERE ("
	                    + "   food.owner = :owner "
	                    + "   OR ("
	                    + "      food.owner IS NULL "
	                    + "      AND NOT EXISTS (SELECT subFood FROM Food subFood WHERE subFood.owner = :owner AND subFood.name = food.name)"
	                    + "   )"
	                    + ") AND LOWER(food.name) LIKE LOWER(CONCAT('%', :name, '%')) "
	                    + "ORDER BY food.name ASC")
	    @Nonnull
	    public List<Food> findByNameLike(
	            @Nonnull @Param("owner") FitnessUser owner,
	            @Nonnull @Param("name") String name
	    );
	 
	 @Nonnull
	    public List<Food> findByOwnerEqualsAndNameEquals(
	            @Nonnull FitnessUser owner,
	            @Nonnull String name
	    );
	 
	
}
