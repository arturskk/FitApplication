package com.FitApka.DTOconverter;

import javax.annotation.Nullable;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.DTO.ReportDataDTO;
import com.FitApka.model.ExercisePerformed;
import com.FitApka.model.ReportData;

@Component
public final class ReportDataToDTO  implements Converter< ReportData, ReportDataDTO>{

	   @Override
	    @Nullable
	    public ReportDataDTO convert(@Nullable final ReportData reportData) {
	        ReportDataDTO dto = null;
	        if (reportData != null) {
	            dto = new ReportDataDTO();
	            dto.setId(reportData.getId());
	            dto.setUserId(reportData.getUser().getId());
	            dto.setDate(reportData.getDate());
	            dto.setKilograms(reportData.getKilograms());
	            dto.setNetCalories(reportData.getNetCalories());
	            dto.setNetPoints(reportData.getNetPoints());
	        }
	        return dto;
	    }

}
