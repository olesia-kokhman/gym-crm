package org.olesia.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.impl.TrainingDaoMapImpl;
import org.olesia.model.Training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainingDaoMapImplTest {

    private TrainingDaoMapImpl trainingDao;

    @BeforeEach
    void setUp() {
        trainingDao = new TrainingDaoMapImpl();

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("training", new HashMap<>());

        trainingDao.setStorage(storage);
    }

    @Test
    void save_ShouldSaveTraining_WhenTrainingIsValid() {
        Training training = createTraining();

        Training result = trainingDao.save(training);

        assertEquals(training, result);
        assertEquals(Optional.of(training), trainingDao.findById(training.getId()));
    }

    @Test
    void save_ShouldThrowException_WhenTrainingIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainingDao.save(null));
    }

    @Test
    void save_ShouldThrowException_WhenTrainingIdIsNull() {
        Training training = createTraining();
        training.setId(null);

        assertThrows(IllegalArgumentException.class, () -> trainingDao.save(training));
    }

    @Test
    void save_ShouldThrowException_WhenTraineeIdIsNull() {
        Training training = createTraining();
        training.setTraineeId(null);

        assertThrows(IllegalArgumentException.class, () -> trainingDao.save(training));
    }

    @Test
    void save_ShouldThrowException_WhenTrainerIdIsNull() {
        Training training = createTraining();
        training.setTrainerId(null);

        assertThrows(IllegalArgumentException.class, () -> trainingDao.save(training));
    }

    @Test
    void save_ShouldThrowException_WhenTrainingAlreadyExists() {
        Training training = createTraining();

        trainingDao.save(training);

        assertThrows(IllegalArgumentException.class, () -> trainingDao.save(training));
    }

    @Test
    void findById_ShouldReturnTraining_WhenTrainingExists() {
        Training training = createTraining();
        trainingDao.save(training);

        Optional<Training> result = trainingDao.findById(training.getId());

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTrainingDoesNotExist() {
        Optional<Training> result = trainingDao.findById(UUID.randomUUID());

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllTrainings() {
        Training training1 = createTraining();
        Training training2 = createTraining();

        trainingDao.save(training1);
        trainingDao.save(training2);

        List<Training> result = trainingDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(training1));
        assertTrue(result.contains(training2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenStorageIsEmpty() {
        List<Training> result = trainingDao.findAll();

        assertTrue(result.isEmpty());
    }

    private Training createTraining() {
        Training training = new Training();
        training.setId(UUID.randomUUID());
        training.setTraineeId(UUID.randomUUID());
        training.setTrainerId(UUID.randomUUID());
        training.setName("Morning Training");
        return training;
    }
}