package org.olesia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.TrainerDao;
import org.olesia.model.Trainer;
import org.olesia.model.TrainingType;
import org.olesia.service.impl.TrainerServiceImpl;
import org.olesia.util.UserCredentialsGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    private TrainerDao trainerDao;
    private UserCredentialsGenerator credentialsGenerator;
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        trainerDao = mock(TrainerDao.class);
        credentialsGenerator = mock(UserCredentialsGenerator.class);

        trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setCredentialsGenerator(credentialsGenerator);
    }

    @Test
    void create_ShouldCreateTrainer_WhenTrainerIsValid() {
        Trainer trainer = createTrainerWithoutId();

        when(credentialsGenerator.generateUsername(trainer)).thenReturn("John.Smith");
        when(credentialsGenerator.generatePassword()).thenReturn("abc1234567");
        when(trainerDao.save(trainer)).thenReturn(trainer);

        Trainer result = trainerService.create(trainer);

        assertNotNull(result.getId());
        assertEquals("John.Smith", result.getUsername());
        assertEquals("abc1234567", result.getPassword());
        assertTrue(result.isActive());

        verify(credentialsGenerator).generateUsername(trainer);
        verify(credentialsGenerator).generatePassword();
        verify(trainerDao).save(trainer);
    }

    @Test
    void create_ShouldNotOverwriteId_WhenIdAlreadyExists() {
        UUID id = UUID.randomUUID();
        Trainer trainer = createTrainerWithoutId();
        trainer.setId(id);

        when(credentialsGenerator.generateUsername(trainer)).thenReturn("John.Smith");
        when(credentialsGenerator.generatePassword()).thenReturn("abc1234567");
        when(trainerDao.save(trainer)).thenReturn(trainer);

        Trainer result = trainerService.create(trainer);

        assertEquals(id, result.getId());
        verify(trainerDao).save(trainer);
    }

    @Test
    void create_ShouldThrowException_WhenTrainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainerService.create(null));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenFirstNameIsNull() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setFirstName(null);

        assertThrows(IllegalArgumentException.class, () -> trainerService.create(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenFirstNameIsBlank() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setFirstName("   ");

        assertThrows(IllegalArgumentException.class, () -> trainerService.create(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenLastNameIsNull() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setLastName(null);

        assertThrows(IllegalArgumentException.class, () -> trainerService.create(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenLastNameIsBlank() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setLastName("   ");

        assertThrows(IllegalArgumentException.class, () -> trainerService.create(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void create_ShouldThrowException_WhenSpecializationIsNull() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setSpecialization(null);

        assertThrows(IllegalArgumentException.class, () -> trainerService.create(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldUpdateTrainer_WhenTrainerIsValid() {
        Trainer trainer = createTrainerWithId();

        when(trainerDao.update(trainer)).thenReturn(trainer);

        Trainer result = trainerService.update(trainer);

        assertEquals(trainer, result);
        verify(trainerDao).update(trainer);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenTrainerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainerService.update(null));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenIdIsNull() {
        Trainer trainer = createTrainerWithoutId();

        assertThrows(IllegalArgumentException.class, () -> trainerService.update(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenFirstNameIsBlank() {
        Trainer trainer = createTrainerWithId();
        trainer.setFirstName("   ");

        assertThrows(IllegalArgumentException.class, () -> trainerService.update(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenLastNameIsBlank() {
        Trainer trainer = createTrainerWithId();
        trainer.setLastName("   ");

        assertThrows(IllegalArgumentException.class, () -> trainerService.update(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void update_ShouldThrowException_WhenSpecializationIsNull() {
        Trainer trainer = createTrainerWithId();
        trainer.setSpecialization(null);

        assertThrows(IllegalArgumentException.class, () -> trainerService.update(trainer));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findById_ShouldReturnTrainer_WhenIdIsValid() {
        UUID id = UUID.randomUUID();
        Trainer trainer = createTrainerWithId();

        when(trainerDao.findById(id)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());

        verify(trainerDao).findById(id);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainerService.findById(null));

        verifyNoInteractions(trainerDao);
        verifyNoInteractions(credentialsGenerator);
    }

    @Test
    void findAll_ShouldReturnAllTrainers() {
        Trainer trainer1 = createTrainerWithId();
        Trainer trainer2 = createTrainerWithId();

        when(trainerDao.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        List<Trainer> result = trainerService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(trainer1));
        assertTrue(result.contains(trainer2));

        verify(trainerDao).findAll();
        verifyNoInteractions(credentialsGenerator);
    }

    private Trainer createTrainerWithoutId() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("John");
        trainer.setLastName("Smith");
        trainer.setSpecialization(createTrainingType());
        return trainer;
    }

    private Trainer createTrainerWithId() {
        Trainer trainer = createTrainerWithoutId();
        trainer.setId(UUID.randomUUID());
        return trainer;
    }

    private TrainingType createTrainingType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Fitness");
        return trainingType;
    }
}