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
       name = "weight",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"})
)
public final class Weight {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id", columnDefinition="INT")
	private Integer id;
	
	@Column(name ="date", nullable = false)
	private Date date;
	
	@Column(name="kilograms", nullable = false)
	private Double kilograms;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private FitnessUser user;
	
	
	public Weight(
			@Nonnull final FitnessUser user,
			@Nonnull final Date date,
			@Nonnull final double kilograms
			)
	{
		this.user = user;
		this.date = (Date) date.clone();
		this.kilograms = kilograms;
		
	}
	
	public Weight() 
	{}

	
	public Integer getId() {
		return id;
	}

	public void setId(  Integer id) {
		this.id = id;
	}
	@Nonnull
	public Date getDate() {
		return date;
	}
	@Nonnull
	public void setDate( @Nonnull Date date) {
		this.date = date;
	}

	public Double getKilograms() {
		return kilograms;
	}
	@Nonnull
	public void setKilograms(  @Nonnull Double kilograms) {
		this.kilograms = kilograms;
	}

	public FitnessUser getUser() {
		return user;
	}
	 
	@Nonnull
	public void setUser(  @Nonnull FitnessUser user) {
		this.user = user;
	}
	
	
}