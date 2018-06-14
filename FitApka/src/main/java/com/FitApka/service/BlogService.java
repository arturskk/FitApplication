package com.FitApka.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FitApka.model.BlogPost;
import com.FitApka.DTO.BlogPostDTO;
import com.FitApka.DTOconverter.BlogPostToDTO;
import com.FitApka.repository.BlogPostRepository;

@Service
public final class BlogService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BlogPostToDTO blogPostToDTO;
	private final BlogPostRepository blogPostRepository;
	
	@Autowired
	public BlogService(
			@Nonnull final BlogPostRepository blogPostRepository,
			@Nonnull final BlogPostToDTO blogPostToDTO
			)
	{
		this.blogPostRepository = blogPostRepository;
		this.blogPostToDTO = blogPostToDTO; 
	}
	
	
	public final void  newBlogPost(final  @Nonnull BlogPostDTO blogPostDTO)
	{
		
		@Nonnull
		BlogPost blogPost = new BlogPost(
				blogPostDTO.getContent(),
				blogPostDTO.getDate(),
				blogPostDTO.getTitle(),
				blogPostDTO.getSummary(),
				blogPostDTO.getTags(),
				blogPostDTO.getCategory()
				);
		
	
		blogPostRepository.save(blogPost);
		
		
	}
	
	
	public final void  editBlogPost(final  @Nonnull BlogPostDTO blogPostDTO)
	{
		
		final BlogPost blogPost =  blogPostRepository.findById(blogPostDTO.getId()).orElse(new BlogPost());
		
		blogPost.setContent(blogPostDTO.getContent());
		blogPost.setCategory(blogPostDTO.getCategory());
		blogPost.setSummary(blogPostDTO.getSummary());
		blogPost.setDate(new java.sql.Date(System.currentTimeMillis()));
		blogPost.setTitle(blogPostDTO.getTitle());
		blogPost.setTags(blogPostDTO.getTags());
		
		blogPostRepository.save(blogPost);
		
	}
	
	public final List<BlogPostDTO> findBlogPosts()
	{
	 List<BlogPost> posts = new ArrayList<>();
	 blogPostRepository.findAll().forEach(posts::add) ;
	
	 return posts.stream().map(blogPostToDTO::convert).collect(Collectors.toList());
	
		
	}
	
	public final List<BlogPostDTO> FindBlogPostsByTitle(final @Nonnull String title)
	{
		List<BlogPost> posts = new ArrayList<>();
		
		return blogPostRepository.findByTitle(title).stream().map(blogPostToDTO::convert).collect(Collectors.toList());
		
	}
		
	
	public final void  NewBlogPost(final  @Nonnull BlogPostDTO blogPostDTO)
	{
		BlogPost blogPost = new BlogPost();
		
		blogPost.setContent(blogPostDTO.getContent());
		blogPost.setCategory(blogPostDTO.getCategory());
		blogPost.setSummary(blogPostDTO.getSummary());
		blogPost.setDate(new java.sql.Date(System.currentTimeMillis()));
		blogPost.setTitle(blogPostDTO.getTitle());
		blogPost.setTags(blogPostDTO.getTags());
		
		blogPostRepository.save(blogPost);
		
	}
	
	public final BlogPostDTO ReadPost(Integer id)
	{
		
		BlogPost blogPost = blogPostRepository.findById(id).orElse(new BlogPost());
		
		if(blogPost == null)
		{
			 throw new WebApplicationException(404);
		}
		
		return blogPostToDTO.convert(blogPost);
		
		
	}
	
	
	public final void DeleteBlogPost(Integer id)
	{
		//blogPostRepository.delete(id);
		
		logger.info("Post with id:"+id+ "DELETED");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
