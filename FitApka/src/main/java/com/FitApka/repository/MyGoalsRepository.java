package com.FitApka.repository;


import javax.annotation.Nonnull;

import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.FitnessUser;
import com.FitApka.model.MyGoals;

public interface MyGoalsRepository extends CrudRepository<MyGoals, Integer> {
	
	
	@Nonnull
    public MyGoals findByUser(@Nonnull FitnessUser user);
}
