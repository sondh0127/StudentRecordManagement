package com.project.javafx.repository;

import com.project.javafx.model.User;
import org.bson.Document;

public class UserRepository extends AbstractRepository<User, String> {

    private static UserRepository instance = null;

    private UserRepository() {
        super(User.class, USER_COLL);
    }

    public static UserRepository getInstance() {
        if (instance == null) synchronized (UserRepository.class) {
            if (instance == null) instance = new UserRepository();
        }
        return instance;
    }

    @Override
    protected String getID(User e) {
        return e.getUsername();
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("username", s);
    }
}
