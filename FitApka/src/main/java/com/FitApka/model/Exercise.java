package com.FitApka.model;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercise")
public final class Exercise {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @Column(name = "metabolic_equivalent", nullable = false)
    private Double metabolicEquivalent;

    @Column(name = "category", length = 25, nullable = false)
    private String category;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    public Exercise(
            @Nonnull final String code,
            final double metabolicEquivalent,
            @Nonnull final String category,
            @Nonnull final String description
    ) {
        this.code = code;
        this.metabolicEquivalent = metabolicEquivalent;
        this.category = category;
        this.description = description;
    }

    public Exercise() {
    }

    @Nonnull
    public int getId() {
        return id;
    }

    public void setId(@Nonnull final int id) {
        this.id = id;
    }
    
    @Nonnull
    public String getCode() {
        return code;
    }

    public void setCode(@Nonnull final String code) {
        this.code = code;
    }

    @Nonnull
    public Double getMetabolicEquivalent() {
        return metabolicEquivalent;
    }

    public void setMetabolicEquivalent(@Nonnull final Double metabolicEquivalent) {
        this.metabolicEquivalent = metabolicEquivalent;
    }

    @Nonnull
    public String getCategory() {
        return category;
    }

    public void setCategory(@Nonnull final String category) {
        this.category = category;
    }

    @Nonnull
    public String getDescription() {
        return description.trim();
    }

    public void setDescription(@Nonnull final String description) {
        this.description = (description == null) ? "" : description.trim();
    }
	
	
	
}
