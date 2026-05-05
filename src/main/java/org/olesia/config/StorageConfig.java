package org.olesia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@ComponentScan("org.olesia")
@PropertySource("classpath:application.properties")
public class StorageConfig {

    @Bean
    public Map<String, Map<UUID, Object>> storage() {
        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.put("training", new HashMap<>());
        return storage;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
