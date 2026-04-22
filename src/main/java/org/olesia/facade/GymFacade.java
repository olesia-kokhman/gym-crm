package org.olesia.facade;

import org.olesia.model.Trainee;
import org.olesia.model.Trainer;
import org.olesia.model.Training;
import org.olesia.service.TraineeService;
import org.olesia.service.TrainerService;
import org.olesia.service.TrainingService;
import org.springframework.stereotype.Component;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService) {

        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;

    }

    public Trainee createTrainee() {
        return null;
    }

    public Trainer createTrainer() {
        return null;
    }

    public Training createTraining() {
        return null;
    }
}
