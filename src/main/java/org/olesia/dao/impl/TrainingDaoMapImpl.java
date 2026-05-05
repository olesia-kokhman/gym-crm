package org.olesia.dao.impl;

import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainingDaoMapImpl implements TrainingDao {

    private static final Logger log = LoggerFactory.getLogger(TrainingDaoMapImpl.class);

    private Map<UUID, Training> trainingStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.trainingStorage = (Map<UUID, Training>) (Map<?, ?>) storage.get("training");
        log.debug("Training storage has been initialized");
    }

    private void validate(Training training) {
        if (training == null) {
            log.error("Training validation failed: training is null");
            throw new IllegalArgumentException("Training must not be null");
        }

        if (training.getId() == null) {
            log.error("Training validation failed: id is null");
            throw new IllegalArgumentException("Training id must not be null");
        }

        if (training.getTraineeId() == null) {
            log.error("Training validation failed: traineeId is null");
            throw new IllegalArgumentException("Training traineeId must not be null");
        }

        if (training.getTrainerId() == null) {
            log.error("Training validation failed: trainerId is null");
            throw new IllegalArgumentException("Training trainerId must not be null");
        }
    }

    @Override
    public Training save(Training training) {
        validate(training);

        UUID id = training.getId();
        log.debug("Saving training with id: {}", id);

        if (trainingStorage.containsKey(id)) {
            log.warn("Cannot save training. Training with id {} already exists", id);
            throw new IllegalArgumentException("Training with id " + id + " already exists");
        }

        trainingStorage.put(id, training);
        log.info("Training saved with id: {}", id);

        return training;
    }

    @Override
    public Optional<Training> findById(UUID id) {
        log.debug("Finding training by id: {}", id);

        Optional<Training> result = Optional.ofNullable(trainingStorage.get(id));

        log.debug("Training found by id {}: {}", id, result.isPresent());

        return result;
    }

    @Override
    public List<Training> findAll() {
        log.debug("Finding all trainings");

        List<Training> trainings = new ArrayList<>(trainingStorage.values());

        log.debug("Found {} trainings", trainings.size());

        return trainings;
    }
}