package com.project.javafx.repository;

import com.project.javafx.model.User;

public class UserRepository extends AbstractRepository<User, String> {

    private static UserRepository instance = null;

    private UserRepository() {
        super(User.class, USER_COLL);
    }

    public static UserRepository getInstance() {
        if (instance == null) synchronized (AnnualClassRepository.class) {
            if (instance == null) instance = new UserRepository();
        }
        return instance;
    }

    @Override
    protected String getID(User e) {
        return e.getUsername();
    }
}
