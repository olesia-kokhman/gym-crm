package org.olesia.dao.impl;

import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TraineeDaoMapImpl implements TraineeDao {
    @Override
    public Trainee save(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public Optional<Trainee> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Trainee> findAll() {
        return List.of();
    }
}
