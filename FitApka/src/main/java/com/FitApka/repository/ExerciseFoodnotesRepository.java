package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FitApka.model.Exercise;
import com.FitApka.model.ExerciseFoodnotes;
import com.FitApka.model.FitnessUser;



public interface  ExerciseFoodnotesRepository extends CrudRepository<ExerciseFoodnotes, Integer>  {
	
	
	@Nullable
	public ExerciseFoodnotes findByUserAndDate(
			@Nonnull FitnessUser user,
			@Nonnull Date date
			);
	
	
	@Nullable
	public List<ExerciseFoodnotes> findByUser(
			@Nonnull FitnessUser user
			);
	
	
	
	 @Query(
	            "SELECT notes FROM ExerciseFoodnotes notes "
	                + "WHERE notes.user = :user "         
	                + "AND notes.date BETWEEN :startDate AND :endDate "
	                + "ORDER BY notes.date DESC"
	    )
	    @Nonnull
	    public List<ExerciseFoodnotes> findByUserBetweenDates(
	            @Nonnull @Param("user") FitnessUser user,
	            @Nonnull @Param("startDate") Date startDate,
	            @Nonnull @Param("endDate") Date endDate
	    );
	
	 
	
	 @Query(
	            "SELECT notes FROM ExerciseFoodnotes notes "
	                + "WHERE notes.user = :user "
	                + "AND notes.type = :type"        
	    )
	    @Nonnull
	    public List<ExerciseFoodnotes> findByUsesAndType(
	            @Nonnull @Param("user") FitnessUser user,
	            @Nonnull @Param("type") String type
	          
	    );
	 
	 
	 

}
