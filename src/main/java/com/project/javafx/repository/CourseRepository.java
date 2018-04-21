package com.project.javafx.repository;

import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;

public class CourseRepository extends AbstractRepository<Course, String> {
    private static CourseRepository instance = new CourseRepository();

    private CourseRepository() {
        super(Course.class,MongoUtils.COURSE_COLL);
    }

    public static CourseRepository getInstance() {
        return instance;
    }

    public void initCourses() {
        CreditCourse creditCourse = new CreditCourse("CSC101", "Continuous mathematics", 2);
        CreditCourse creditCourse2 = new CreditCourse("CSC102", "Design and analysis of algorithms", 3);
        CreditCourse creditCourse3 = new CreditCourse("CSC103", "Digital systems", 2);
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
    public Set<Course> getObjectCollection() {
        Set<Course> courses = new HashSet<>();
        Document query1 = new Document("creditHours", new Document("$eq", null));
        Document query2 = new Document("creditHours", new Document("$ne", null));
        Set<Course> courses1 = getInstance().getObjectCollection(query1, Course.class);
        Set<Course> courses2 = getInstance().getObjectCollection(query2, CreditCourse.class);
        courses.addAll(courses1);
        courses.addAll(courses2);
        return courses;
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
