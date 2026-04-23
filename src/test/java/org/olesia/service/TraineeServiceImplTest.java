package org.olesia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.TraineeDao;
import org.olesia.model.Trainee;
import org.olesia.service.impl.TraineeServiceImpl;
import org.olesia.util.UserCredentialsGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceImplTest {

    private TraineeDao traineeDao;
    private UserCredentialsGenerator credentialsGenerator;
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        traineeDao = mock(TraineeDao.class);
        credentialsGenerator = mock(UserCredentialsGenerator.class);

        traineeService = new TraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setCredentialsGenerator(credentialsGenerator);
    }

    @Test
    void create_ShouldCreateTrainee_WhenTraineeIsValid() {
        Trainee trainee = createTraineeWithoutId();

        when(credentialsGenerator.generateUsername(trainee)).thenReturn("John.Smith");
        when(credentialsGenerator.generatePassword()).thenReturn("abc1234567");
        when(traineeDao.save(trainee)).thenReturn(trainee);

        Trainee result = traineeService.create(trainee);

        assertNotNull(result.getId());
        assertEquals("John.Smith", result.getUsername());
        assertEquals("abc1234567", result.getPassword());
        assertTrue(result.isActive());

        verify(credentialsGenerator).generateUsername(trainee);
        verify(credentialsGenerator).generatePassword();
        verify(traineeDao).save(trainee);
    }

    @Test
    void create_ShouldNotOverwriteId_WhenIdAlreadyExists() {
        UUID id = UUID.randomUUID();
        Trainee trainee = createTraineeWithoutId();
        trainee.setId(id);

        when(credentialsGenerator.generateUsername(trainee)).thenReturn("John.Smith");
        when(credentialsGenerator.generatePassword()).thenReturn("abc1234567");
        when(traineeDao.save(trainee)).thenReturn(trainee);

        Trainee result = traineeService.create(trainee);

        assertEquals(id, result.getId());
        verify(traineeDao).save(trainee);
    }

    @Test
    void create_ShouldThrowException_WhenTraineeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.create(null));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenFirstNameIsNull() {
        Trainee trainee = createTraineeWithoutId();
        trainee.setFirstName(null);

        assertThrows(IllegalArgumentException.class, () -> traineeService.create(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenFirstNameIsBlank() {
        Trainee trainee = createTraineeWithoutId();
        trainee.setFirstName("   ");

        assertThrows(IllegalArgumentException.class, () -> traineeService.create(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenLastNameIsNull() {
        Trainee trainee = createTraineeWithoutId();
        trainee.setLastName(null);

        assertThrows(IllegalArgumentException.class, () -> traineeService.create(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenLastNameIsBlank() {
        Trainee trainee = createTraineeWithoutId();
        trainee.setLastName("   ");

        assertThrows(IllegalArgumentException.class, () -> traineeService.create(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldUpdateTrainee_WhenTraineeIsValid() {
        Trainee trainee = createTraineeWithId();

        when(traineeDao.update(trainee)).thenReturn(trainee);

        Trainee result = traineeService.update(trainee);

        assertEquals(trainee, result);
        verify(traineeDao).update(trainee);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenTraineeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.update(null));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenIdIsNull() {
        Trainee trainee = createTraineeWithoutId();

        assertThrows(IllegalArgumentException.class, () -> traineeService.update(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenFirstNameIsBlank() {
        Trainee trainee = createTraineeWithId();
        trainee.setFirstName("   ");

        assertThrows(IllegalArgumentException.class, () -> traineeService.update(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenLastNameIsBlank() {
        Trainee trainee = createTraineeWithId();
        trainee.setLastName("   ");

        assertThrows(IllegalArgumentException.class, () -> traineeService.update(trainee));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void deleteById_ShouldDeleteTrainee_WhenIdIsValid() {
        UUID id = UUID.randomUUID();

        traineeService.deleteById(id);

        verify(traineeDao).deleteById(id);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void deleteById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.deleteById(null));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findById_ShouldReturnTrainee_WhenIdIsValid() {
        UUID id = UUID.randomUUID();
        Trainee trainee = createTraineeWithId();

        when(traineeDao.findById(id)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());

        verify(traineeDao).findById(id);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> traineeService.findById(null));

        verifyNoInteractions(traineeDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findAll_ShouldReturnAllTrainees() {
        Trainee trainee1 = createTraineeWithId();
        Trainee trainee2 = createTraineeWithId();

        when(traineeDao.findAll()).thenReturn(Arrays.asList(trainee1, trainee2));

        List<Trainee> result = traineeService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(trainee1));
        assertTrue(result.contains(trainee2));

        verify(traineeDao).findAll();
        verifyNoInteractions(credentialsGenerator);
    }

    private Trainee createTraineeWithoutId() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Smith");
        trainee.setAddress("Kyiv");
        return trainee;
    }

    private Trainee createTraineeWithId() {
        Trainee trainee = createTraineeWithoutId();
        trainee.setId(UUID.randomUUID());
        return trainee;
    }
}