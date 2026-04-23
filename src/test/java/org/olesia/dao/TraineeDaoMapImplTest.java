package org.olesia.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.impl.TraineeDaoMapImpl;
import org.olesia.model.Trainee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TraineeDaoMapImplTest {

    private TraineeDaoMapImpl traineeDao;

    @BeforeEach
    void setUp() {
        traineeDao = new TraineeDaoMapImpl();

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());

        traineeDao.setStorage(storage);
    }

    @Test
    void save_ShouldSaveTrainee_WhenTraineeIsValid() {
        Trainee trainee = createTrainee();

        Trainee result = traineeDao.save(trainee);

        assertEquals(trainee, result);
        assertEquals(Optional.of(trainee), traineeDao.findById(trainee.getId()));
    }

    @Test
    void save_ShouldThrowException_WhenTraineeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeDao.save(null));
    }

    @Test
    void save_ShouldThrowException_WhenTraineeIdIsNull() {
        Trainee trainee = new Trainee();

        assertThrows(IllegalArgumentException.class, () -> traineeDao.save(trainee));
    }

    @Test
    void save_ShouldThrowException_WhenTraineeAlreadyExists() {
        Trainee trainee = createTrainee();

        traineeDao.save(trainee);

        assertThrows(IllegalArgumentException.class, () -> traineeDao.save(trainee));
    }

    @Test
    void update_ShouldUpdateTrainee_WhenTraineeExists() {
        Trainee trainee = createTrainee();
        traineeDao.save(trainee);

        trainee.setAddress("Updated address");

        Trainee result = traineeDao.update(trainee);

        assertEquals("Updated address", result.getAddress());
        assertEquals("Updated address", traineeDao.findById(trainee.getId()).get().getAddress());
    }

    @Test
    void update_ShouldThrowException_WhenTraineeDoesNotExist() {
        Trainee trainee = createTrainee();

        assertThrows(IllegalArgumentException.class, () -> traineeDao.update(trainee));
    }

    @Test
    void deleteById_ShouldDeleteTrainee_WhenTraineeExists() {
        Trainee trainee = createTrainee();
        traineeDao.save(trainee);

        traineeDao.deleteById(trainee.getId());

        assertTrue(!traineeDao.findById(trainee.getId()).isPresent());
    }

    @Test
    void deleteById_ShouldThrowException_WhenTraineeDoesNotExist() {
        UUID id = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> traineeDao.deleteById(id));
    }

    @Test
    void findById_ShouldReturnTrainee_WhenTraineeExists() {
        Trainee trainee = createTrainee();
        traineeDao.save(trainee);

        Optional<Trainee> result = traineeDao.findById(trainee.getId());

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenTraineeDoesNotExist() {
        Optional<Trainee> result = traineeDao.findById(UUID.randomUUID());

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllTrainees() {
        Trainee trainee1 = createTrainee();
        Trainee trainee2 = createTrainee();

        traineeDao.save(trainee1);
        traineeDao.save(trainee2);

        List<Trainee> result = traineeDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(trainee1));
        assertTrue(result.contains(trainee2));
    }

    private Trainee createTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(UUID.randomUUID());
        trainee.setFirstName("John");
        trainee.setLastName("Smith");
        trainee.setAddress("Kyiv");
        return trainee;
    }

    @Test
    void update_ShouldThrowException_WhenTraineeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeDao.update(null));
    }

    @Test
    void update_ShouldThrowException_WhenTraineeIdIsNull() {
        Trainee trainee = new Trainee();

        assertThrows(IllegalArgumentException.class, () -> traineeDao.update(trainee));
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenStorageIsEmpty() {
        List<Trainee> result = traineeDao.findAll();

        assertTrue(result.isEmpty());
    }


}