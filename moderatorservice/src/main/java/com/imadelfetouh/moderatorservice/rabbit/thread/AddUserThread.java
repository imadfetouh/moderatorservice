package com.imadelfetouh.moderatorservice.rabbit.thread;

import com.imadelfetouh.moderatorservice.rabbit.RabbitNonStopConsumer;
import com.imadelfetouh.moderatorservice.rabbit.consumer.DefaultConsumer;
import com.imadelfetouh.moderatorservice.rabbit.delivercallback.AddUserDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUserThread implements Runnable {

    private Logger logger = Logger.getLogger(AddUserThread.class.getName());

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public AddUserThread() {
        queue_name = "moderatorservice_adduserconsumer";
        exchange_name = "adduserexchange";
        deliverCallback = new AddUserDeliverCallback();
    }

    @Override
    public void run() {
        StartConsuming startConsuming = new StartConsuming(queue_name, exchange_name, deliverCallback);
        startConsuming.start();
    }
}
