package org.olesia.service.impl;

import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.olesia.service.TrainerService;
import org.olesia.util.UserCredentialsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger log = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private TrainerDao trainerDao;
    private UserCredentialsGenerator credentialsGenerator;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setCredentialsGenerator(UserCredentialsGenerator credentialsGenerator) {
        this.credentialsGenerator = credentialsGenerator;
    }

    private void validate(Trainer trainer) {
        if (trainer == null) {
            log.error("Validation failed: trainer is null");
            throw new IllegalArgumentException("Trainer must not be null");
        }
        if (isBlank(trainer.getFirstName())) {
            log.error("Validation failed: trainer firstName is blank");
            throw new IllegalArgumentException("Trainer firstName must not be blank");
        }
        if (isBlank(trainer.getLastName())) {
            log.error("Validation failed: trainer lastName is blank");
            throw new IllegalArgumentException("Trainer lastName must not be blank");
        }
        if (trainer.getSpecialization() == null) {
            log.error("Validation failed: trainer specialization is null");
            throw new IllegalArgumentException("Trainer specialization must not be null");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public Trainer create(Trainer trainer) {
        log.info("Creating trainer");

        validate(trainer);

        if (trainer.getId() == null) {
            trainer.setId(UUID.randomUUID());
            log.debug("Generated trainer id: {}", trainer.getId());
        }

        trainer.setUsername(credentialsGenerator.generateUsername(trainer));
        trainer.setPassword(credentialsGenerator.generatePassword());
        trainer.setActive(true);

        log.debug("Generated username: {}", trainer.getUsername());

        Trainer saved = trainerDao.save(trainer);

        log.info("Trainer created with id: {}", saved.getId());

        return saved;
    }

    @Override
    public Trainer update(Trainer trainer) {
        log.info("Updating trainer");

        validate(trainer);

        if (trainer.getId() == null) {
            log.error("Update failed: trainer id is null");
            throw new IllegalArgumentException("Trainer id must not be null");
        }

        Trainer updated = trainerDao.update(trainer);

        log.info("Trainer updated with id: {}", updated.getId());

        return updated;
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        log.debug("Finding trainer by id: {}", id);

        if (id == null) {
            log.error("Find failed: trainer id is null");
            throw new IllegalArgumentException("Trainer id must not be null");
        }

        Optional<Trainer> result = trainerDao.findById(id);

        log.debug("Trainer found: {}", result.isPresent());

        return result;
    }

    @Override
    public List<Trainer> findAll() {
        log.debug("Finding all trainers");

        List<Trainer> result = trainerDao.findAll();

        log.debug("Found {} trainers", result.size());

        return result;
    }
}