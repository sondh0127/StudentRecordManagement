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

    private CourseRepository(String filepath) {
        super(filepath);
    }

    public void initCourses() {
        CreditCourse creditCourse  = new CreditCourse("CSC101", "Continuous mathematics", 2);
        CreditCourse creditCourse2  = new CreditCourse("CSC102", "Design and analysis of algorithms", 3);
        CreditCourse creditCourse3  = new CreditCourse("CSC103", "Digital systems", 2);
        Course course = new Course("PHY130", "Physics I");
        Course course2 = new Course("PHY132", "Physics I Lab");
        Course course3 = new Course("PHY230", "Physics II");

        CourseRepository.getInstance().save(creditCourse);
        CourseRepository.getInstance().save(creditCourse2);
        CourseRepository.getInstance().save(creditCourse3);
        CourseRepository.getInstance().save(course);
        CourseRepository.getInstance().save(course2);
        CourseRepository.getInstance().save(course3);
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
                        .registerSubtype(Course.class, Course.class.getSimpleName())
                        .registerSubtype(CreditCourse.class, CreditCourse.class.getSimpleName());
        return adapter;
    }

    @Override
    protected Type setToken() {
        return new TypeToken<Set<Course>>() {}.getType();
    }
}
