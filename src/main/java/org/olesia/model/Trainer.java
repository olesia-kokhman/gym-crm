package org.olesia.model;

import java.util.UUID;

public class Trainer extends User {
    private TrainingType specialization;

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

}
