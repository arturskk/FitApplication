package com.FitApka.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.DTO.FoodDTO;
import com.FitApka.DTO.FoodEatenDTO;
import com.FitApka.model.Food;
import com.FitApka.service.ExerciseService;
import com.FitApka.service.FoodService;
@Controller
@RequestMapping("/api")
final class FoodController extends AbstractController {

    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }
    
    
    @GetMapping(value = "/")
    public final void handleRootUrl(final HttpServletResponse response) throws IOException {
        response.sendRedirect("/profil.html");
    }
    
    @RequestMapping(value = "/food/eaten/add")
    @ResponseStatus(value = HttpStatus.OK)
    public final void addFoodEaten(
            @Nonnull @RequestParam(value = "foodId", required = true) final String foodIdString,
            @Nonnull @RequestParam(value = "date", required = false) final String dateString,
            @Nonnull final Model model
    ) {
        final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
        final Integer foodId = Integer.valueOf(foodIdString);
        foodService.addFoodEaten(userDTO.getId(), foodId, date);
    }
    
    
    
    
    @GetMapping("/foodeaten/{date}")
    @ResponseBody
    public final List<FoodEatenDTO> loadFoodsEaten(
            @PathVariable(name = "date") final String dateString,
            final HttpServletRequest request
    ) {
    	  final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
        return foodService.findEatenOnDate(userDTO.getId(), date);
    }

    @PostMapping("/foodeaten")
    @ResponseBody
    public final FoodEatenDTO addFoodEaten(
            @RequestBody final Map<String, Object> payload,
            final HttpServletRequest request
           
    ) {
    	final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final String foodIdString = (String) payload.get("id");
        final String dateString = (String) payload.get("date");
        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
        final Integer foodId = Integer.valueOf(foodIdString);
        return foodService.addFoodEaten(userDTO.getId(), foodId, date);
    }

    @PutMapping("/foodeaten/{id}")
    @ResponseBody
    public final FoodEatenDTO updateFoodEaten(
            @PathVariable(name = "id") final String foodEatenIdString,
            @RequestBody final Map<String, Object> payload,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final Integer foodEatenId = Integer.valueOf(foodEatenIdString);
        final FoodEatenDTO foodEatenDTO = foodService.findFoodEatenById(foodEatenId);
        if (foodEatenDTO == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        final FitnessUserDTO userDTO = currentAuthenticatedUser();
        if (!foodEatenDTO.getUserId().equals(userDTO.getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        Food.ServingType servingType;
        Double servingQty;
        try {
            servingType = Food.ServingType.fromString((String) payload.get("servingType"));
            servingQty = Double.parseDouble((String) payload.get("servingQty"));
            foodService.updateFoodEaten(foodEatenId, servingQty, servingType);
        } catch (final NullPointerException | NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        return foodService.updateFoodEaten(foodEatenId, servingQty, servingType);
    }

    @DeleteMapping("/foodeaten/{id}")
    public final void deleteFoodEaten(
            @PathVariable(name = "id") final String idString,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final Integer foodEatenId = Integer.valueOf(idString);
        final FoodEatenDTO foodEatenDTO = foodService.findFoodEatenById(foodEatenId);
        if (foodEatenDTO == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final FitnessUserDTO userDTO = currentAuthenticatedUser();
        if (!foodEatenDTO.getUserId().equals(userDTO.getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        foodService.deleteFoodEaten(foodEatenId);
    }

    @GetMapping("/food/recent/{date}")
    @ResponseBody
    public final List<FoodDTO> loadRecentFoods(
            @PathVariable(name = "date") final String dateString,
            final HttpServletRequest request
    ) {
    	 final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final Date date = dateString == null ? todaySqlDateForUser(userDTO) : stringToSqlDate(dateString);
        return foodService.findEatenRecently(userDTO.getId(), date);
    }

    @RequestMapping(value = "/food/search/{searchString}")
    @ResponseBody
    public final List<FoodDTO> searchFoods(
            @PathVariable final String searchString,
            final HttpServletRequest request
    ) {
    	 final FitnessUserDTO userDTO = currentAuthenticatedUser();
        return foodService.searchFoods(userDTO.getId(), searchString);
    }

    @RequestMapping(value = "/food/get/{foodId}")
    @ResponseBody
    public final FoodDTO getFood(
            @PathVariable final String foodId,
            final HttpServletRequest request
    ) {
    	 final FitnessUserDTO userDTO = currentAuthenticatedUser();
        FoodDTO foodDTO = foodService.getFoodById(Integer.valueOf(foodId));
        // Only return foods that are visible to the requesting user
        if (foodDTO.getOwnerId() != null && !foodDTO.getOwnerId().equals(userDTO.getId())) {
            foodDTO = null;
        }
        return foodDTO;
    }

    @RequestMapping(value = "/food/update")
    @ResponseBody
    public final String createOrUpdateFood(
            @ModelAttribute final FoodDTO foodDTO,
            final HttpServletRequest request,
            final Model model
    ) {
    	final FitnessUserDTO userDTO = currentAuthenticatedUser();
        String resultMessage;
        if (foodDTO.getId() == null) {
            resultMessage = foodService.createFood(foodDTO, userDTO);
        } else {
            resultMessage = foodService.updateFood(foodDTO, userDTO);
        }
        return resultMessage;
    }

}