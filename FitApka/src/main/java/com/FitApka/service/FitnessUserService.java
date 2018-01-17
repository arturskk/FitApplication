package com.FitApka.service;

import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.DTO.WeightDTO;
import com.FitApka.DTOconverter.FitnessUserToDTO;
import com.FitApka.DTOconverter.WeightToDTO;
import com.FitApka.config.SecurityConfig;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.Weight;
import com.FitApka.repository.FitnessUserRepository;
import com.FitApka.repository.WeightRepository;

@Service
public final class FitnessUserService {

	
		private final ReportDataService reportDataService;
	    private final FitnessUserRepository userRepository;
	    private final WeightRepository weightRepository;
	    private final FitnessUserToDTO userDTOConverter;
	    private final WeightToDTO weightDTOConverter;

	    @Autowired
	    public FitnessUserService(
	            @Nonnull final ReportDataService reportDataService,
	            @Nonnull final FitnessUserRepository userRepository,
	            @Nonnull final WeightRepository weightRepository,
	            @Nonnull final FitnessUserToDTO userDTOConverter,
	            @Nonnull final WeightToDTO weightDTOConverter
	    ) {
	        this.reportDataService = reportDataService;
	        this.userRepository = userRepository;
	        this.weightRepository = weightRepository;
	        this.userDTOConverter = userDTOConverter;
	        this.weightDTOConverter = weightDTOConverter;
	    }

	    @Nullable
	    public FitnessUserDTO findUser(@Nonnull final Integer userId) {
	        final FitnessUser user = userRepository.findOne(userId);
	        return userDTOConverter.convert(user);
	    }

	    @Nonnull
	    public List<FitnessUserDTO> findAllUsers() {
	        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
	                .map(userDTOConverter::convert)
	                .collect(toList());
	    }

	    public void createUser(
	            @Nonnull final FitnessUserDTO userDTO,
	            @Nonnull final String password
	    ) {
	        final FitnessUser user = new FitnessUser(
	                userDTO.getGender(),
	                userDTO.getBirthdate(),
	                userDTO.getHeightInCm(),
	                userDTO.getActivityLevel(),
	                userDTO.getEmail(),
	                encryptPassword(password),
	                userDTO.getFirstName(),
	                userDTO.getLastName(),
	                userDTO.getTimeZone(),
	                new Timestamp(new java.util.Date().getTime()),
	                new Timestamp(new java.util.Date().getTime())
	        );
	        userRepository.save(user);
	        reportDataService.updateUserFromDate(user, new Date(System.currentTimeMillis()));
	    }

	    public void updateUser(@Nonnull final FitnessUserDTO userDTO) {
	        updateUser(userDTO, null);
	    }

	    public void updateUser(
	            @Nonnull final FitnessUserDTO userDTO,
	            @Nullable final String newPassword
	    ) {
	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        user.setGender(userDTO.getGender());
	        user.setBirthdate(userDTO.getBirthdate());
	        user.setHeightInCm(userDTO.getHeightInCm());
	        user.setActivityLevel(userDTO.getActivityLevel());
	        user.setEmail(userDTO.getEmail());
	        user.setFirstName(userDTO.getFirstName());
	        user.setLastName(userDTO.getLastName());
	        user.setTimeZone(userDTO.getTimeZone());
	        if (newPassword != null && !newPassword.isEmpty()) {
	            user.setPasswordHash(encryptPassword(newPassword));
	        }
	        final java.util.Date lastUpdatedDate = reportDataService.adjustDateForTimeZone(new Date(new java.util.Date().getTime()), ZoneId.of(userDTO.getTimeZone()));
	        user.setLastUpdatedTime(new Timestamp(lastUpdatedDate.getTime()));
	        userRepository.save(user);
	        refreshAuthenticatedUser();
	        reportDataService.updateUserFromDate(user, new Date(System.currentTimeMillis()));
	    }

	    public void refreshAuthenticatedUser() { 
	        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
	        if (currentAuthentication.getPrincipal() instanceof SecurityConfig.SpringUserDetails) {
	            final SecurityConfig.SpringUserDetails currentPrincipal = (SecurityConfig.SpringUserDetails) currentAuthentication.getPrincipal();
	            final FitnessUser refreshedUser = userRepository.findOne(currentPrincipal.getUserDTO().getId());
	            final SecurityConfig.SpringUserDetails refreshedPrincipal = new SecurityConfig.SpringUserDetails(userDTOConverter.convert(refreshedUser), refreshedUser.getPasswordHash());
	            final Authentication newAuthentication = new UsernamePasswordAuthenticationToken(refreshedPrincipal, refreshedUser.getPasswordHash(), currentPrincipal.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	        } else {
	            System.out.println("The currently-authenticated principal is not an instance of type SecurityConfig.SpringUserDetails");
	        }
	    }

	    @Nullable
	    public WeightDTO findWeightOnDate(
	            @Nonnull final FitnessUserDTO userDTO,
	            @Nonnull final Date date
	    ) {
	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        final Weight weight = weightRepository.findByUserMostRecentOnDate(user, date);
	        return weightDTOConverter.convert(weight);
	    }

	    public void updateWeight(
	            @Nonnull final FitnessUserDTO userDTO,
	            @Nonnull final Date date,
	            final double kilograms
	    ) {
	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        Weight weight = weightRepository.findByUserAndDate(user, date);
	        if (weight == null) {
	            weight = new Weight(
	                    user,
	                    date,
	                    kilograms
	            );
	        } else {
	            weight.setKilograms(kilograms);
	        }
	        weightRepository.save(weight);
	        reportDataService.updateUserFromDate(user, date);
	        refreshAuthenticatedUser();
	    }

	    public boolean verifyPassword(
	            @Nonnull final FitnessUserDTO userDTO,
	            @Nonnull final String password
	    ) {
	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        return passwordEncoder.matches(password, user.getPasswordHash());
	    }

	    @Nonnull
	    public String encryptPassword(@Nonnull final String rawPassword) {
	        final String salt = BCrypt.gensalt(10, new SecureRandom());
	        return BCrypt.hashpw(rawPassword, salt);
	    }

	    @Nullable
	    public String getPasswordHash(@Nonnull final FitnessUserDTO userDTO) {
	        final FitnessUser user = userRepository.findOne(userDTO.getId());
	        return user.getPasswordHash();
	    }

	
	
	
	
	
	
}
