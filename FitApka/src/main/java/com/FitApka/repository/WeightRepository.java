package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.FitnessUser;
import com.FitApka.model.Weight;

public interface WeightRepository extends CrudRepository<Weight,Integer> {

	@Nonnull
	public List<Weight> findByUserOrderByDateDesc( @Nonnull FitnessUser user);
	
	@Query(
            value = "SELECT weight.* FROM weight, fitness_user "
            		+ "WHERE weight.user_id = fitness_user.id "
            		+ "AND fitness_user.id = ?1 "
            		+ "AND weight.date <= ?2 "
            		+ "ORDER BY weight.date DESC LIMIT 1",
            nativeQuery = true
    )
	 @Nullable
    public Weight findByUserMostRecentOnDate(
            @Nonnull FitnessUser user,
            @Nonnull Date date
    );
	
	
	@Nullable
	public Weight findByUserAndDate(
			@Nonnull FitnessUser user,
			@Nonnull Date date
			);
	
	
}
