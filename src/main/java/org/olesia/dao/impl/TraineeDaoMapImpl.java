package org.olesia.dao.impl;

import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TraineeDaoMapImpl implements TraineeDao {

    private static final Logger log = LoggerFactory.getLogger(TraineeDaoMapImpl.class);

    private Map<UUID, Trainee> traineeStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.traineeStorage = (Map<UUID, Trainee>) (Map<?, ?>) storage.get("trainee");
        log.debug("Trainee storage has been initialized");
    }

    private void validate(Trainee trainee) {
        if (trainee == null) {
            log.error("Trainee validation failed: trainee is null");
            throw new IllegalArgumentException("Trainee must not be null");
        }

        if (trainee.getId() == null) {
            log.error("Trainee validation failed: id is null");
            throw new IllegalArgumentException("Trainee id must not be null");
        }
    }

    @Override
    public Trainee save(Trainee trainee) {
        validate(trainee);

        UUID traineeId = trainee.getId();
        log.debug("Saving trainee with id: {}", traineeId);

        if (traineeStorage.containsKey(traineeId)) {
            log.warn("Cannot save trainee. Trainee with id {} already exists", traineeId);
            throw new IllegalArgumentException("Trainee with id " + traineeId + " already exists");
        }

        traineeStorage.put(traineeId, trainee);
        log.info("Trainee saved with id: {}", traineeId);

        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        validate(trainee);

        UUID traineeId = trainee.getId();
        log.debug("Updating trainee with id: {}", traineeId);

        if (!traineeStorage.containsKey(traineeId)) {
            log.warn("Cannot update trainee. Trainee with id {} does not exist", traineeId);
            throw new IllegalArgumentException("Trainee with id " + traineeId + " does not exist");
        }

        traineeStorage.put(traineeId, trainee);
        log.info("Trainee updated with id: {}", traineeId);

        return trainee;
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting trainee with id: {}", id);

        if (!traineeStorage.containsKey(id)) {
            log.warn("Cannot delete trainee. Trainee with id {} does not exist", id);
            throw new IllegalArgumentException("Trainee with id " + id + " does not exist");
        }

        traineeStorage.remove(id);
        log.info("Trainee deleted with id: {}", id);
    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        log.debug("Finding trainee by id: {}", id);

        Optional<Trainee> result = Optional.ofNullable(traineeStorage.get(id));

        log.debug("Trainee found by id {}: {}", id, result.isPresent());

        return result;
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Finding all trainees");

        List<Trainee> trainees = new ArrayList<>(traineeStorage.values());

        log.debug("Found {} trainees", trainees.size());

        return trainees;
    }
}