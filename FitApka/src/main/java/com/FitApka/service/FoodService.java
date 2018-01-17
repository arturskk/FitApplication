package com.FitApka.service;

import static java.util.stream.Collectors.toList;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.DTO.FoodDTO;
import com.FitApka.DTO.FoodEatenDTO;
import com.FitApka.DTOconverter.FoodEatenToDTO;
import com.FitApka.DTOconverter.FoodToDTO;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.Food;
import com.FitApka.model.FoodEaten;
import com.FitApka.repository.FitnessUserRepository;
import com.FitApka.repository.FoodEatenRepository;
import com.FitApka.repository.FoodRepository;


@Service
public final class FoodService {

		private final ReportDataService reportDataService;
	    private final FitnessUserRepository userRepository;
	    private final FoodRepository foodRepository;
	    private final FoodEatenRepository foodEatenRepository;
	    private final FoodToDTO foodDTOConverter;
	    private final FoodEatenToDTO foodEatenDTOConverter;

	    @Autowired
	    public FoodService(
	            @Nonnull final ReportDataService reportDataService,
	            @Nonnull final FitnessUserRepository userRepository,
	            @Nonnull final FoodRepository foodRepository,
	            @Nonnull final FoodEatenRepository foodEatenRepository,
	            @Nonnull final FoodToDTO foodDTOConverter,
	            @Nonnull final FoodEatenToDTO foodEatenDTOConverter
	    ) {
	        this.reportDataService = reportDataService;
	        this.userRepository = userRepository;
	        this.foodRepository = foodRepository;
	        this.foodEatenRepository = foodEatenRepository;
	        this.foodDTOConverter = foodDTOConverter;
	        this.foodEatenDTOConverter = foodEatenDTOConverter;
	    }
	    
	    @Nonnull
	    public final List<FoodEatenDTO> findEatenOnDate(
	            @Nonnull final Integer userId,
	            @Nonnull final Date date
	    ) {
	        final FitnessUser user = userRepository.findOne(userId);
	        return foodEatenRepository.findByUserEqualsAndDateEquals(user, date)
	                .stream()
	                .map(foodEatenDTOConverter::convert)
	                .collect(toList());
	    }

	    @Nonnull
	    public final List<FoodDTO> findEatenRecently(
	            @Nonnull final Integer userId,
	            @Nonnull final Date currentDate
	    ) {
	    	
	        final FitnessUser user = userRepository.findOne(userId);
	        final Calendar calendar = new GregorianCalendar();
	        calendar.setTime(currentDate);
	        calendar.add(Calendar.DATE, -14);
	        final Date twoWeeksAgo = new Date(calendar.getTime().getTime());
	        return foodEatenRepository.findByUserEatenWithinRange(user, new Date(twoWeeksAgo.getTime()), new Date(currentDate.getTime()) )
	                .stream()
	                .map(foodDTOConverter::convert)
	                .collect(toList());
	    
	    }

	    @Nullable
	    public final FoodEatenDTO findFoodEatenById(@Nonnull final Integer foodEatenId) {
	        final FoodEaten foodEaten = foodEatenRepository.findOne(foodEatenId);
	        return foodEatenDTOConverter.convert(foodEaten);
	    }

	    public final FoodEatenDTO addFoodEaten(
	            @Nonnull final Integer userId,
	            @Nonnull final Integer foodId,
	            @Nonnull final Date date
	    ) {
	        final boolean duplicate = findEatenOnDate(userId, date).stream()
	                .anyMatch( (FoodEatenDTO foodAlreadyEaten) -> foodAlreadyEaten.getFood().getId().equals(foodId) );
	        if (!duplicate) {
	            final FitnessUser user = userRepository.findOne(userId);
	            final Food food = foodRepository.findOne(foodId);
	            final FoodEaten foodEaten = new FoodEaten(
	                    user,
	                    food,
	                    date,
	                    food.getDefaultServingType(),
	                    food.getServingTypeQty()
	            );
	            foodEatenRepository.save(foodEaten);
	            reportDataService.updateUserFromDate(user, date);
	            return foodEatenDTOConverter.convert(foodEaten);
	        }
	    else {
            return null;
        }
	        
	    }

	    public final FoodEatenDTO  updateFoodEaten(
	            @Nonnull final Integer foodEatenId,
	            final double servingQty,
	            @Nonnull final Food.ServingType servingType
	    ) {
	        final FoodEaten foodEaten = foodEatenRepository.findOne(foodEatenId);
	        foodEaten.setServingQty(servingQty);
	        foodEaten.setServingType(servingType);
	        foodEatenRepository.save(foodEaten);
	        reportDataService.updateUserFromDate(foodEaten.getUser(), foodEaten.getDate());
	        return foodEatenDTOConverter.convert(foodEaten);
	    }

