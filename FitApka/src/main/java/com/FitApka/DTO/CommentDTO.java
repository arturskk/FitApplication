package com.FitApka.DTO;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public final class CommentDTO implements Serializable {

	 private Integer id;
	 private String content;
	 private Date date;
	 private BlogPostDTO blogPostDTO;
	
	 
	 public CommentDTO (
			 	@Nullable final Integer id,
				@Nonnull final String content,
				@Nonnull final Date date,
				@Nonnull final BlogPostDTO blogPostDTO
				)
		{
		 	this.id = id;
			this.content = content;
			this.date = (Date) date.clone();
			this.blogPostDTO = blogPostDTO;

		}
	 
	 
	 public CommentDTO () {}

	@Nullable
	public Integer getId() {
		return id;
	}


	public void setId(@Nonnull final Integer id) {
		this.id = id;
	}

	@Nonnull
	public String getContent() {
		return content;
	}


	public void setContent(@Nonnull final String content) {
		this.content = content;
	}

	@Nonnull
	public Date getDate() {
		return date;
	}


	public void setDate(@Nonnull final Date date) {
		this.date = date;
	}

	@Nonnull
	public BlogPostDTO getBlogPostDTO() {
		return blogPostDTO;
	}


	public void setBlogPostDTO(@Nonnull final BlogPostDTO blogPostDTO) {
		this.blogPostDTO = blogPostDTO;
	}
	 
	 
	 
	 
	 
	 
	
}
