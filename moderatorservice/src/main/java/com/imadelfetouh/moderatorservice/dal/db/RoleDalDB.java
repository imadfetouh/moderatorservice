package com.imadelfetouh.moderatorservice.dal.db;

import com.imadelfetouh.moderatorservice.dal.configuration.Executer;
import com.imadelfetouh.moderatorservice.dal.configuration.SessionType;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.ChangeRoleExecuter;
import com.imadelfetouh.moderatorservice.dalinterface.RoleDal;
import com.imadelfetouh.moderatorservice.model.dto.ChangeRoleDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import com.imadelfetouh.moderatorservice.rabbit.RabbitProducer;
import com.imadelfetouh.moderatorservice.rabbit.producer.ChangeRoleProducer;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDalDB implements RoleDal {

    @Override
    public ResponseModel<Void> changeRole(String userId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        ResponseModel<Void> responseModel = executer.execute(new ChangeRoleExecuter(userId));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            RabbitProducer rabbitProducer = new RabbitProducer();
            ChangeRoleDTO changeRoleDTO = new ChangeRoleDTO(userId, Role.MODERATOR.name());
            rabbitProducer.produce(new ChangeRoleProducer(changeRoleDTO));
        }

        return responseModel;
    }
}
