package com.project.javafx.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.ulti.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.Set;

public class CourseRepository extends AbstractRepository<Course> {

    private static final String path = "src/main/resources/public/Courses.json";
    private static CourseRepository instance = new CourseRepository(path);


    public static CourseRepository getInstance() {
        return instance;
    }

    public CourseRepository(String filepath) {
        super(filepath);
    }

    @Override
    protected String converter(Course e) {
        return e.getCourseCode();
    }

    @Override
    protected RuntimeTypeAdapterFactory<Course> setAdapter() {
        RuntimeTypeAdapterFactory<Course> adapter =
                RuntimeTypeAdapterFactory
                        .of(Course.class, "type")
                        .registerSubtype(CreditCourse.class, CreditCourse.class.getSimpleName());
        return adapter;
    }

    @Override
    protected Type setToken() {
        return new TypeToken<Set<Course>>() {}.getType();
    }
}
