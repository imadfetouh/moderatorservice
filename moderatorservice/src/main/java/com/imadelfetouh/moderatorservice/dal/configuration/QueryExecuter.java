package com.imadelfetouh.moderatorservice.dal.configuration;

import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import org.hibernate.Session;

public interface QueryExecuter<T> {

    ResponseModel<T> executeQuery(Session session);
}
