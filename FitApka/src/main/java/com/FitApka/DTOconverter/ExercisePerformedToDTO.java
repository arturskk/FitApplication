package com.FitApka.DTOconverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.model.ExercisePerformed;

@Component
public class ExercisePerformedToDTO implements Converter<ExercisePerformed, ExercisePerformedDTO>{

	private final ExerciseToDTO exerciseDTOConverter;

    @Autowired
    public ExercisePerformedToDTO(@Nonnull final ExerciseToDTO exerciseDTOConverter) {
        this.exerciseDTOConverter = exerciseDTOConverter;
    }

    @Override
    @Nullable
    public ExercisePerformedDTO convert(@Nullable final ExercisePerformed exercisePerformed) {
        ExercisePerformedDTO dto = null;
        if (exercisePerformed != null) {
            dto = new ExercisePerformedDTO();
            dto.setId(exercisePerformed.getId());
            dto.setUserId(exercisePerformed.getUser().getId());
            dto.setExercise(exerciseDTOConverter.convert(exercisePerformed.getExercise()));
            dto.setDate(exercisePerformed.getDate());
            dto.setMinutes(exercisePerformed.getMinutes());
        }
        return dto;
    }

}
