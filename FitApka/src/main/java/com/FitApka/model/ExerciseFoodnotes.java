package com.FitApka.model;

import java.sql.Date;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "exercise_foodnotes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"})
)
public final class ExerciseFoodnotes {
	
public enum Type {
		
		EXERCISE, FOOD;
		
		@Nullable
        public static Type fromString(@Nonnull final String s) {
            Type match = null;
            for (final Type type : Type.values()) {
                if (type.toString().equalsIgnoreCase(s)) {
                    match = type;
                }
            }
            return match;
        }
		
	}
	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
	private Integer id;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "content", length =200, nullable = false)
	private String content;
	
	@Column(name = "type", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
    private ExerciseFoodnotes.Type type;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private FitnessUser user;
	
	
	public ExerciseFoodnotes() {}
	
	public ExerciseFoodnotes(
			@Nonnull Date date,
			@Nonnull String content,
			@Nonnull ExerciseFoodnotes.Type type,
			@Nonnull FitnessUser user
			)
	{
		this.date = (Date) date.clone();
		this.content = content;
		this.type = type;
		this.user = user;
	}

	@Nonnull
	public Integer getId() {
		return id;
	}

	public void setId(@Nonnull Integer id) {
		this.id = id;
	}
	@Nonnull
	public Date getDate() {
		return date;
	}

	public void setDate(@Nonnull Date date) {
		this.date = date;
	}
	@Nonnull
	public String getContent() {
		return content;
	}

	public void setContent(@Nonnull String content) {
		this.content = content;
	}
	@Nonnull
	public ExerciseFoodnotes.Type getType() {
		return type;
	}

	public void setType(@Nonnull ExerciseFoodnotes.Type type) {
		this.type = type;
	}
	@Nonnull
	public FitnessUser getUser() {
		return user;
	}

	public void setUser(@Nonnull FitnessUser user) {
		this.user = user;
	}
	
	
	
	
	
	
	
}
