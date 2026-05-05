package org.olesia.service;

import org.olesia.model.Trainee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TraineeService {

    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    void deleteById(UUID id);

    Optional<Trainee> findById(UUID id);

    List<Trainee> findAll();
}