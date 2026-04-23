package org.olesia.dao.impl;

import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDaoMapImpl implements TrainerDao {

    private Map<UUID, Trainer> trainerStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.trainerStorage = (Map<UUID, Trainer>) (Map<?, ?>) storage.get("trainer");
    }

    private void validate(Trainer trainer) {
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer must not be null");
        }

        if (trainer.getId() == null) {
            throw new IllegalArgumentException("Trainer userId must not be null");
        }
    }

    @Override
    public Trainer save(Trainer trainer) {
        validate(trainer);

        UUID trainerId = trainer.getId();

        if (trainerStorage.containsKey(trainerId)) {
            throw new IllegalArgumentException("Trainer with id " + trainerId + " already exists");
        }

        trainerStorage.put(trainerId, trainer);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        validate(trainer);

        UUID trainerId = trainer.getId();

        if (!trainerStorage.containsKey(trainerId)) {
            throw new IllegalArgumentException("Trainer with id " + trainerId + " does not exist");
        }

        trainerStorage.put(trainerId, trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(trainerStorage.values());
    }
}