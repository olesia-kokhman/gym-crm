package org.olesia.service.impl;

import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.olesia.service.TraineeService;
import org.olesia.util.UserCredentialsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TraineeServiceImpl implements TraineeService {

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
            throw new IllegalArgumentException("Trainee must not be null");
        }
        if (isBlank(trainee.getFirstName())) {
            throw new IllegalArgumentException("Trainee firstName must not be blank");
        }
        if (isBlank(trainee.getLastName())) {
            throw new IllegalArgumentException("Trainee lastName must not be blank");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public Trainee create(Trainee trainee) {
        validate(trainee);

        if (trainee.getId() == null) {
            trainee.setId(UUID.randomUUID());
        }

        trainee.setUsername(credentialsGenerator.generateUsername(trainee));
        trainee.setPassword(credentialsGenerator.generatePassword());
        trainee.setActive(true);

        return traineeDao.save(trainee);
    }

    @Override
    public Trainee update(Trainee trainee) {
        validate(trainee);

        if (trainee.getId() == null) {
            throw new IllegalArgumentException("Trainee id must not be null");
        }

        return traineeDao.update(trainee);
    }

    @Override
    public void deleteById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Trainee id must not be null");
        }
        traineeDao.deleteById(id);
    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Trainee id must not be null");
        }
        return traineeDao.findById(id);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDao.findAll();
    }


}