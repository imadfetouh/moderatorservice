package com.imadelfetouh.moderatorservice.rabbit.thread;

import com.imadelfetouh.moderatorservice.rabbit.RabbitNonStopConsumer;
import com.imadelfetouh.moderatorservice.rabbit.consumer.DefaultConsumer;
import com.imadelfetouh.moderatorservice.rabbit.delivercallback.ChangeRoleDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeRoleThread implements Runnable {

    private Logger logger = Logger.getLogger(ChangeRoleThread.class.getName());

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public ChangeRoleThread() {
        queue_name = "moderatorservice_changeroleconsumer";
        exchange_name = "changeroleexchange";
        deliverCallback = new ChangeRoleDeliverCallback();
    }

    @Override
    public void run() {
        StartConsuming startConsuming = new StartConsuming(queue_name, exchange_name, deliverCallback);
        startConsuming.start();
    }
}
