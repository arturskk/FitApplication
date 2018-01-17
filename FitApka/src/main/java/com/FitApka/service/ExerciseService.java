package com.FitApka.service;

import static java.util.stream.Collectors.toList;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FitApka.DTO.ExerciseDTO;
import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.DTO.WeightDTO;
import com.FitApka.DTOconverter.ExercisePerformedToDTO;
import com.FitApka.DTOconverter.ExerciseToDTO;
import com.FitApka.DTOconverter.FitnessUserToDTO;
import com.FitApka.model.Exercise;
import com.FitApka.model.ExercisePerformed;
import com.FitApka.model.FitnessUser;
import com.FitApka.repository.ExercisePerformedRepository;
import com.FitApka.repository.ExerciseRepository;
import com.FitApka.repository.FitnessUserRepository;


@Service
public final class ExerciseService {
	
		private final FitnessUserService userService;
	    private final ReportDataService reportDataService;
	    private final FitnessUserRepository userRepository;
	    private final ExerciseRepository exerciseRepository;
	    private final ExercisePerformedRepository exercisePerformedRepository;
	    private final FitnessUserToDTO userDTOConverter;
	    private final ExerciseToDTO exerciseDTOConverter;
	    private final ExercisePerformedToDTO exercisePerformedDTOConverter;

	    @Autowired
	    public ExerciseService(
	            @Nonnull final FitnessUserService userService,
	            @Nonnull final ReportDataService reportDataService,
	            @Nonnull final FitnessUserRepository userRepository,
	            @Nonnull final ExerciseRepository exerciseRepository,
	            @Nonnull final ExercisePerformedRepository exercisePerformedRepository,
	            @Nonnull final FitnessUserToDTO userDTOConverter,
	            @Nonnull final ExerciseToDTO exerciseDTOConverter,
	            @Nonnull final ExercisePerformedToDTO exercisePerformedDTOConverter
	    ) {
	        this.userService = userService;
	        this.reportDataService = reportDataService;
	        this.userRepository = userRepository;
	        this.exerciseRepository = exerciseRepository;
	        this.exercisePerformedRepository = exercisePerformedRepository;
	        this.userDTOConverter = userDTOConverter;
	        this.exerciseDTOConverter = exerciseDTOConverter;
	        this.exercisePerformedDTOConverter = exercisePerformedDTOConverter;
	    }

	    @Nonnull
	    public final List<ExercisePerformedDTO> findPerformedOnDate(
	            @Nonnull final Integer userId,
	            @Nonnull final Date date
	    ) {
	        final FitnessUser user = userRepository.findOne(userId);
	        WeightDTO weight = null;
	        if(userService.findWeightOnDate(userDTOConverter.convert(user), date) != null)
	        {
	         weight = userService.findWeightOnDate(userDTOConverter.convert(user), date);
	        } else
	        {
	        	
	        	 weight = new WeightDTO(null,new Date(new java.util.Date().getTime()),55.0);
	        }
	        	
	        	final WeightDTO weight2 = weight;
	        	

	        final List<ExercisePerformed> exercisesPerformed = exercisePerformedRepository.findByUserEqualsAndDateEquals(user, date);
	        return exercisesPerformed.stream()
	                .map( (ExercisePerformed exercisePerformed) -> {
	                    final ExercisePerformedDTO dto = exercisePerformedDTOConverter.convert(exercisePerformed);
	                    if (dto != null) {
	                        final int caloriesBurned = calculateCaloriesBurned(
	                                exercisePerformed.getExercise().getMetabolicEquivalent(),
	                                exercisePerformed.getMinutes(),
	                                weight2.getKilograms()
	                        );
	                        dto.setCaloriesBurned(caloriesBurned);
	                        final double pointsBurned = calculatePointsBurned(
	                                exercisePerformed.getExercise().getMetabolicEquivalent(),
	                                exercisePerformed.getMinutes(),
	                                weight2.getKilograms()
	                        );
	                        dto.setPointsBurned(pointsBurned);
	                    }
	                    return dto;
	                })
	                .collect(toList());
	    }

