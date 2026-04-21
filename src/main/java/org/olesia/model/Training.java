package org.olesia.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Training {

    private UUID id;
    private UUID traineeId;
    private UUID trainerId;
    private String name;
    private TrainingType type;
    private LocalDateTime dateTime;
    private Duration duration;
}
