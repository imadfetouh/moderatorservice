package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.model.dto.UserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GetUsersExecuter implements QueryExecuter<List<UserDTO>> {

    @Override
    public ResponseModel<List<UserDTO>> executeQuery(Session session) {
        ResponseModel<List<UserDTO>> responseModel = new ResponseModel<>();

        Query query = session.createQuery("SELECT new com.imadelfetouh.moderatorservice.model.dto.UserDTO(u.userId, cast(u.role as string), u.username, u.photo, u.profile.bio, u.profile.location, u.profile.website) FROM User u LEFT JOIN u.profile p");

        List<UserDTO> userDTOS = query.getResultList();

        responseModel.setData(userDTOS);

        responseModel.setResponseType((userDTOS.isEmpty()) ? ResponseType.EMPTY : ResponseType.CORRECT);

        return responseModel;
    }
}
