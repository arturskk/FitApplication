package com.FitApka.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;




public class PostRepositoryImpl implements PostRepositoryCustom {

	 	@PersistenceContext
	    private EntityManager em;
	 	
	 	
	 	
	 	public int post(int d)
	 	{
	 		Session session = (Session) em.getDelegate();
	 	
	 		d = d +2;
	 		return d;
	 		
	 	}
}