	    @Nonnull
	    public final List<ExerciseDTO> findPerformedRecently(
	            @Nonnull final Integer userId,
	            @Nonnull final Date currentDate
	    ) {
	        final FitnessUser user = userRepository.findOne(userId);
	        final Calendar calendar = new GregorianCalendar();
	        calendar.setTime(currentDate);
	        calendar.add(Calendar.DATE, -14);
	        final Date twoWeeksAgo = new Date(calendar.getTime().getTime());
	        final List<Exercise> recentExercises = exercisePerformedRepository.findByUserPerformedWithinRange(
	                user,
	                new Date(twoWeeksAgo.getTime()),
	                new Date(currentDate.getTime())
	        );
	        return recentExercises.stream()
	                .map(exerciseDTOConverter::convert)
	                .collect(toList());
	    }

	    public final void addExercisePerformed(
	            @Nonnull final Integer userId,
	            @Nonnull final Integer exerciseId,
	            @Nonnull final Date date
	    ) {
	        final boolean duplicate = findPerformedOnDate(userId, date).stream()
	                .anyMatch( (ExercisePerformedDTO exerciseAlreadyPerformed) -> exerciseAlreadyPerformed.getExercise().getId().equals(exerciseId) );
	        if (!duplicate) {
	            final FitnessUser user = userRepository.findOne(userId);
	            final Exercise exercise = exerciseRepository.findOne(exerciseId);
	            final ExercisePerformed exercisePerformed = new ExercisePerformed(
	                    user,
	                    exercise,
	                    date,
	                    1
	            );
	            exercisePerformedRepository.save(exercisePerformed);
	            reportDataService.updateUserFromDate(user, date);
	        }
	    }

	    public final void updateExercisePerformed(
	            @Nonnull final Integer exercisePerformedId,
	            final int minutes
	    ) {
	        final ExercisePerformed exercisePerformed = exercisePerformedRepository.findOne(exercisePerformedId);
	        exercisePerformed.setMinutes(minutes);
	        exercisePerformedRepository.save(exercisePerformed);
	        reportDataService.updateUserFromDate(exercisePerformed.getUser(), exercisePerformed.getDate());
	    }

	    public final void deleteExercisePerformed(@Nonnull final Integer exercisePerformedId) {
	        final ExercisePerformed exercisePerformed = exercisePerformedRepository.findOne(exercisePerformedId);
	        exercisePerformedRepository.delete(exercisePerformed);
	        reportDataService.updateUserFromDate(exercisePerformed.getUser(), exercisePerformed.getDate());
	    }

	    @Nullable
	    public final ExercisePerformedDTO findExercisePerformedById(@Nonnull final Integer exercisePerformedId) {
	        final ExercisePerformed exercisePerformed = exercisePerformedRepository.findOne(exercisePerformedId);
	        return exercisePerformedDTOConverter.convert(exercisePerformed);
	    }

	    @Nonnull
	    public final List<String> findAllCategories() {
	        return exerciseRepository.findAllCategories();
	    }

	    @Nonnull
	    public final List<ExerciseDTO> findExercisesInCategory(@Nonnull final String category) {
	        return exerciseRepository.findByCategoryOrderByDescriptionAsc(category).stream()
	                .map(exerciseDTOConverter::convert)
	                .collect(toList());
	    }

	    @Nonnull
	    public final List<ExerciseDTO> searchExercises(@Nonnull final String searchString) {
	        return exerciseRepository.findByDescriptionLike(searchString).stream()
	                .map(exerciseDTOConverter::convert)
	                .collect(toList());
	    }

	    public static int calculateCaloriesBurned(
	            final double metabolicEquivalent,
	            final int minutes,
	            final double weightInKilogram
	    ) {
	        final double weightInKilograms = weightInKilogram / 2.2;
	        return (int) (metabolicEquivalent * 3.5 * weightInKilograms / 200 * minutes);
	    }

	    public static double calculatePointsBurned(
	            final double metabolicEquivalent,
	            final int minutes,
	            final double weightInKilograms
	    ) {
	        final int caloriesBurnedPerHour = calculateCaloriesBurned(metabolicEquivalent, 60, weightInKilograms);
	        double pointsBurned;
	        if (caloriesBurnedPerHour < 400) {
	            pointsBurned = weightInKilograms * minutes * 0.000232;
	        } else if (caloriesBurnedPerHour < 900) {
	            pointsBurned = weightInKilograms * minutes * 0.000327;
	        } else {
	            pointsBurned = weightInKilograms * minutes * 0.0008077;
	        }
	        return pointsBurned;
	    }

}
