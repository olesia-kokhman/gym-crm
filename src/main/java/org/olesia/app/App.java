package org.olesia.app;

import org.olesia.config.StorageConfig;
import org.olesia.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StorageConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);
        gymFacade.createTrainee();
    }
}
