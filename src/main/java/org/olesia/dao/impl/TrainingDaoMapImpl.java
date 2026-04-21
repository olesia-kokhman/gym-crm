package org.olesia.dao.impl;

import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrainingDaoMapImpl implements TrainingDao {
    @Override
    public Training save(Training training) {
        return null;
    }

    @Override
    public Optional<Training> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Training> findAll() {
        return List.of();
    }
}
