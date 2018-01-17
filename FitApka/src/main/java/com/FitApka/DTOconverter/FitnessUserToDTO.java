package com.FitApka.DTOconverter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.Weight;
import com.FitApka.repository.WeightRepository;

@Component
public class FitnessUserToDTO implements Converter<FitnessUser, FitnessUserDTO> {

	 private final WeightRepository weightRepository;

	    @Autowired
	    public FitnessUserToDTO(@Nonnull final WeightRepository weightRepository) {
	        this.weightRepository = weightRepository;
	    }

	    @Override
	    @Nullable
	    public FitnessUserDTO convert(@Nullable final FitnessUser user) {
	    	FitnessUserDTO dto = null;
	        if (user != null) {
	            final double currentWeight = getCurrentWeight(user);
	            dto = new FitnessUserDTO(
	                    user.getId(),
	                    user.getGender(),
	                    user.getBirthdate(),
	                    user.getHeightInCm(),
	                    user.getActivityLevel(),
	                    user.getEmail(),
	                    user.getFirstName(),
	                    user.getLastName(),
	                    user.getTimeZone(),
	                    currentWeight,
	                    getBmi(user, currentWeight),
	                    getMaintenanceCalories(user, currentWeight),
	                    getDailyPoints(user, currentWeight)
	            );
	        }
	        return dto;
	    }

	    private double getCurrentWeight(@Nonnull final FitnessUser user) {
	        final List<Weight> weights = weightRepository.findByUserOrderByDateDesc(user);
	        double currentWeight = 0;
	        if (weights != null && !weights.isEmpty()) {
	            currentWeight = weights.get(0).getKilograms();
	        }
	        return currentWeight;
	    }

	    private double getBmi(
	            @Nonnull final FitnessUser user,
	            final double currentWeight
	    ) {
	        double bmi = 0;
	        if (currentWeight != 0 && user.getHeightInCm() != 0) {
	            bmi = (currentWeight * 703) / (user.getHeightInCm() * user.getHeightInCm());
	        }
	        return bmi;
	    }

	    private int getMaintenanceCalories(
	            @Nonnull final FitnessUser user,
	            final double currentWeight
	    ) {
	        int maintenanceCalories = 0;
	        final long age = ChronoUnit.YEARS.between(user.getBirthdate().toLocalDate(), LocalDate.now());
	        if (user.getGender() != null
	                && currentWeight != 0
	                && user.getHeightInCm() != 0
	                && age != 0
	                && user.getActivityLevel() != null
	                ) {
	            final double centimeters = user.getHeightInCm() * 2.54f;
	            final double kilograms = currentWeight / 2.2f;
	            final double adjustedWeight = user.getGender().equals(FitnessUser.Gender.FEMALE) ? 655f + (9.6f * kilograms) : 66f + (13.7f * kilograms);
	            final double adjustedHeight = user.getGender().equals(FitnessUser.Gender.FEMALE) ? 1.7f * centimeters : 5f * centimeters;
	            final float adjustedAge = user.getGender().equals(FitnessUser.Gender.FEMALE) ? 4.7f * age : 6.8f * age;
	            maintenanceCalories = (int) ((adjustedWeight + adjustedHeight - adjustedAge) * user.getActivityLevel().getValue());
	        }
	        return maintenanceCalories;
	    }

	    public double getDailyPoints(
	            @Nonnull final FitnessUser user,
	            final double currentWeight
	    ) {
	        double dailyPoints = 0;
	        final long age = ChronoUnit.YEARS.between(user.getBirthdate().toLocalDate(), LocalDate.now());
	        if (user.getGender() != null
	                && age != 0
	                && currentWeight != 0
	                && user.getHeightInCm() != 0
	                && user.getActivityLevel() != null
	                ) {
	            // Factor in gender
	            dailyPoints = user.getGender().equals(FitnessUser.Gender.FEMALE) ? 2.0 : 8.0;
	            // Factor in age
	            if (age <= 26) {
	                dailyPoints += 4.0;
	            } else if (age <= 37) {
	                dailyPoints += 3.0;
	            } else if (age <= 47) {
	                dailyPoints += 2.0;
	            } else if (age <= 58) {
	                dailyPoints += 1.0;
	            }
	            // Factor in weight
	            dailyPoints += currentWeight / 10.0;
	            // Factor in height
	            if (user.getHeightInCm() >= 61 && user.getHeightInCm() <= 70) {
	                dailyPoints += 1.0;
	            } else if (user.getHeightInCm() > 70) {
	                dailyPoints += 2.0;
	            }
	            // Factor in activity level
	            if (user.getActivityLevel().equals(FitnessUser.ActivityLevel.EXTREMELY_ACTIVE) || user.getActivityLevel().equals(FitnessUser.ActivityLevel.VERY_ACTIVE)) {
	                dailyPoints += 6.0;
	            } else if (user.getActivityLevel().equals(FitnessUser.ActivityLevel.MODERATELY_ACTIVE)) {
	                dailyPoints += 4.0;
	            } else if (user.getActivityLevel().equals(FitnessUser.ActivityLevel.LIGHTLY_ACTIVE)) {
	                dailyPoints += 2.0;
	            }
	            // Factor in daily "flex" points quota
	            dailyPoints += 5.0;
	        }
	        return dailyPoints;
	    }

}
