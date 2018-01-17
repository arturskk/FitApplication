package com.FitApka.DTO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.FitApka.model.FitnessUser;
import com.FitApka.model.FitnessUser.ActivityLevel;
import com.FitApka.model.FitnessUser.Gender;

public class FitnessUserDTO implements Serializable{

		
	    private Integer id;
	    @NotNull(message="Gender can't be empty")
	    FitnessUser.Gender gender;
	    @NotNull(message="Birthday can't be empty")
        Date birthdate;
	    @NotNull(message="Height can't be empty")
	    @Max(250)
	    @Min(70)
        double heightInCm;
	    @NotNull(message="ActivityLevel can't be empty")
        ActivityLevel activityLevel;
	    @NotNull(message="Email can't be empty")
	    @Size(min=4, max=50,message="Wrong email")
        String email;
	    @Size(min=2, max=50,message="Firstname is not correct")
	    @Pattern(regexp = "^[A-Za-z]*$",message="Wrong firstname ")
        String firstName;
	    @Size(min=2, max=50,message="Lastname is not correct")
	    @Pattern(regexp = "^[A-Za-z]*$",message="wrong lastname ")
        String lastName;
	    @NotNull
      	String timeZone;
        private double currentWeight;
	    private double bmi;
	    private int maintenanceCalories;
	    private double dailyPoints;
	    
	   
	    
	    
	   

		public FitnessUserDTO(
	    		@Nullable final Integer id,
	    		@Nonnull final Gender gender,
	            @Nonnull final Date birthdate,
	            final double heightInCm,
	            final ActivityLevel activityLevel,
	            @Nonnull final String email,
	            @Nonnull final String firstName,
	            @Nonnull final String lastName,
	            @Nonnull  String timeZone,
	            double currentWeight,
	    	    double bmi,
	    	    int maintenanceCalories,
	    	    double dailyPoints
	    
	    ) {
	    	this.id = id;
    		this.gender = gender;
    		this.birthdate = (Date) birthdate.clone();
    		this.heightInCm =  heightInCm;
            this.activityLevel =  activityLevel;
            this.email= email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.timeZone = timeZone;
            this.currentWeight =  currentWeight;
    		this.bmi = bmi;
    		this.maintenanceCalories = maintenanceCalories;
    		this.dailyPoints = dailyPoints;
	    }
	    
	   
	    public FitnessUserDTO(){
	    	
	    }

	    @Nullable
		public Integer getId() {
			return id;
		}


		public void setId(@Nonnull final Integer id) {
			this.id = id;
		}

		@Nonnull
		 public FitnessUser.Gender getGender() {
				return gender;
			}


			public void setGender(@Nonnull final FitnessUser.Gender gender) {
				this.gender = gender;
			}


			@Nonnull
		public Date getBirthdate() {
			return birthdate;
		}


		public void setBirthdate(@Nonnull final Date birthdate) {
			this.birthdate = birthdate;
		}


		public double getHeightInCm() {
			return heightInCm;
		}


		public void setHeightInCm(final double heightInCm) {
			this.heightInCm = heightInCm;
		}

		
		public ActivityLevel getActivityLevel() {
			return activityLevel;
		}


		public void setActivityLevel( final ActivityLevel activityLevel) {
			this.activityLevel = activityLevel;
		}

		@Nonnull
		public String getEmail() {
			return email;
		}


		public void setEmail(@Nonnull final String email) {
			this.email = email;
		}

		@Nonnull
		public String getFirstName() {
			return firstName;
		}


		public void setFirstName(@Nonnull final String firstName) {
			this.firstName = firstName;
		}

		@Nonnull
		public String getLastName() {
			return lastName;
		}


		public void setLastName(@Nonnull final String lastName) {
			this.lastName = lastName;
		}

		@Nonnull
		public String getTimeZone() {
			return timeZone;
		}


		public void setTimeZone(@Nonnull final String timeZone) {
			this.timeZone = timeZone;
		}


		public double getCurrentWeight() {
			return currentWeight;
		}


		public void setCurrentWeight(final double currentWeight) {
			this.currentWeight = currentWeight;
		}


		public double getBmi() {
			return bmi;
		}


		public void setBmi(final double bmi) {
			this.bmi = bmi;
		}


		public int getMaintenanceCalories() {
			return maintenanceCalories;
		}


		public void setMaintenanceCalories(final int maintenanceCalories) {
			this.maintenanceCalories = maintenanceCalories;
		}


		public double getDailyPoints() {
			return dailyPoints;
		}


		public void setDailyPoints(final double dailyPoints) {
			this.dailyPoints = dailyPoints;
		}
	    
	   
	    
	    
}




