package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.model.dto.ChangeRoleDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;

public class ChangeRoleExecuter implements QueryExecuter<Void> {

    private String userId;
    private ChangeRoleDTO changeRoleDTO;

    public ChangeRoleExecuter(String userId) {
        this.userId = userId;
    }

    public ChangeRoleExecuter(ChangeRoleDTO changeRoleDTO) {
        this.changeRoleDTO = changeRoleDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Query query = session.createQuery("UPDATE User u SET u.role = :role WHERE u.userId = :userId");

        if(userId == null) {
            query.setParameter("role", Role.valueOf(changeRoleDTO.getRole()));
            query.setParameter("userId", changeRoleDTO.getUserId());
        }
        else{
            query.setParameter("role", Role.MODERATOR);
            query.setParameter("userId", userId);
        }

        query.executeUpdate();

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
