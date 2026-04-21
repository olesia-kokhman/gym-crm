package org.olesia.dao;

import org.olesia.model.Training;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingDao {

    Training save(Training training);

    Optional<Training> findById(UUID id);
    List<Training> findAll();
}
