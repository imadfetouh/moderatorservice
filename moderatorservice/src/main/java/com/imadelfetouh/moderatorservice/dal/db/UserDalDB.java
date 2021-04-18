package com.imadelfetouh.moderatorservice.dal.db;

import com.imadelfetouh.moderatorservice.dal.configuration.Executer;
import com.imadelfetouh.moderatorservice.dal.configuration.SessionType;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.DeleteUserExecuter;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.GetUsersExecuter;
import com.imadelfetouh.moderatorservice.dalinterface.UserDal;
import com.imadelfetouh.moderatorservice.model.dto.UserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import com.imadelfetouh.moderatorservice.rabbit.RabbitProducer;
import com.imadelfetouh.moderatorservice.rabbit.producer.DeleteUserProducer;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDalDB implements UserDal {

    @Override
    public ResponseModel<List<UserDTO>> getUsers() {
        Executer<List<UserDTO>> executer = new Executer<>(SessionType.READ);
        return executer.execute(new GetUsersExecuter());
    }

    @Override
    public ResponseModel<Void> deleteUser(String userId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        ResponseModel<Void> responseModel = executer.execute(new DeleteUserExecuter(userId));

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            RabbitProducer rabbitProducer = new RabbitProducer();
            rabbitProducer.produce(new DeleteUserProducer(userId));
        }

        return responseModel;
    }
}
