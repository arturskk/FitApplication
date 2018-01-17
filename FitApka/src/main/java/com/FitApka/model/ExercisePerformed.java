package com.FitApka.model;

import java.sql.Date;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(
        name = "exercise_performed",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "exercise_id", "date"})
)

public class ExercisePerformed {

	 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
	private Integer id;
    
    @Column(name = "date", nullable = false)
	private Date date;
    
    @Column(name = "minutes", nullable = false)
    private Integer minutes;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private FitnessUser user;
    
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    
    
    public ExercisePerformed(
            @Nonnull final FitnessUser user,
            @Nonnull final Exercise exercise,
            @Nonnull final Date date,
            final int minutes
    ) {
        this.user = user;
        this.exercise = exercise;
        this.date = (Date) date.clone();
        this.minutes = minutes;
    }
    
    public ExercisePerformed() {
    }

    @Nonnull
    public Integer getId() {
        return id;
    }

    public void setId(@Nonnull final Integer id) {
        this.id = id;
    }

    @Nonnull
    public FitnessUser getUser() {
        return user;
    }

    public void setUser(@Nonnull final FitnessUser user) {
        this.user = user;
    }

    @Nonnull
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(@Nonnull final Exercise exercise) {
        this.exercise = exercise;
    }

    @Nonnull
    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(@Nonnull final Date date) {
        this.date = (Date) date.clone();
    }

    @Nonnull
    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(@Nonnull final Integer minutes) {
        this.minutes = minutes;
    }

    
}


