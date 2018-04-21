package com.project.javafx.repository;

import com.project.javafx.model.User;

import static com.project.javafx.ulti.mongoDBUtil.MongoUtils.USER_COLL;

public class UserRepository extends AbstractRepository<User, String> {

    private static UserRepository instance = new UserRepository();

    public UserRepository() {
        super(User.class, USER_COLL);
    }

    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    protected String getID(User e) {
        return e.getUsername();
    }
}
