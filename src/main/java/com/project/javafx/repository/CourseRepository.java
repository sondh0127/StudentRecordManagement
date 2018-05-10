package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.ulti.gsonUtil.CourseDeserializer;
import com.project.javafx.ulti.gsonUtil.CreditCourseDeserializer;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void getObjectCollection() {
        Document query1 = new Document("creditHours", new Document("$eq", null));
        Document query2 = new Document("creditHours", new Document("$ne", null));
        getObjectCollection(query1, Course.class);
        getObjectCollection(query2, CreditCourse.class);
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("courseCode", s);
    }

    public Set<CreditCourse> findAllCreditCourse() {
        return objects.stream()
                .filter(cours -> cours instanceof CreditCourse)
                .map(cours -> (CreditCourse) cours)
                .collect(Collectors.toSet());
    }

    public Set<Course> findAllNormalCourse() {
        return objects.stream()
                .filter(course -> !(course instanceof CreditCourse))
                .collect(Collectors.toSet());
    }

    @Override
    protected String getID(Course e) {
        return e.getCourseCode();
    }

    @Override
    protected Gson gsonCreator() {
        Type courseType = new TypeToken<List<CreditCourse>>() {
        }.getType();
        JsonSerializer<List<CreditCourse>> serializer = (src, typeOfSrc, context) -> {
            JsonArray jsons = new JsonArray();
            for (CreditCourse course : src) {
                jsons.add(course.getCourseCode());
            }
            return jsons;
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(courseType, serializer)
                .registerTypeAdapter(CreditCourse.class, new CreditCourseDeserializer())
                .registerTypeAdapter(Course.class, new CourseDeserializer())
                .create();
        return gson;
    }
}
