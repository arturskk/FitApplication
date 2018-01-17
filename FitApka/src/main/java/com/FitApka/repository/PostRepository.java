package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FitApka.model.Exercise;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer>, PostRepositoryCustom {
	
	
	 @Nonnull
	 public List<Post> findByUser(@Nonnull FitnessUser user);
	 
	 
	 @Nonnull
	 public List<Post> findByUserAndDate(@Nonnull FitnessUser user,@Nonnull Date date);
	 
	 
	 @Query(
	            "SELECT post FROM Post post "
	                + "WHERE post.user = :user "
	                + "AND post.date BETWEEN :startDate AND :endDate "
	                + "Order by post.date DESC"
	    )
	    @Nonnull
	    public List<Exercise> findByUserBetweenDates(
	            @Nonnull @Param("user") FitnessUser user,
	            @Nonnull @Param("startDate") Date startDate,
	            @Nonnull @Param("endDate") Date endDate
	    );
	
	
	

}
