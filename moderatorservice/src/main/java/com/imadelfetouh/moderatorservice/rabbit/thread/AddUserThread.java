package com.imadelfetouh.moderatorservice.rabbit.thread;

import com.imadelfetouh.moderatorservice.rabbit.RabbitNonStopConsumer;
import com.imadelfetouh.moderatorservice.rabbit.consumer.AddUserConsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUserThread implements Runnable {

    private Logger logger = Logger.getLogger(AddUserThread.class.getName());

    @Override
    public void run() {
        while(true) {
            try {
                RabbitNonStopConsumer rabbitNonStopConsumer = new RabbitNonStopConsumer();
                AddUserConsumer addUserConsumer = new AddUserConsumer();

                rabbitNonStopConsumer.consume(addUserConsumer);
            } catch (Exception e) {
                logger.log(Level.ALL, e.getMessage());
            }
        }
    }
}
