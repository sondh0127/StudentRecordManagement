package com.project.javafx.repository;

import com.project.javafx.model.User;
import com.project.javafx.model.UserType;
import org.junit.Test;

public class UserRepositoryTest {

    @Test
    public void init() {
        UserRepository.getInstance().save(new User("admin", "adminpass", UserType.ADMINISTRATOR));
        UserRepository.getInstance().save(new User("Admin", "adminpass", UserType.ADMINISTRATOR));
    }

}
