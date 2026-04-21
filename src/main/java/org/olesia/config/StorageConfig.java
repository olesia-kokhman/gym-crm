package org.olesia.config;

import org.olesia.model.Trainee;
import org.olesia.model.Trainer;
import org.olesia.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class StorageConfig {

    @Bean
    public Map<UUID, Trainee> traineeStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<UUID, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<UUID, Training> trainingStorage() {
        return new HashMap<>();
    }
}
