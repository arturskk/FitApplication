package com.FitApka.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.config.SecurityConfig;



public class AbstractController {
	
    public static final String LOGIN_TEMPLATE = "login";
    static final String SIGNUP_TEMPLATE = "signup";
    static final String PROFILE_TEMPLATE = "profile";
    static final String FOOD_TEMPLATE = "food";
    static final String EXERCISE_TEMPLATE = "exercise";
    static final String REPORT_TEMPLATE = "report";
    
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Nullable
    final FitnessUserDTO currentAuthenticatedUser() {
        FitnessUserDTO userDTO = null;
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof SecurityConfig.SpringUserDetails) {
            final SecurityConfig.SpringUserDetails userDetails = (SecurityConfig.SpringUserDetails) authentication.getPrincipal();
            userDTO = userDetails.getUserDTO();
        }
        return userDTO;
    }
    
    final java.sql.Date stringToSqlDate(final String dateString) {
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf(dateString);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
            date = new java.sql.Date(new Date().getTime());
        }
        return date;
    }

    final java.sql.Date todaySqlDateForUser(final FitnessUserDTO user) {
        if (user == null) {
            return new java.sql.Date(new Date().getTime());
        } else {
            final ZoneId timeZone = ZoneId.of(user.getTimeZone());
            final ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone);
            return new java.sql.Date(zonedDateTime.toLocalDate().atStartOfDay(timeZone).toInstant().toEpochMilli());
        }
    }
}
