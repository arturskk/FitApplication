package com.FitApka.DTO;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.FitApka.model.ExerciseFoodnotes;

public final class ExerciseFoodnotesDTO implements Serializable{

	private Integer id;
	private Date date;
	private String content;
	private ExerciseFoodnotes.Type type;
	
	public ExerciseFoodnotesDTO(
			@Nullable final Integer id,
			@Nonnull final Date date,
			@Nonnull final String content,
			@Nonnull final ExerciseFoodnotes.Type type
			)
	{
		this.id = id;
		this.date = (Date) date.clone();
		this.content = content;
		this.type = type;
	
	}
	
	
	public ExerciseFoodnotesDTO() {}

	@Nullable
	public Integer getId() {
		return id;
	}


	public void setId(@Nonnull final Integer id) {
		this.id = id;
	}

	@Nonnull
	public Date getDate() {
		return date;
	}


	public void setDate(@Nonnull final Date date) {
		this.date = date;
	}

	@Nonnull
	public String getContent() {
		return content;
	}


	public void setContent(@Nonnull final String content) {
		this.content = content;
	}

	@Nonnull
	public ExerciseFoodnotes.Type getType() {
		return type;
	}


	public void setType(@Nonnull final ExerciseFoodnotes.Type type) {
		this.type = type;
	}
	
	
	
	
	
	
}