	    public final void deleteFoodEaten(@Nonnull final Integer foodEatenId) {
	        final FoodEaten foodEaten = foodEatenRepository.findOne(foodEatenId);
	        reportDataService.updateUserFromDate(foodEaten.getUser(), foodEaten.getDate());
	        foodEatenRepository.delete(foodEaten);
	    }

	    @Nonnull
	    public final List<FoodDTO> searchFoods(
	            @Nonnull final Integer userId,
	            @Nonnull final String searchString
	    ) {
	        final FitnessUser user = userRepository.findOne(userId);
	        final List<Food> foods = foodRepository.findByNameLike(user, searchString);
	        return foods.stream().map(foodDTOConverter::convert).collect(toList());
	    }

	    @Nullable
	    public final FoodDTO getFoodById(@Nonnull final Integer foodId) {
	        final Food food = foodRepository.findOne(foodId);
	        return foodDTOConverter.convert(food);
	    }

	    @Nonnull
	    public final String updateFood(
	            @Nonnull final FoodDTO foodDTO,
	            @Nonnull final FitnessUserDTO userDTO
	    ) {
	        String resultMessage = "";
	        
	        if (foodDTO.getOwnerId() == null || foodDTO.getOwnerId().equals(userDTO.getId())) {

	            final FitnessUser user = userRepository.findOne(userDTO.getId());
	            final List<Food> foodsWithSameNameOwnedByThisUser = foodRepository.findByOwnerEqualsAndNameEquals(user, foodDTO.getName());
	            final boolean noConflictsFound = foodsWithSameNameOwnedByThisUser
	                    .stream()
	                    .allMatch( (Food food) -> foodDTO.getId().equals(food.getId()) );
	            if (noConflictsFound) {
	                Food food = null;
	                Date dateFirstEaten = null;
	                if (foodDTO.getOwnerId() == null) {
	                    food = new Food();
	                    food.setOwner(user);
	                    dateFirstEaten = new Date(System.currentTimeMillis());
	                } else {
	                    food = foodRepository.findOne(foodDTO.getId());
	                    final List<FoodEaten> foodsEatenSortedByDate = foodEatenRepository.findByUserEqualsAndFoodEqualsOrderByDateAsc(user, food);
	                    dateFirstEaten = (foodsEatenSortedByDate != null && !foodsEatenSortedByDate.isEmpty())
	                            ? foodsEatenSortedByDate.get(0).getDate() : new Date(System.currentTimeMillis());
	                }
	                food.setName(foodDTO.getName());
	                food.setDefaultServingType(foodDTO.getDefaultServingType());
	                food.setServingTypeQty(foodDTO.getServingTypeQty());
	                food.setCalories(foodDTO.getCalories());
	                food.setFat(foodDTO.getFat());
	                food.setSaturatedFat(foodDTO.getSaturatedFat());
	                food.setCarbs(foodDTO.getCarbs());
	                food.setFiber(foodDTO.getFiber());
	                food.setSugar(foodDTO.getSugar());
	                food.setProtein(foodDTO.getProtein());
	                food.setSodium(foodDTO.getSodium());
	                foodRepository.save(food);
	                resultMessage = "Success!";
	                reportDataService.updateUserFromDate(user, dateFirstEaten);
	            } else {
	                resultMessage = "Error:  You already have another customized food with this name.";
	            }

	        } else {
	            resultMessage = "Error:  You are attempting to modify another user's customized food.";
	        }
	        return resultMessage;
	    }

	    @Nonnull
	    public final String createFood(
	            @Nonnull final FoodDTO foodDTO,
	            @Nonnull final FitnessUserDTO userDTO
	    ) {
	        String resultMessage = "";

	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        final List<Food> foodsWithSameNameOwnedByThisUser = foodRepository.findByOwnerEqualsAndNameEquals(user, foodDTO.getName());

	        if (foodsWithSameNameOwnedByThisUser.isEmpty()) {
	            final Food food = new Food();
	            food.setOwner(user);
	            food.setName(foodDTO.getName());
	            food.setDefaultServingType(foodDTO.getDefaultServingType());
	            food.setServingTypeQty(foodDTO.getServingTypeQty());
	            food.setCalories(foodDTO.getCalories());
	            food.setFat(foodDTO.getFat());
	            food.setSaturatedFat(foodDTO.getSaturatedFat());
	            food.setCarbs(foodDTO.getCarbs());
	            food.setFiber(foodDTO.getFiber());
	            food.setSugar(foodDTO.getSugar());
	            food.setProtein(foodDTO.getProtein());
	            food.setSodium(foodDTO.getSodium());
	            foodRepository.save(food);
	            resultMessage = "Success!";
	            reportDataService.updateUserFromDate(user, new Date(System.currentTimeMillis()));
	        } else {
	            resultMessage = "Error:  You already have another customized food with this name.";
	        }
	        return resultMessage;
	    }
	    
	    
	    
	    
	
}
