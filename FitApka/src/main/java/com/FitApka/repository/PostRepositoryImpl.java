package com.FitApka.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;




public class PostRepositoryImpl implements PostRepositoryCustom {

	 	@PersistenceContext
	    private EntityManager em;
	 	
	 	
	 	
	 	public int post(int d)
	 	{
	 	
	 		d = d +2;
	 		return d;
	 		
	 	}
}
