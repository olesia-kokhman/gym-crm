package org.olesia.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Training {

    private UUID id;
    private UUID traineeId;
    private UUID trainerId;
    private String name;
    private TrainingType type;
    private LocalDateTime dateTime;
    private Duration duration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(UUID traineeId) {
        this.traineeId = traineeId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getType() {
        return type;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) && Objects.equals(traineeId, training.traineeId)
                && Objects.equals(trainerId, training.trainerId) && Objects.equals(name, training.name)
                && Objects.equals(type, training.type) && Objects.equals(dateTime, training.dateTime)
                && Objects.equals(duration, training.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, traineeId, trainerId, name, type, dateTime, duration);
    }
}
