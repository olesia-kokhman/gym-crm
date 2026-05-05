package org.olesia.dao;

import org.olesia.model.Trainer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainerDao {

    Trainer save(Trainer trainer);
    Trainer update(Trainer trainer);

    Optional<Trainer> findById(UUID id);
    List<Trainer> findAll();
}
