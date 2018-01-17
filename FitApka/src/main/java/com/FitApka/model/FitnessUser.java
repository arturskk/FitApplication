package com.FitApka.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "fitness_user")
public final class FitnessUser {

    public enum Gender {

        MALE, FEMALE;

        @Nullable
        public static Gender fromString(@Nonnull final String s) {
            Gender match = null;
            for (final Gender gender : Gender.values()) {
                if (gender.toString().equalsIgnoreCase(s)) {
                    match = gender;
                }
            }
            return match;
        }

        @Override
        public String toString() {
            return super.toString();
        }

    }

    public enum ActivityLevel {

        SEDENTARY(1.25), LIGHTLY_ACTIVE(1.3), MODERATELY_ACTIVE(1.5), VERY_ACTIVE(1.7), EXTREMELY_ACTIVE(2.0);

        private double value;

        private ActivityLevel(final double value) {
            this.value = value;
        }

        @Nullable
        public static ActivityLevel fromValue(final double value) {
            ActivityLevel match = null;
            for (final ActivityLevel activityLevel : ActivityLevel.values()) {
                if (activityLevel.getValue() == value) {
                    match = activityLevel;
                }
            }
            return match;
        }

        @Nullable
        public static ActivityLevel fromString(@Nonnull final String s) {
            ActivityLevel match = null;
            for (final ActivityLevel activityLevel : ActivityLevel.values()) {
                if (activityLevel.toString().equalsIgnoreCase(s)) {
                    match = activityLevel;
                }
            }
            return match;
        }

        public double getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            final StringBuilder s = new StringBuilder(super.toString().toLowerCase().replace('_', ' '));
            for (int index = 0; index < s.length(); index++) {
                if (index == 0 || s.charAt(index - 1) == ' ') {
                    final String currentCharAsString = Character.toString(s.charAt(index));
                    s.replace(index, index + 1, currentCharAsString.toUpperCase());
                }
            }
            return s.toString();
        }

    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @Column(name = "GENDER", length = 6, nullable = false)
    private String gender;

    @Column(name = "BIRTHDATE", nullable = false)
    private Date birthdate;

    @Column(name = "HEIGHT_IN_CM", nullable = false)
    private double heightInCm;

    @Column(name = "ACTIVITY_LEVEL", nullable = true)
    private double activityLevel;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "PASSWORD_HASH", length = 100, nullable = true)
    private String passwordHash;

    @Column(name = "FIRST_NAME", length = 20, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 20, nullable = false)
    private String lastName;

    @Column(name = "TIMEZONE", length = 50, nullable = false)
    private String timeZone;

    @Column(name = "CREATED_TIME", nullable = false)
    private Timestamp createdTime;

    @Column(name = "LAST_UPDATED_TIME", nullable = false)
    private Timestamp lastUpdatedTime;

    @OneToMany(mappedBy = "user")
    private Set<Weight> weights = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Food> foods = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<FoodEaten> foodsEaten = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comment = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<ExerciseFoodnotes> exercisefoodNotes = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<MyGoals> mygoals = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<ExercisePerformed> exercisesPerformed = new HashSet<>();
    

    public FitnessUser(
            @Nonnull final Gender gender,
            @Nonnull final Date birthdate,
            final double heightInCm,
            @Nonnull final ActivityLevel activityLevel,
            @Nonnull final String email,
            @Nullable final String passwordHash,
            @Nonnull final String firstName,
            @Nonnull final String lastName,
            @Nonnull final String timeZone,
            @Nonnull final Timestamp createdTime,
            @Nonnull final Timestamp lastUpdatedTime
    ) {
       
        this.gender = gender.toString();
        this.birthdate = (Date) birthdate.clone();
        this.heightInCm = heightInCm;
        this.activityLevel = activityLevel.getValue();
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeZone = timeZone;
        this.createdTime = (Timestamp) createdTime.clone();
        this.lastUpdatedTime = (Timestamp) lastUpdatedTime.clone();
    }

    public FitnessUser() {
    }

    @Nullable
	public Integer getId() {
		return id;
	}

	public void setId(@Nonnull final Integer id) {
		this.id = id;
	}
	
	@Nonnull
    public Gender getGender() {
        return Gender.fromString(gender);
    }

    public void setGender(@Nonnull final Gender gender) {
        this.gender = gender.toString();
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

	public void setHeightInCm(double heightInCm) {
		this.heightInCm = heightInCm;
	}
	
    
    public ActivityLevel getActivityLevel() {
        return ActivityLevel.fromValue(activityLevel);
    }

    public void setActivityLevel(final ActivityLevel activityLevel) {
        this.activityLevel = activityLevel.getValue();
    }
	@Nonnull
	public String getEmail() {
		return email;
	}

	public void setEmail(@Nonnull final String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
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

	public void setLastName(@Nonnull final  String lastName) {
		this.lastName = lastName;
	}
	@Nonnull
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(@Nonnull final String timeZone) {
		this.timeZone = timeZone;
	}
	@Nonnull
	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(@Nonnull final Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	@Nonnull
	public Timestamp getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(@Nonnull final Timestamp lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	public Set<FoodEaten> getFoodsEaten() {
		return foodsEaten;
	}

	public void setFoodsEaten(Set<FoodEaten> foodsEaten) {
		this.foodsEaten = foodsEaten;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public Set<ExerciseFoodnotes> getExercisefoodNotes() {
		return exercisefoodNotes;
	}

	public void setExercisefoodNotes(Set<ExerciseFoodnotes> exercisefoodNotes) {
		this.exercisefoodNotes = exercisefoodNotes;
	}

	public Set<MyGoals> getMygoals() {
		return mygoals;
	}

	public void setMygoals(Set<MyGoals> mygoals) {
		this.mygoals = mygoals;
	}

	public Set<ExercisePerformed> getExercisesPerformed() {
		return exercisesPerformed;
	}

	public void setExercisesPerformed(Set<ExercisePerformed> exercisesPerformed) {
		this.exercisesPerformed = exercisesPerformed;
	}

	public Set<Weight> getWeights() {
		return weights;
	}

	public void setWeights(Set<Weight> weights) {
		this.weights = weights;
	} 

}