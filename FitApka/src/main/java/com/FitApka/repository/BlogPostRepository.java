package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.BlogPost;


public interface  BlogPostRepository extends CrudRepository<BlogPost, Integer>{
	
	@Nonnull public List<BlogPost> findByTitle(@Nonnull String title);
	
	@Nonnull public List<BlogPost> findByCategory(@Nonnull String category);
	
	@Nonnull public List<BlogPost> findByDateBetween(@Nonnull  Date startDate, @Nonnull Date endDate);

}
