package com.FitApka.DTO;

import java.io.Serializable;
import java.sql.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class ExercisePerformedDTO implements Serializable{
	private Integer id;
    private Integer userId;
    private ExerciseDTO exercise;
    private Date date;
    private int minutes;
    private int caloriesBurned;
    private double pointsBurned;

    public ExercisePerformedDTO(
            @Nullable final Integer id,
            @Nonnull final Integer userId,
            @Nonnull final ExerciseDTO exercise,
            @Nonnull final Date date,
            final int minutes
    ) {
        this.id = id;
        this.userId = userId;
        this.exercise = exercise;
        this.date = (Date) date.clone();
        this.minutes = minutes;
    }

    public ExercisePerformedDTO() {
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nonnull final Integer id) {
        this.id = id;
    }

    @Nonnull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@Nonnull final Integer userId) {
        this.userId = userId;
    }

    @Nonnull
    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(@Nonnull final ExerciseDTO exercise) {
        this.exercise = exercise;
    }

    @Nonnull
    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(@Nonnull final Date date) {
        this.date = (Date) date.clone();
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(final int minutes) {
        this.minutes = minutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(final int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getPointsBurned() {
        return pointsBurned;
    }

    public void setPointsBurned(final double pointsBurned) {
        this.pointsBurned = pointsBurned;
    }
}
