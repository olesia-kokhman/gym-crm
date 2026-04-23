package org.olesia.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olesia.model.Trainee;
import org.olesia.model.Trainer;
import org.olesia.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserCredentialsGeneratorTest {

    private UserCredentialsGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new UserCredentialsGenerator();

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());

        generator.setStorage(storage);
    }

    @Test
    void generateUsername_ShouldReturnFirstNameDotLastName_WhenUserIsUnique() {
        User user = createUser("John", "Smith");

        String result = generator.generateUsername(user);

        assertEquals("John.Smith", result);
    }

    @Test
    void generateUsername_ShouldAddSuffix_WhenSameTraineeExists() {
        Trainee existingTrainee = createTrainee("John", "Smith");

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.get("trainee").put(existingTrainee.getId(), existingTrainee);

        generator.setStorage(storage);

        User user = createUser("John", "Smith");

        String result = generator.generateUsername(user);

        assertEquals("John.Smith2", result);
    }

    @Test
    void generateUsername_ShouldAddSuffix_WhenSameTrainerExists() {
        Trainer existingTrainer = createTrainer("John", "Smith");

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.get("trainer").put(existingTrainer.getId(), existingTrainer);

        generator.setStorage(storage);

        User user = createUser("John", "Smith");

        String result = generator.generateUsername(user);

        assertEquals("John.Smith2", result);
    }

    @Test
    void generateUsername_ShouldAddCorrectSuffix_WhenSameTraineeAndTrainerExist() {
        Trainee existingTrainee = createTrainee("John", "Smith");
        Trainer existingTrainer = createTrainer("John", "Smith");

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.get("trainee").put(existingTrainee.getId(), existingTrainee);
        storage.get("trainer").put(existingTrainer.getId(), existingTrainer);

        generator.setStorage(storage);

        User user = createUser("John", "Smith");

        String result = generator.generateUsername(user);

        assertEquals("John.Smith3", result);
    }

    @Test
    void generateUsername_ShouldIgnoreUsersWithDifferentNames() {
        Trainee existingTrainee = createTrainee("Alice", "Brown");

        Map<String, Map<UUID, Object>> storage = new HashMap<>();
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.get("trainee").put(existingTrainee.getId(), existingTrainee);

        generator.setStorage(storage);

        User user = createUser("John", "Smith");

        String result = generator.generateUsername(user);

        assertEquals("John.Smith", result);
    }

    @Test
    void generateUsername_ShouldThrowException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> generator.generateUsername(null));
    }

    @Test
    void generateUsername_ShouldThrowException_WhenFirstNameIsNull() {
        User user = createUser(null, "Smith");

        assertThrows(IllegalArgumentException.class, () -> generator.generateUsername(user));
    }

    @Test
    void generateUsername_ShouldThrowException_WhenLastNameIsNull() {
        User user = createUser("John", null);

        assertThrows(IllegalArgumentException.class, () -> generator.generateUsername(user));
    }

    @Test
    void generatePassword_ShouldReturnPasswordWithLength10() {
        String password = generator.generatePassword();

        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void generatePassword_ShouldReturnOnlyLettersAndDigits() {
        String password = generator.generatePassword();

        assertTrue(password.matches("[A-Za-z0-9]{10}"));
    }

    private User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    private Trainee createTrainee(String firstName, String lastName) {
        Trainee trainee = new Trainee();
        trainee.setId(UUID.randomUUID());
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        return trainee;
    }

    private Trainer createTrainer(String firstName, String lastName) {
        Trainer trainer = new Trainer();
        trainer.setId(UUID.randomUUID());
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        return trainer;
    }
}