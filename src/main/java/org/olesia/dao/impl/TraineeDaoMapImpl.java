package org.olesia.dao.impl;

import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TraineeDaoMapImpl implements TraineeDao {

    private Map<UUID, Trainee> traineeStorage;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setStorage(Map<String, Map<UUID, Object>> storage) {
        this.traineeStorage = (Map<UUID, Trainee>) (Map<?, ?>) storage.get("trainee");
    }

    private void validate(Trainee trainee) {
        if (trainee == null) {
            throw new IllegalArgumentException("Trainee must not be null");
        }

        if (trainee.getId() == null) {
            throw new IllegalArgumentException("Trainee userId must not be null");
        }
    }

    @Override
    public Trainee save(Trainee trainee) {
        validate(trainee);

        UUID traineeId = trainee.getId();
        if (traineeStorage.containsKey(traineeId)) {
            throw new IllegalArgumentException("Trainee with id " + traineeId + " already exists");
        }

        traineeStorage.put(traineeId, trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        validate(trainee);

        UUID traineeId = trainee.getId();
        if (!traineeStorage.containsKey(traineeId)) {
            throw new IllegalArgumentException("Trainee with id " + traineeId + " does not exist");
        }

        traineeStorage.put(traineeId, trainee);
        return trainee;
    }

    @Override
    public void deleteById(UUID id) {
        if (!traineeStorage.containsKey(id)) {
            throw new IllegalArgumentException("Trainee with id " + id + " does not exist");
        }

        traineeStorage.remove(id);
    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(traineeStorage.values());
    }
}
