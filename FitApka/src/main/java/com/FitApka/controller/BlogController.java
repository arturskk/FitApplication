package com.FitApka.controller;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.FitApka.DTO.BlogPostDTO;
import com.FitApka.service.BlogService;


@Controller
public class BlogController extends AbstractController {
	
	
	
	private final BlogService blogService;

    @Autowired
    public BlogController(final BlogService blogService) {
        this.blogService = blogService;
    }
    
  
	
    @GetMapping(value = "/findAllBlogPosts")
    @ResponseBody
     public final List<BlogPostDTO> findBlogPosts()
     {
    	
    	return blogService.findBlogPosts();
    	
     }
    
    @GetMapping(value = "/findAllBlogPosts/{title}")
    @ResponseBody
     public final List<BlogPostDTO> findBlogPosts(@PathVariable final String title)
     {
    	
    	return blogService.FindBlogPostsByTitle(title);
    	
     }
    
	
	
	

}
