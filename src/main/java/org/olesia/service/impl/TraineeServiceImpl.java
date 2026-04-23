package org.olesia.service.impl;

import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.olesia.service.TraineeService;
import org.olesia.util.UserCredentialsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger log = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private TraineeDao traineeDao;
    private UserCredentialsGenerator credentialsGenerator;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setCredentialsGenerator(UserCredentialsGenerator credentialsGenerator) {
        this.credentialsGenerator = credentialsGenerator;
    }

    private void validate(Trainee trainee) {
        if (trainee == null) {
            log.error("Validation failed: trainee is null");
            throw new IllegalArgumentException("Trainee must not be null");
        }
        if (isBlank(trainee.getFirstName())) {
            log.error("Validation failed: firstName is blank");
            throw new IllegalArgumentException("Trainee firstName must not be blank");
        }
        if (isBlank(trainee.getLastName())) {
            log.error("Validation failed: lastName is blank");
            throw new IllegalArgumentException("Trainee lastName must not be blank");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public Trainee create(Trainee trainee) {
        log.info("Creating trainee");

        validate(trainee);

        if (trainee.getId() == null) {
            trainee.setId(UUID.randomUUID());
            log.debug("Generated trainee id: {}", trainee.getId());
        }

        trainee.setUsername(credentialsGenerator.generateUsername(trainee));
        trainee.setPassword(credentialsGenerator.generatePassword());
        trainee.setActive(true);

        log.debug("Generated username: {}", trainee.getUsername());

        Trainee saved = traineeDao.save(trainee);

        log.info("Trainee created with id: {}", saved.getId());

        return saved;
    }

    @Override
    public Trainee update(Trainee trainee) {
        log.info("Updating trainee");

        validate(trainee);

        if (trainee.getId() == null) {
            log.error("Update failed: id is null");
            throw new IllegalArgumentException("Trainee id must not be null");
        }

        Trainee updated = traineeDao.update(trainee);

        log.info("Trainee updated with id: {}", updated.getId());

        return updated;
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Deleting trainee with id: {}", id);

        if (id == null) {
            log.error("Delete failed: id is null");
            throw new IllegalArgumentException("Trainee id must not be null");
        }

        traineeDao.deleteById(id);

        log.info("Trainee deleted with id: {}", id);
    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        log.debug("Finding trainee by id: {}", id);

        if (id == null) {
            log.error("Find failed: id is null");
            throw new IllegalArgumentException("Trainee id must not be null");
        }

        Optional<Trainee> result = traineeDao.findById(id);

        log.debug("Trainee found: {}", result.isPresent());

        return result;
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Finding all trainees");

        List<Trainee> result = traineeDao.findAll();

        log.debug("Found {} trainees", result.size());

        return result;
    }
}