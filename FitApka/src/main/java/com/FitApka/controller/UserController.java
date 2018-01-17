package com.FitApka.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.DTO.WeightDTO;
import com.FitApka.model.FitnessUser;
import com.FitApka.service.FitnessUserService;
import com.FitApka.config.SecurityConfig;


@Controller
public final class UserController extends AbstractController{

	
	
	private final FitnessUserService userService;
	
	
	@PostMapping(value = "/api/user/save")
	@ResponseBody
	 public final String aveProfile(
            @RequestBody final Map<String, Object> payload,
            final HttpServletRequest request
    ) throws IOException {
		
    	
    	final FitnessUserDTO userDTO = currentAuthenticatedUser();
        userDTO.setGender(FitnessUser.Gender.fromString((String) payload.get("gender")));
        userDTO.setBirthdate(stringToSqlDate((String) payload.get("birthdate")));
        userDTO.setHeightInCm((Integer) payload.get("heightInCm"));
        userDTO.setActivityLevel(FitnessUser.ActivityLevel.SEDENTARY);
        userDTO.setFirstName((String) payload.get("firstName"));
        userDTO.setLastName((String) payload.get("lastName"));
        userDTO.setTimeZone((String) payload.get("timeZone"));
        userService.updateUser(userDTO);
        
        return "Profile changed";
        
        
    }
	
	

    @Autowired
    public UserController(@Nonnull final FitnessUserService userService) {
        this.userService = userService;
    }
    
   
    
    @GetMapping(value = "/")
    public final void handleRootUrl(final HttpServletResponse response) throws IOException {
        response.sendRedirect("/profil.html");
    }
    
   
    @RequestMapping(value = {"/profile2"}, method = RequestMethod.GET, produces="text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final String ControllerTest() {
    	
    	
    	 String resultMessage = "artur";
    	 return resultMessage;
    

    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    
    
    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    @Nonnull
    public final String createUser( 
    		@Nullable
            @RequestParam(value = "date", required = false)
            final String dateString,
            @Nonnull
            final Model model )
    {
    
    	final FitnessUserDTO userDTO = new FitnessUserDTO();
    	
    	model.addAttribute("dateString", dateString);
        model.addAttribute("allTimeZones", new TreeSet<String>(ZoneId.getAvailableZoneIds()));
    	model.addAttribute("allActivityLevels", FitnessUser.ActivityLevel.values());
    	model.addAttribute("user", userDTO);
    	model.addAttribute("allGenders", FitnessUser.Gender.values());
    	
    	
    	
    	
    	return SIGNUP_TEMPLATE;
    	
       
    }
    
    
    
    @RequestMapping(value = {"/signup/save"}, method = RequestMethod.POST)
    @Nonnull
    public final String createUser(
    		@Nullable
            @RequestParam(value = "date", required = false)
            final String dateString,
            @NotNull
            @RequestParam(value = "Password")
            final String Password,
            @ModelAttribute("user")
            @Valid
            final FitnessUserDTO userDTO, 
            @Nonnull
            final BindingResult result,
            @Nonnull
            final Model model )
    {
    	
 
    	
    	
    	 if (result.hasErrors() || Password == null || Password.isEmpty()) {
    	    	model.addAttribute("dateString", dateString);
    	        model.addAttribute("allTimeZones", new TreeSet<String>(ZoneId.getAvailableZoneIds()));
    	    	model.addAttribute("allActivityLevels", FitnessUser.ActivityLevel.values());
    	    	model.addAttribute("user", userDTO);
    	    	model.addAttribute("allGenders", FitnessUser.Gender.values());
    	    	if(Password == null || Password.isEmpty())
    	    	{
    	    	String pwerror = "Password can't be empty";
        		model.addAttribute("error", pwerror);
    	    	}
    		 
    		 return SIGNUP_TEMPLATE;
    		
         }
    	 
    
    	
    	userService.createUser(userDTO,Password);
    	
    	return LOGIN_TEMPLATE;
    }
    
    
    
    
    
    @GetMapping(value = "/api/user/weight/{date}")
    @ResponseBody
    public final Double loadWeight(
            @PathVariable(name = "date", required = false) final String dateString,
            final HttpServletRequest request
    ) {
    	   final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final java.sql.Date date = (dateString == null || dateString.isEmpty())
                ? todaySqlDateForUser(userDTO)
                : stringToSqlDate(dateString);
        final WeightDTO weightDTO = userService.findWeightOnDate(userDTO, date);
        return weightDTO == null ? null : weightDTO.getKilograms();
    }

    @PostMapping(value = "/api/user/weight/{date}", consumes = "application/json")
    public final void saveWeight(
            @PathVariable(name = "date", required = false) final String dateString,
            @RequestBody final Map<String, Object> payload,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws IOException {
        double weight;
        try {
            weight = Double.parseDouble(payload.get("weight").toString());
        } catch (NullPointerException | NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        final FitnessUserDTO userDTO = currentAuthenticatedUser();
        final java.sql.Date date = (dateString == null || dateString.isEmpty())
                ? todaySqlDateForUser(userDTO)
                : stringToSqlDate(dateString);
        userService.updateWeight(userDTO, date, weight);
    }
    
    
    
    
    

    
    
    @GetMapping(value = "/api/user")
    @ResponseBody
    public final FitnessUserDTO loadProfile() {
    	final FitnessUserDTO userDTO = currentAuthenticatedUser();
        return userDTO;
    }
    
    
    @PostMapping(value = "/api/user/password")
    @ResponseBody
    public final String savePassword(
            @RequestBody final Map<String, Object> payload,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final String currentPassword = (String) payload.get("currentPassword");
        final String newPassword = (String) payload.get("newPassword");
        final String reenterNewPassword = (String) payload.get("reenterNewPassword");

        final FitnessUserDTO userDTO = currentAuthenticatedUser();
        if (!newPassword.equals(reenterNewPassword)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "The \"New Password\" and \"Re-enter New Password\" fields do not match";
        } else if (!userService.verifyPassword(userDTO, currentPassword)) {
            // "Current Password" field doesn't match the user's current password
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "The \"Current Password\" field does not match your current password";
        }

        userService.updateUser(userDTO, newPassword);
        return "Password changed";
    }
    
    
}

    
    
    
    
    
    
    

