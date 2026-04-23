package org.olesia.service.impl;

import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.olesia.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    private void validate(Training training) {
        if (training == null) {
            log.error("Validation failed: training is null");
            throw new IllegalArgumentException("Training must not be null");
        }
        if (training.getTraineeId() == null) {
            log.error("Validation failed: traineeId is null");
            throw new IllegalArgumentException("Training traineeId must not be null");
        }
        if (training.getTrainerId() == null) {
            log.error("Validation failed: trainerId is null");
            throw new IllegalArgumentException("Training trainerId must not be null");
        }
        if (training.getType() == null) {
            log.error("Validation failed: training type is null");
            throw new IllegalArgumentException("Training type must not be null");
        }
        if (training.getName() == null || training.getName().trim().isEmpty()) {
            log.error("Validation failed: training name is blank");
            throw new IllegalArgumentException("Training name must not be blank");
        }
        if (training.getDateTime() == null) {
            log.error("Validation failed: dateTime is null");
            throw new IllegalArgumentException("Training dateTime must not be null");
        }
        if (training.getDuration() == null) {
            log.error("Validation failed: duration is null");
            throw new IllegalArgumentException("Training duration must not be null");
        }
    }

    @Override
    public Training create(Training training) {
        log.info("Creating training");

        validate(training);

        if (training.getId() == null) {
            training.setId(UUID.randomUUID());
            log.debug("Generated training id: {}", training.getId());
        }

        Training saved = trainingDao.save(training);

        log.info("Training created with id: {}", saved.getId());

        return saved;
    }

    @Override
    public Optional<Training> findById(UUID id) {
        log.debug("Finding training by id: {}", id);

        if (id == null) {
            log.error("Find failed: training id is null");
            throw new IllegalArgumentException("Training id must not be null");
        }

        Optional<Training> result = trainingDao.findById(id);

        log.debug("Training found: {}", result.isPresent());

        return result;
    }

    @Override
    public List<Training> findAll() {
        log.debug("Finding all trainings");

        List<Training> result = trainingDao.findAll();

        log.debug("Found {} trainings", result.size());

        return result;
    }
}