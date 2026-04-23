package org.olesia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.dao.TrainingDao;
import org.olesia.model.Training;
import org.olesia.model.TrainingType;
import org.olesia.service.impl.TrainingServiceImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    private TrainingDao trainingDao;
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        trainingDao = mock(TrainingDao.class);

        trainingService = new TrainingServiceImpl();
        trainingService.setTrainingDao(trainingDao);
    }

    @Test
    void create_ShouldCreateTraining_WhenTrainingIsValid() {
        Training training = createTrainingWithoutId();

        when(trainingDao.save(training)).thenReturn(training);

        Training result = trainingService.create(training);

        assertNotNull(result.getId());
        assertEquals(training, result);

        verify(trainingDao).save(training);
    }

    @Test
    void create_ShouldNotOverwriteId_WhenIdAlreadyExists() {
        UUID id = UUID.randomUUID();
        Training training = createTrainingWithoutId();
        training.setId(id);

        when(trainingDao.save(training)).thenReturn(training);

        Training result = trainingService.create(training);

        assertEquals(id, result.getId());
        verify(trainingDao).save(training);
    }

    @Test
    void create_ShouldThrowException_WhenTrainingIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainingService.create(null));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenTraineeIdIsNull() {
        Training training = createTrainingWithoutId();
        training.setTraineeId(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenTrainerIdIsNull() {
        Training training = createTrainingWithoutId();
        training.setTrainerId(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenTypeIsNull() {
        Training training = createTrainingWithoutId();
        training.setType(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenNameIsNull() {
        Training training = createTrainingWithoutId();
        training.setName(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenNameIsBlank() {
        Training training = createTrainingWithoutId();
        training.setName("   ");

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenDateTimeIsNull() {
        Training training = createTrainingWithoutId();
        training.setDateTime(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void create_ShouldThrowException_WhenDurationIsNull() {
        Training training = createTrainingWithoutId();
        training.setDuration(null);

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(training));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void findById_ShouldReturnTraining_WhenIdIsValid() {
        UUID id = UUID.randomUUID();
        Training training = createTrainingWithId();

        when(trainingDao.findById(id)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());

        verify(trainingDao).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainingService.findById(null));

        verifyNoInteractions(trainingDao);
    }

    @Test
    void findAll_ShouldReturnAllTrainings() {
        Training training1 = createTrainingWithId();
        Training training2 = createTrainingWithId();

        when(trainingDao.findAll()).thenReturn(Arrays.asList(training1, training2));

        List<Training> result = trainingService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(training1));
        assertTrue(result.contains(training2));

        verify(trainingDao).findAll();
    }

    private Training createTrainingWithoutId() {
        Training training = new Training();
        training.setTraineeId(UUID.randomUUID());
        training.setTrainerId(UUID.randomUUID());
        training.setName("Morning Training");
        training.setType(createTrainingType());
        training.setDateTime(LocalDateTime.now());
        training.setDuration(Duration.ofMinutes(60));
        return training;
    }

    private Training createTrainingWithId() {
        Training training = createTrainingWithoutId();
        training.setId(UUID.randomUUID());
        return training;
    }

    private TrainingType createTrainingType() {
        TrainingType type = new TrainingType();
        type.setTrainingTypeName("Fitness");
        return type;
    }
}