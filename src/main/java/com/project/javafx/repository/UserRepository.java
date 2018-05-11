package com.project.javafx.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.User;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.Set;

public class UserRepository extends AbstractRepository<User, String> {

    private static UserRepository instance = null;
    private static final String path = "src/main/resources/Users.json";

    private UserRepository() {
        super(User.class, USER_COLL, path);
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
    protected Type setListType() {
        return new TypeToken<Set<User>>() {}.getType();
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("username", s);
    }
}
