package com.FitApka.repository;

import java.sql.Date;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.FitnessUser;
import com.FitApka.model.ReportData;

public interface ReportDataRepository extends CrudRepository<ReportData, Integer> {
	
	@Nonnull
    public List<ReportData> findByUserOrderByDateAsc(@Nonnull FitnessUser user);
	
	@Nonnull
	public List<ReportData> findByUserAndDateOrderByDateAsc(@Nonnull FitnessUser user, @Nonnull Date date);
	 
	@Nonnull
	public List<ReportData> findByUserAndDateBetweenOrderByDateAsc(@Nonnull FitnessUser user, @Nonnull Date startDate, @Nonnull Date endDate);

}
