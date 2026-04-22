package org.olesia.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

@Component
public class UserCredentialsGenerator {

    private  Map<UUID, Trainer> trainerStorage;
    private  Map<UUID, Trainee> traineeStorage;

    @Autowired
    @Qualifier("traineeStorage")
    public void setTraineeStorage(Map<UUID, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Autowired
    @Qualifier("trainerStorage")
    public void setTrainerStorage(Map<UUID, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public String generateUsername(User user) {

        if(user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        if (user.getFirstName() == null || user.getLastName() == null) {
            throw new IllegalArgumentException("First name and last name must not be null");
        }

        long traineeNumber= traineeStorage.values().stream().filter(u -> {
            return user.getFirstName().equals(u.getUser().getFirstName())
                    && user.getLastName().equals(u.getUser().getLastName());
        }).count();

        long trainerNumber = trainerStorage.values().stream().filter(u -> {
            return user.getFirstName().equals(u.getUser().getFirstName())
                    && user.getLastName().equals(u.getUser().getLastName());
        }).count();

        String username = user.getFirstName() + "." + user.getLastName();

        long total = traineeNumber + trainerNumber;
        if(total > 0) {
            username = username + (total + 1);
        }

        return username;
    }

    public String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}
