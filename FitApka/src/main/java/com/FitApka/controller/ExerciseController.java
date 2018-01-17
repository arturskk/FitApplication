package com.FitApka.controller;

import static java.util.stream.Collectors.toList;

import java.sql.Date;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FitApka.DTO.ExerciseDTO;
import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.service.ExerciseService;


@Controller
final class ExerciseController extends AbstractController  {

	 private final ExerciseService exerciseService;

	    private final Function<ExerciseDTO, ExerciseDTO> truncateExerciseDescriptionFunction = (ExerciseDTO exerciseDTO) -> {
	        if (exerciseDTO.getDescription().length() > 50) {
	            final String description = exerciseDTO.getDescription().substring(0, 47) + "...";
	            exerciseDTO.setDescription(description);
	        }
	        return exerciseDTO;
	    };

	    @Autowired
	    public ExerciseController(@Nonnull final ExerciseService exerciseService) {
	        this.exerciseService = exerciseService;
	    }

	    @RequestMapping(value = "/exercise", method = RequestMethod.GET)
	    @Nonnull
	    public final String viewMainExercisePage(
	            @Nullable
	            @RequestParam(value = "date", required = false)
	            final String dateString,

	            @Nonnull final Model model
	    ) {
	        final FitnessUserDTO userDTO = currentAuthenticatedUser();
	        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);

	        final List<ExerciseDTO> exercisesPerformedRecently = exerciseService.findPerformedRecently(userDTO.getId(), date)
	                .stream()
	                .map(truncateExerciseDescriptionFunction)
	                .collect(toList());

	        final List<String> categories = exerciseService.findAllCategories();
	        final String firstCategory = (categories.size() > 0) ? categories.get(0) : "";
	        final List<ExerciseDTO> exercisesInCategory = exerciseService.findExercisesInCategory(firstCategory)
	                .stream()
	                .map(truncateExerciseDescriptionFunction)
	                .collect(toList());

	        final List<ExercisePerformedDTO> exercisePerformedThisDate = exerciseService.findPerformedOnDate(userDTO.getId(), date);
	        int totalMinutes = 0;
	        int totalCaloriesBurned = 0;
	        for (final ExercisePerformedDTO exercisePerformed : exercisePerformedThisDate) {
	            totalMinutes += exercisePerformed.getMinutes();
	            totalCaloriesBurned += exercisePerformed.getCaloriesBurned();
	        }

	        model.addAttribute("dateString", dateString);
	        model.addAttribute("exercisesPerformedRecently", exercisesPerformedRecently);
	        model.addAttribute("categories", categories);
	        model.addAttribute("exercisesInCategory", exercisesInCategory);
	        model.addAttribute("exercisesPerformedThisDate", exercisePerformedThisDate);
	        model.addAttribute("totalMinutes", totalMinutes);
	        model.addAttribute("totalCaloriesBurned", totalCaloriesBurned);
	        return EXERCISE_TEMPLATE;
	    }

	    @RequestMapping(value = "/exercise/performed/add")
	    @Nonnull
	    public final String addExercisePerformed(
	            @Nonnull @RequestParam(value = "exerciseId", required = true) final String exerciseIdString,
	            @Nullable @RequestParam(value = "date", required = false) final String dateString,
	            @Nonnull final Model model
	    ) {
	        final FitnessUserDTO userDTO = currentAuthenticatedUser();
	        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
	        final Integer exerciseId = Integer.valueOf(exerciseIdString);
	        exerciseService.addExercisePerformed(userDTO.getId(), exerciseId, date);
	        return viewMainExercisePage(dateString, model);
	    }

	    @RequestMapping(value = "/exercise/performed/update")
	    @Nonnull
	    public final String updateExercisePerformed(
	            @Nonnull @RequestParam(value = "exercisePerformedId", required = true) final String exercisePerformedId,
	            @RequestParam(value = "minutes", required = true, defaultValue = "1") final int minutes,
	            @Nonnull @RequestParam(value = "action", required = true) final String action,
	            @Nonnull final Model model
	    ) {
	        final FitnessUserDTO userDTO = currentAuthenticatedUser();
	        final Integer exercisePerformedInteger= Integer.valueOf(exercisePerformedId);
	        final ExercisePerformedDTO exercisePerformedDTO = exerciseService.findExercisePerformedById(exercisePerformedInteger);
	        final String dateString = dateFormat.format(exercisePerformedDTO.getDate());
	        if (!userDTO.getId().equals(exercisePerformedDTO.getUserId())) {
	            System.out.println("\n\nThis user is unable to update this exercise performed\n");
	        } else if (action.equalsIgnoreCase("update")) {
	            exerciseService.updateExercisePerformed(exercisePerformedInteger, minutes);
	        } else if (action.equalsIgnoreCase("delete")) {
	            exerciseService.deleteExercisePerformed(exercisePerformedInteger);
	        }
	        return viewMainExercisePage(dateString, model);
	    }
	    
	    
	    @GetMapping("/api/exerciseperformed/{date}")
	    @ResponseBody
	    public final List<ExercisePerformedDTO> loadExercisesPerformed(
	            @PathVariable(name = "date") final String dateString,
	            final HttpServletRequest request
	    ) {
	    	  final FitnessUserDTO userDTO = currentAuthenticatedUser();
	        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
	        return exerciseService.findPerformedOnDate(userDTO.getId(), date);
	    }

	    @RequestMapping(value = "/exercise/bycategory/{category}")
	    @Nonnull
	    public final
	    @ResponseBody
	    List<ExerciseDTO> findExercisesInCategory(@Nonnull @PathVariable final String category) {
	        return exerciseService.findExercisesInCategory(category);
	    }

	    @RequestMapping(value = "/exercise/search/{searchString}")
	    @Nonnull
	    public final
	    @ResponseBody
	    List<ExerciseDTO> searchExercises(@Nonnull @PathVariable final String searchString) {
	        return exerciseService.searchExercises(searchString);
	    }
	
}
