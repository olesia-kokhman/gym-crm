package org.olesia.dao;

import org.olesia.model.Trainee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TraineeDao {

    Trainee save(Trainee trainee);
    Trainee update(Trainee trainee);

    void deleteById(UUID id);
    Optional<Trainee> findById(UUID id);
    List<Trainee> findAll();
}
