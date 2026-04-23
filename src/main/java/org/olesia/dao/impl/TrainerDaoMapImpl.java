package org.olesia.dao.impl;

import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDaoMapImpl implements TrainerDao {

    private static final Logger log = LoggerFactory.getLogger(TrainerDaoMapImpl.class);

    private Map<UUID, Trainer> trainerStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.trainerStorage = (Map<UUID, Trainer>) (Map<?, ?>) storage.get("trainer");
        log.debug("Trainer storage has been initialized");
    }

    private void validate(Trainer trainer) {
        if (trainer == null) {
            log.error("Trainer validation failed: trainer is null");
            throw new IllegalArgumentException("Trainer must not be null");
        }

        if (trainer.getId() == null) {
            log.error("Trainer validation failed: id is null");
            throw new IllegalArgumentException("Trainer id must not be null");
        }
    }

    @Override
    public Trainer save(Trainer trainer) {
        validate(trainer);

        UUID trainerId = trainer.getId();
        log.debug("Saving trainer with id: {}", trainerId);

        if (trainerStorage.containsKey(trainerId)) {
            log.warn("Cannot save trainer. Trainer with id {} already exists", trainerId);
            throw new IllegalArgumentException("Trainer with id " + trainerId + " already exists");
        }

        trainerStorage.put(trainerId, trainer);
        log.info("Trainer saved with id: {}", trainerId);

        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        validate(trainer);

        UUID trainerId = trainer.getId();
        log.debug("Updating trainer with id: {}", trainerId);

        if (!trainerStorage.containsKey(trainerId)) {
            log.warn("Cannot update trainer. Trainer with id {} does not exist", trainerId);
            throw new IllegalArgumentException("Trainer with id " + trainerId + " does not exist");
        }

        trainerStorage.put(trainerId, trainer);
        log.info("Trainer updated with id: {}", trainerId);

        return trainer;
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        log.debug("Finding trainer by id: {}", id);

        Optional<Trainer> result = Optional.ofNullable(trainerStorage.get(id));

        log.debug("Trainer found by id {}: {}", id, result.isPresent());

        return result;
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Finding all trainers");

        List<Trainer> trainers = new ArrayList<>(trainerStorage.values());

        log.debug("Found {} trainers", trainers.size());

        return trainers;
    }
}