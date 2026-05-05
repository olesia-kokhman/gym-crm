package org.olesia.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.impl.TrainerDaoMapImpl;
import org.olesia.model.Trainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainerDaoMapImplTest {

    private TrainerDaoMapImpl trainerDao;

    @BeforeEach
    void setUp() {
        trainerDao = new TrainerDaoMapImpl();

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainer", new HashMap<>());

        trainerDao.setStorage(storage);
    }

    @Test
    void save_ShouldSaveTrainer_WhenTrainerIsValid() {
        Trainer trainer = createTrainer();

        Trainer result = trainerDao.save(trainer);

        assertEquals(trainer, result);
        assertEquals(Optional.of(trainer), trainerDao.findById(trainer.getId()));
    }

    @Test
    void save_ShouldThrowException_WhenTrainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainerDao.save(null));
    }

    @Test
    void save_ShouldThrowException_WhenTrainerIdIsNull() {
        Trainer trainer = new Trainer();

        assertThrows(IllegalArgumentException.class, () -> trainerDao.save(trainer));
    }

    @Test
    void save_ShouldThrowException_WhenTrainerAlreadyExists() {
        Trainer trainer = createTrainer();

        trainerDao.save(trainer);

        assertThrows(IllegalArgumentException.class, () -> trainerDao.save(trainer));
    }

    @Test
    void update_ShouldUpdateTrainer_WhenTrainerExists() {
        Trainer trainer = createTrainer();
        trainerDao.save(trainer);

        trainer.setFirstName("UpdatedName");

        Trainer result = trainerDao.update(trainer);

        assertEquals("UpdatedName", result.getFirstName());
        assertEquals("UpdatedName", trainerDao.findById(trainer.getId()).get().getFirstName());
    }

    @Test
    void update_ShouldThrowException_WhenTrainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainerDao.update(null));
    }

    @Test
    void update_ShouldThrowException_WhenTrainerIdIsNull() {
        Trainer trainer = new Trainer();

        assertThrows(IllegalArgumentException.class, () -> trainerDao.update(trainer));
    }

    @Test
    void update_ShouldThrowException_WhenTrainerDoesNotExist() {
        Trainer trainer = createTrainer();

        assertThrows(IllegalArgumentException.class, () -> trainerDao.update(trainer));
    }

    @Test
    void findById_ShouldReturnTrainer_WhenTrainerExists() {
        Trainer trainer = createTrainer();
        trainerDao.save(trainer);

        Optional<Trainer> result = trainerDao.findById(trainer.getId());

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTrainerDoesNotExist() {
        Optional<Trainer> result = trainerDao.findById(UUID.randomUUID());

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllTrainers() {
        Trainer trainer1 = createTrainer();
        Trainer trainer2 = createTrainer();

        trainerDao.save(trainer1);
        trainerDao.save(trainer2);

        List<Trainer> result = trainerDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(trainer1));
        assertTrue(result.contains(trainer2));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenStorageIsEmpty() {
        List<Trainer> result = trainerDao.findAll();

        assertTrue(result.isEmpty());
    }

    private Trainer createTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId(UUID.randomUUID());
        trainer.setFirstName("John");
        trainer.setLastName("Smith");
        return trainer;
    }
}