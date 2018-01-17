package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FitApka.model.Exercise;
import com.FitApka.model.ExercisePerformed;
import com.FitApka.model.FitnessUser;


public interface ExercisePerformedRepository extends CrudRepository<ExercisePerformed, Integer> {

    @Query(
            "SELECT exercisePerformed FROM ExercisePerformed exercisePerformed, Exercise exercise "
                + "WHERE exercisePerformed.exercise = exercise "
                + "AND exercisePerformed.user = :user "
                + "AND exercisePerformed.date = :date "
                + "ORDER BY exercise.description ASC"
    )
    @Nonnull
    public List<ExercisePerformed> findByUserEqualsAndDateEquals(
            @Nonnull @Param("user") FitnessUser user,
            @Nonnull @Param("date") Date date
    );

    @Query(
            "SELECT DISTINCT exercise FROM Exercise exercise, ExercisePerformed exercisePerformed "
                + "WHERE exercise = exercisePerformed.exercise "
                + "AND exercisePerformed.user = :user "
                + "AND exercisePerformed.date BETWEEN :startDate AND :endDate "
                + "ORDER BY exercise.description ASC"
    )
    @Nonnull
    public List<Exercise> findByUserPerformedWithinRange(
            @Nonnull @Param("user") FitnessUser user,
            @Nonnull @Param("startDate") Date startDate,
            @Nonnull @Param("endDate") Date endDate
    );

}
