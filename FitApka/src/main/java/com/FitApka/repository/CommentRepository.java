package com.FitApka.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.BlogPost;
import com.FitApka.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>  {
	

	public List<Comment> findByblogPost(@Nonnull BlogPost blogPost);
	
	
	
	@Query(
		
	            value = "SELECT comment.* FROM comment, fitness_user"
	            		+ "WHERE comment.user_id = fitness_user.id"
	            		+ "AND comment.blog_id = ?1"
	            		+ "Order by comment.date DESC LIMIT 3"
	            ,
	            nativeQuery = true
	  
			)
	@Nonnull
	public List<Comment> findTop3ByBlogpostOrderByDateDesc(@Nonnull BlogPost blogPost);
	

}
