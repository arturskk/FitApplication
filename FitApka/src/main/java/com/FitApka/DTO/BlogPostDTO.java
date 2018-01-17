package com.FitApka.DTO;

import java.sql.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class BlogPostDTO {

	private Integer id;
	private String content;
	private Date date;
	private String title;
	private String summary;
	private String tags;
	private String category;
	
	

	
		public BlogPostDTO(
		@Nullable final Integer id,
		@Nonnull final String content,
		@Nonnull final Date date,
		@Nonnull final String title,
		@Nonnull final String summary,
		@Nonnull final String tags,
		@Nonnull final String category
			)
	{
		this.id = id;
		this.content = content;
		this.date = (Date) date.clone();
		this.title = title;
		this.summary = summary;
		this.tags = tags;
		this.category = category;
	}
	
		
		public BlogPostDTO() {}

		@Nullable
		public Integer getId() {
			return id;
		}


		public void setId(final @Nonnull Integer id) {
			this.id = id;
		}

		@Nonnull 
		public String getContent() {
			return content;
		}


		public void setContent(final @Nonnull String content) {
			this.content = content;
		}

		@Nonnull 
		public Date getDate() {
			return date;
		}


		public void setDate(final @Nonnull Date date) {
			this.date = date;
		}

		@Nonnull 
		public String getTitle() {
			return title;
		}


		public void setTitle(final @Nonnull String title) {
			this.title = title;
		}

		@Nonnull 
		public String getSummary() {
			return summary;
		}


		public void setSummary(final @Nonnull String summary) {
			this.summary = summary;
		}

		@Nonnull 
		public String getTags() {
			return tags;
		}


		public void setTags(final @Nonnull String tags) {
			this.tags = tags;
		}

		@Nonnull 
		public String getCategory() {
			return category;
		}


		public void setCategory(final @Nonnull String category) {
			this.category = category;
		}
		
		
		
		
		
		
		
	
	
}
