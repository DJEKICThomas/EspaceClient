package be.depairon.espaceclient.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public abstract class GenericDao {

    public static int MAX_RESULTS = 50000;
    protected final String SCHEMA = "YG51TT";

    @Autowired
    protected EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
