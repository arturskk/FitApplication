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

@Entity
@Table(
        name = "post"
)

public final class Post {

		
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
	
	@Column(name = "content", columnDefinition = "varchar(200)", nullable = false)
	private String content;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private FitnessUser user;
	
	
	
	public Post(
			@Nonnull FitnessUser user,
			@Nonnull String content,
			@Nonnull Date date
			)
	{
	this.user = user;
	this.content = content;
	this.date = (Date) date.clone();
	}
	
	public Post()
	{}
	@Nonnull
	public Integer getId() {
		return id;
	}

	public void setId(@Nonnull Integer id) {
		this.id = id;
	}
	@Nonnull
	public String getContent() {
		return content;
	}

	public void setContent(@Nonnull String content) {
		this.content = content;
	}
	@Nonnull
	public Date getDate() {
		return date;
	}

	public void setDate(@Nonnull Date date) {
		this.date = date;
	}
	@Nonnull
	public FitnessUser getUser() {
		return user;
	}

	public void setUser(@Nonnull FitnessUser user) {
		this.user = user;
	}
	
	
	
	
}


