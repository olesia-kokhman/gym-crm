package org.olesia.config;

import org.olesia.model.Trainee;
import org.olesia.model.Trainer;
import org.olesia.model.Training;
import org.olesia.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class StorageBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(StorageBeanPostProcessor.class);

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!"storage".equals(beanName)) {
            return bean;
        }

        Map<String, Map<UUID, Object>> storage = (Map<String, Map<UUID, Object>>) bean;
        loadStorage(storage);

        return bean;
    }

    private void loadStorage(Map<String, Map<UUID, Object>> storage) {
        log.info("Initializing storage from file");

        String path = environment.getProperty("storage.init.file");

        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Property storage.init.file is not configured");
        }

        Resource initFile = new ClassPathResource(path.replace("classpath:", ""));

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(initFile.getInputStream(), StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(";");

                switch (parts[0]) {
                    case "TRAINER":
                        loadTrainer(parts, storage);
                        break;
                    case "TRAINEE":
                        loadTrainee(parts, storage);
                        break;
                    case "TRAINING":
                        loadTraining(parts, storage);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown entity type: " + parts[0]);
                }
            }

            log.info("Storage initialized successfully");

        } catch (Exception e) {
            log.error("Failed to initialize storage from file", e);
            throw new RuntimeException("Failed to initialize storage from file", e);
        }
    }

    private void loadTrainer(String[] parts, Map<String, Map<UUID, Object>> storage) {
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid TRAINER line");
        }

        Trainer trainer = new Trainer();
        trainer.setId(UUID.fromString(parts[1]));
        trainer.setFirstName(parts[2]);
        trainer.setLastName(parts[3]);
        trainer.setUsername(parts[4]);
        trainer.setPassword(parts[5]);
        trainer.setActive(Boolean.parseBoolean(parts[6]));

        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName(parts[7]);
        trainer.setSpecialization(specialization);

        storage.get("trainer").put(trainer.getId(), trainer);

        log.debug("Trainer loaded with id: {}", trainer.getId());
    }

    private void loadTrainee(String[] parts, Map<String, Map<UUID, Object>> storage) {
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid TRAINEE line");
        }

        Trainee trainee = new Trainee();
        trainee.setId(UUID.fromString(parts[1]));
        trainee.setFirstName(parts[2]);
        trainee.setLastName(parts[3]);
        trainee.setUsername(parts[4]);
        trainee.setPassword(parts[5]);
        trainee.setActive(Boolean.parseBoolean(parts[6]));
        trainee.setDateOfBirth(LocalDate.parse(parts[7]));
        trainee.setAddress(parts[8]);

        storage.get("trainee").put(trainee.getId(), trainee);

        log.debug("Trainee loaded with id: {}", trainee.getId());
    }

    private void loadTraining(String[] parts, Map<String, Map<UUID, Object>> storage) {
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid TRAINING line");
        }

        Training training = new Training();
        training.setId(UUID.fromString(parts[1]));
        training.setTraineeId(UUID.fromString(parts[2]));
        training.setTrainerId(UUID.fromString(parts[3]));
        training.setName(parts[4]);

        TrainingType type = new TrainingType();
        type.setTrainingTypeName(parts[5]);
        training.setType(type);

        training.setDateTime(LocalDateTime.parse(parts[6]));
        training.setDuration(Duration.ofMinutes(Long.parseLong(parts[7])));

        storage.get("training").put(training.getId(), training);

        log.debug("Training loaded with id: {}", training.getId());
    }
}