package com.FitApka.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
        name = "blogpost"
)
public final class BlogPost {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
	private Integer id;
	
	@Column(name = "content", columnDefinition = "varchar(200)", nullable = false)
	private String content;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "title", length = 50, nullable = false)
	private String title;
	
	@Column(name = "summary", length = 50, nullable = false)
	private String summary;
	
	@Column(name = "tags", length = 50, nullable = false)
	private String tags;
	
	@Column(name = "category", length = 50, nullable = false)
	private String category;
	
	@OneToMany(mappedBy = "blogPost")
	private Set<Comment> comments = new HashSet<>();
	
	public BlogPost() {}
	
	public BlogPost(
		@Nonnull String content,
		@Nonnull Date date,
		@Nonnull String title,
		@Nonnull String summary,
		@Nonnull String tags,
		@Nonnull String category
			)
	{
		this.content = content;
		this.date = date;
		this.title = title;
		this.summary = summary;
		this.tags = tags;
		this.category = category;
	}

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
	public String getTitle() {
		return title;
	}

	public void setTitle(@Nonnull String title) {
		this.title = title;
	}
	@Nonnull
	public String getSummary() {
		return summary;
	}

	public void setSummary(@Nonnull String summary) {
		this.summary = summary;
	}
	@Nonnull
	public String getTags() {
		return tags;
	}

	public void setTags(@Nonnull String tags) {
		this.tags = tags;
	}
	@Nonnull
	public String getCategory() {
		return category;
	}

	public void setCategory(@Nonnull String category) {
		this.category = category;
	}

	
	
	
	
	
	
}
