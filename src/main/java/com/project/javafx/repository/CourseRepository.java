package com.project.javafx.repository;

import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;

public class CourseRepository extends AbstractRepository<Course, String> {
    private static CourseRepository instance = null;

    private CourseRepository() {
        super(Course.class, MongoDBHandler.COURSE_COLL);
    }

    public static CourseRepository getInstance() {
        if (instance == null) synchronized (CourseRepository.class) {
            if (instance == null) instance = new CourseRepository();
        }
        return instance;
    }

    public void initCourses() {
        CreditCourse creditCourse = new CreditCourse("CSC101", "Continuous mathematics", 2, 0.3);
        CreditCourse creditCourse2 = new CreditCourse("CSC102", "Design and analysis of algorithms", 3, 0.3);
        CreditCourse creditCourse3 = new CreditCourse("CSC103", "Digital systems", 2, 0.3);
        Course course = new Course("PHY130", "Physics I", 0.4);
        Course course2 = new Course("PHY132", "Physics I Lab", 0.3);
        Course course3 = new Course("PHY230", "Physics II", 0.2);

        save(creditCourse);
        save(creditCourse2);
        save(creditCourse3);
        save(course);
        save(course2);
        save(course3);
    }

    @Override
    public Set<Course> getObjectCollection() {
        Set<Course> courses = new HashSet<>();
        Document query1 = new Document("creditHours", new Document("$eq", null));
        Document query2 = new Document("creditHours", new Document("$ne", null));
        Set<Course> courses1 = getObjectCollection(query1, Course.class);
        Set<Course> courses2 = getObjectCollection(query2, CreditCourse.class);
        courses.addAll(courses1);
        courses.addAll(courses2);
        return courses;
    }

    public Set<CreditCourse> findAllCreditCourse() {
        Set<CreditCourse> creditCourses = new HashSet<>();
        Document query = new Document("creditHours", new Document("$ne", null));
        Set<Course> courses = getObjectCollection(query, CreditCourse.class);
        for (Course cours : courses) {
            if (cours instanceof CreditCourse) {
                creditCourses.add((CreditCourse) cours);
            }
        }
        return creditCourses;
    }

    public Set<Course> findAllNormalCourse() {
        Document query = new Document("creditHours", new Document("$eq", null));
        return getObjectCollection(query, Course.class);
    }

    @Override
    protected String getID(Course e) {
        return e.getCourseCode();
    }

//    @Override
//    protected Gson gsonCreator() {
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .registerTypeAdapterFactory(setAdapter())
////                .setExclusionStrategies(new StudentExclusionStrategy())
//                .create();
//        return gson;
//    }
}
