package org.olesia.dao.impl;

import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrainerDaoMapImpl implements TrainerDao {
    @Override
    public Trainer save(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Trainer> findAll() {
        return List.of();
    }
}
