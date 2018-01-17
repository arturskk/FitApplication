package com.FitApka.repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.repository.CrudRepository;

import com.FitApka.model.FitnessUser;


public interface FitnessUserRepository extends CrudRepository<FitnessUser, Integer> {

    @Nullable
    public FitnessUser findByEmailEquals(@Nonnull String email);

}
