package com.imadelfetouh.moderatorservice.rabbit.consumer;

import com.imadelfetouh.moderatorservice.rabbit.Monitor;
import com.imadelfetouh.moderatorservice.rabbit.NonStopConsumer;
import com.imadelfetouh.moderatorservice.rabbit.delivercallback.ChangeRoleDeliverCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeRoleConsumer implements NonStopConsumer {

    private static final Logger logger = Logger.getLogger(ChangeRoleConsumer.class.getName());

    private final String queue_name;
    private final String exchange_name;

    public ChangeRoleConsumer() {
        queue_name = "moderatorservice_changeroleconsumer";
        exchange_name = "changeroleexchange";
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.exchangeDeclare(exchange_name, "direct", true);
            channel.queueBind(queue_name, exchange_name, "");

            DeliverCallback deliverCallback = new ChangeRoleDeliverCallback();

            channel.basicConsume(queue_name, true, deliverCallback, s -> {});

            Monitor monitor = new Monitor();
            monitor.start();
        }
        catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
