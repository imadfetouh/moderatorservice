package com.imadelfetouh.moderatorservice.dal.queryexecuter;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Profile;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.dal.ormmodel.User;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        User user1 = new User("u123", "imad", Role.USER, "imad.jpg", new Profile("p123", "bio", "location", "website"));
        User user2 = new User("u1234", "peter", Role.USER, "peter.jpg", new Profile("p123", "bio", "location", "website"));

        session.persist(user1);
        session.persist(user2);

        session.getTransaction().commit();

        return responseModel;
    }
}
