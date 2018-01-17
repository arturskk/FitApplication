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
        name = "report_data",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"})
)
public final class ReportData {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
	
	@Column(name = "date", nullable = false)
    private Date date;
	
	@Column(name = "net_calories", nullable = false)
    private Integer netCalories = 0;
	
	@Column(name = "net_points", nullable = false)
	private Double netPoints = 0.0;

	@Column(name = "kilograms", nullable = false)
	private Double kilograms = 0.0;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private FitnessUser user;
	
	
	public ReportData(
            @Nonnull final FitnessUser user,
            @Nonnull final Date date,
            final double kilograms,
            final int netCalories,
            final double netPoints
    ) {
        this.user = user;
        this.date = (Date) date.clone();
        this.kilograms = kilograms;
        this.netCalories =  netCalories;
        this.netPoints = netPoints;
    }
	
	public ReportData() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	public Integer getNetCalories() {
		return netCalories;
	}

	public void setNetCalories(@Nonnull Integer netCalories) {
		this.netCalories = netCalories;
	}
	@Nonnull
	public Double getNetPoints() {
		return netPoints;
	}

	public void setNetPoints(@Nonnull Double netPoints) {
		this.netPoints = netPoints;
	}
	@Nonnull
	public Double getKilograms() {
		return kilograms;
	}

	public void setKilograms(@Nonnull Double kilograms) {
		this.kilograms = kilograms;
	}
	@Nonnull
	public FitnessUser getUser() {
		return user;
	}

	public void setUser(@Nonnull FitnessUser user) {
		this.user = user;
	}


	
}

