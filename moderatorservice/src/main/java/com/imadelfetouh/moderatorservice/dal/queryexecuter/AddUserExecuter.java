package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Profile;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.dal.ormmodel.User;
import com.imadelfetouh.moderatorservice.model.dto.NewUserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.hibernate.Session;

public class AddUserExecuter implements QueryExecuter<Void> {

    private NewUserDTO newUserDTO;

    public AddUserExecuter(NewUserDTO newUserDTO) {
        this.newUserDTO = newUserDTO;
    }

    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Profile profile = new Profile(newUserDTO.getProfile().getProfileId(), newUserDTO.getProfile().getBio(), newUserDTO.getProfile().getLocation(), newUserDTO.getProfile().getWebsite());

        User user = new User(newUserDTO.getUserId(), newUserDTO.getUsername(), newUserDTO.getPassword(), Role.valueOf(newUserDTO.getRole()), newUserDTO.getPhoto(), profile);

        session.persist(profile);
        session.persist(user);

        session.getTransaction().commit();

        responseModel.setResponseType(ResponseType.CORRECT);

        return responseModel;
    }
}
