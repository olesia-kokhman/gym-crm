package org.olesia.app;

import org.olesia.config.StorageConfig;
import org.olesia.facade.GymFacade;
import org.olesia.model.Trainee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StorageConfig.class);

        GymFacade gymFacade = context.getBean(GymFacade.class);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Mike");
        trainee.setLastName("Tyson");
        trainee.setAddress("Kyiv");

        Trainee createdTrainee = gymFacade.createTrainee(trainee);

        System.out.println("Created trainee id: " + createdTrainee.getId());
        System.out.println("Created trainee username: " + createdTrainee.getUsername());

        context.close();
    }
}

