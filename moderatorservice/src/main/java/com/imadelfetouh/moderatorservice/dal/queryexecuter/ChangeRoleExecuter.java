package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class ChangeRoleExecuter implements QueryExecuter<Void> {

    private String userId;

    public ChangeRoleExecuter(String userId) {
        this.userId = userId;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createQuery("UPDATE User u SET u.role = :role WHERE u.userId = :userId");
        query.setParameter("role", Role.MODERATOR);
        query.setParameter("userId", userId);

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
