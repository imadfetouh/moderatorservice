package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        return new ResponseModel<>();
    }
}
