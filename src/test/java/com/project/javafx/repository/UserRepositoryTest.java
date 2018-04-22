package com.project.javafx.repository;

import com.project.javafx.model.User;
import org.junit.Test;

public class UserRepositoryTest {

    @Test
    public void init() {
        UserRepository.getInstance().save(new User("Admin", "adminpass"));
    }

}
