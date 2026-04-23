package org.olesia.service;

import org.olesia.model.Training;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingService {

    Training create(Training training);

    Optional<Training> findById(UUID id);

    List<Training> findAll();
}