package org.olesia.service.impl;

import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.olesia.service.TrainerService;
import org.olesia.util.UserCredentialsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {

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
            throw new IllegalArgumentException("Trainer must not be null");
        }
        if (isBlank(trainer.getFirstName())) {
            throw new IllegalArgumentException("Trainer firstName must not be blank");
        }
        if (isBlank(trainer.getLastName())) {
            throw new IllegalArgumentException("Trainer lastName must not be blank");
        }
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Trainer specialization must not be null");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public Trainer create(Trainer trainer) {
        validate(trainer);

        if (trainer.getId() == null) {
            trainer.setId(UUID.randomUUID());
        }

        trainer.setUsername(credentialsGenerator.generateUsername(trainer));
        trainer.setPassword(credentialsGenerator.generatePassword());
        trainer.setActive(true);

        return trainerDao.save(trainer);
    }

    @Override
    public Trainer update(Trainer trainer) {
        validate(trainer);

        if (trainer.getId() == null) {
            throw new IllegalArgumentException("Trainer id must not be null");
        }
        return trainerDao.update(trainer);
    }

    @Override
    public Optional<Trainer> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Trainer id must not be null");
        }
        return trainerDao.findById(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDao.findAll();
    }

}