package com.FitApka.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import org.junit.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.Weight;
import com.FitApka.repository.FitnessUserRepository;
import com.FitApka.repository.PostRepository;
import com.FitApka.repository.WeightRepository;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryTest extends ConfigTest {

	

	@Autowired
    private FitnessUserRepository fitnessuserRepository;

	@Autowired
    private WeightRepository weightRepository;
	
	@Autowired
    private PostRepository postRepository;

 
	@Test
	@Rollback(false)
    public void testAUserRepository() {
    	
    	
    	final FitnessUser newUser = new FitnessUser(
                FitnessUser.Gender.MALE,
                new Date(new java.util.Date().getTime()),
                187,
                FitnessUser.ActivityLevel.SEDENTARY,
                "fake@address.com",
                null,
                "Jane",
                "Doe",
                "America/New_York",
                new Timestamp(new java.util.Date().getTime()),
                new Timestamp(new java.util.Date().getTime())
        );
    	
    	 
    	final FitnessUser newUser2 = new FitnessUser(
                FitnessUser.Gender.MALE,
                new Date(new java.util.Date().getTime()),
                160,
                FitnessUser.ActivityLevel.SEDENTARY,
                "fa@address.com",
                null,
                "Ann",
                "Kilo",
                "America/New_York",
                new Timestamp(new java.util.Date().getTime()),
                new Timestamp(new java.util.Date().getTime())
        );
    	
    	 List<FitnessUser> users = new ArrayList<>();
    	 users.add(newUser);
    	 users.add(newUser2);
    	 fitnessuserRepository.saveAll(users);
    	 
    	 final Iterable<FitnessUser> retrievedNewUser2 = fitnessuserRepository.findAll();
    	 List<FitnessUser> target = new ArrayList<>();
    	 retrievedNewUser2.forEach(target::add);
    	 assertEquals(2,target.size());
    	 assertEquals("Jane",users.get(0).getFirstName());
    	 assertEquals("Ann",users.get(1).getFirstName());
    	 assertEquals(187.0,users.get(0).getHeightInCm(),0);
    	 assertEquals(160.0,users.get(1).getHeightInCm(),0);
        
    }
	
	
	@Test
    public void testBWeightRepository() {
		
    	
		final FitnessUser user = fitnessuserRepository.findByEmailEquals("fa@address.com");
		
		
		
		final Weight weight = new Weight(
				user,
				new Date(new java.util.Date().getTime()),
				90.0
				);
		
		
		weightRepository.save(weight);
		final Date today = new Date(new java.util.Date().getTime());
		final Weight weightfromdb = weightRepository.findByUserMostRecentOnDate(user, today );
		assertEquals(90.0,weightfromdb.getKilograms(),0);
		assertEquals("Ann",weightfromdb.getUser().getFirstName());
		assertEquals(today.toString(),weightfromdb.getDate().toString());
		
		
	}
    
    
    
    
}
