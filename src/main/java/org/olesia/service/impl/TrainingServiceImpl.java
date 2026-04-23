package org.olesia.service.impl;

import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.olesia.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    private void validate(Training training) {
        if (training == null) {
            throw new IllegalArgumentException("Training must not be null");
        }
        if (training.getTraineeId() == null) {
            throw new IllegalArgumentException("Training traineeId must not be null");
        }
        if (training.getTrainerId() == null) {
            throw new IllegalArgumentException("Training trainerId must not be null");
        }
        if (training.getType() == null) {
            throw new IllegalArgumentException("Training type must not be null");
        }
        if (training.getName() == null || training.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Training name must not be blank");
        }
        if (training.getDateTime() == null) {
            throw new IllegalArgumentException("Training dateTime must not be null");
        }
        if (training.getDuration() == null) {
            throw new IllegalArgumentException("Training duration must not be null");
        }
    }

    @Override
    public Training create(Training training) {
        validate(training);

        if (training.getId() == null) {
            training.setId(UUID.randomUUID());
        }

        return trainingDao.save(training);
    }

    @Override
    public Optional<Training> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Training id must not be null");
        }
        return trainingDao.findById(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingDao.findAll();
    }

}