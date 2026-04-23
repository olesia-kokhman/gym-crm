package org.olesia.dao.impl;

import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainingDaoMapImpl implements TrainingDao {

    private Map<UUID, Training> trainingStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.trainingStorage = (Map<UUID, Training>) (Map<?, ?>) storage.get("training");
    }

    private void validate(Training training) {
        if (training == null) {
            throw new IllegalArgumentException("Training must not be null");
        }

        if (training.getId() == null) {
            throw new IllegalArgumentException("Training id must not be null");
        }

        if (training.getTraineeId() == null) {
            throw new IllegalArgumentException("Training traineeId must not be null");
        }

        if (training.getTrainerId() == null) {
            throw new IllegalArgumentException("Training trainerId must not be null");
        }
    }

    @Override
    public Training save(Training training) {
        validate(training);

        if (trainingStorage.containsKey(training.getId())) {
            throw new IllegalArgumentException("Training with id " + training.getId() + " already exists");
        }

        trainingStorage.put(training.getId(), training);
        return training;
    }

    @Override
    public Optional<Training> findById(UUID id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainingStorage.values());
    }
}