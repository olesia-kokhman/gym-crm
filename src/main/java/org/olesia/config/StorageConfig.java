package org.olesia.config;

import org.olesia.model.Trainee;
import org.olesia.model.Trainer;
import org.olesia.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@ComponentScan("org.olesia")
public class StorageConfig {

    @Bean
    public Map<String, Map<UUID, Object>> storage() {
        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.put("training", new HashMap<>());
        return storage;
    }
}
