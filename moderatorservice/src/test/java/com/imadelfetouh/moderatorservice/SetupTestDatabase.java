package com.imadelfetouh.moderatorservice;

import com.imadelfetouh.moderatorservice.dal.configuration.QueryExecuter;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Profile;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.dal.ormmodel.User;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import org.hibernate.Session;

public class SetupTestDatabase implements QueryExecuter<Void> {
    @Override
    public ResponseModel<Void> executeQuery(Session session) {
        ResponseModel<Void> responseModel = new ResponseModel<>();

        Profile profile1 = new Profile("p123", "bio", "location", "website");
        Profile profile2 = new Profile("p1234", "bio", "location", "website");

        User user1 = new User("u123", "imad", Role.USER, "imad.jpg", profile1);
        User user2 = new User("u1234", "peter", Role.USER, "peter.jpg", profile2);

        session.persist(profile1);
        session.persist(profile2);
        session.persist(user1);
        session.persist(user2);

        session.getTransaction().commit();

        return responseModel;
    }
}
