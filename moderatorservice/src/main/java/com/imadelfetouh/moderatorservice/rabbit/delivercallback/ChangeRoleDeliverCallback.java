package com.imadelfetouh.moderatorservice.rabbit.delivercallback;

import com.google.gson.Gson;
import com.imadelfetouh.moderatorservice.dal.configuration.Executer;
import com.imadelfetouh.moderatorservice.dal.configuration.SessionType;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.ChangeRoleExecuter;
import com.imadelfetouh.moderatorservice.model.dto.ChangeRoleDTO;
import com.imadelfetouh.moderatorservice.rabbit.RabbitProps;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeRoleDeliverCallback implements DeliverCallback {

    private final static Logger logger = Logger.getLogger(AddUserDeliverCallback.class.getName());

    private Gson gson;
    private RabbitProps rabbitProps;

    public ChangeRoleDeliverCallback() {
        gson = new Gson();
        rabbitProps = RabbitProps.getInstance();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        try {
            if(!delivery.getProperties().getCorrelationId().equals(rabbitProps.getCorrId())) {
                String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
                ChangeRoleDTO changeRoleDTO = gson.fromJson(json, ChangeRoleDTO.class);

                Executer<Void> executer = new Executer<>(SessionType.WRITE);
                executer.execute(new ChangeRoleExecuter(changeRoleDTO));
            }
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
