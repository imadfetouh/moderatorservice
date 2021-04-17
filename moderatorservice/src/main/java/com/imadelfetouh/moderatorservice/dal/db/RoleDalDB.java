package com.imadelfetouh.moderatorservice.dal.db;

import com.imadelfetouh.moderatorservice.dal.configuration.Executer;
import com.imadelfetouh.moderatorservice.dal.configuration.SessionType;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.ChangeRoleExecuter;
import com.imadelfetouh.moderatorservice.dalinterface.RoleDal;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDalDB implements RoleDal {

    @Override
    public ResponseModel<Void> changeRole(String userId) {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        return executer.execute(new ChangeRoleExecuter(userId));
    }
}
