package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.project.javafx.model.*;
import com.project.javafx.ulti.gsonUtil.*;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentRepository extends AbstractRepository<Student, Long> {

    private static StudentRepository instance = null;

    private StudentRepository() {
        super(Student.class, MongoDBHandler.STUDENT_COLL);
    }

    public static StudentRepository getInstance() {
        if (instance == null) synchronized (StudentRepository.class) {
            if (instance == null) instance = new StudentRepository();
        }
        return instance;
    }

    @Override
    public void getObjectCollection() {
        objects.clear();
        Document query1 = new Document("educationSystem", "ANNUAL");
        Document query2 = new Document("educationSystem", "CREDIT");
        getObjectCollection(query1, AnnualStudent.class);
        getObjectCollection(query2, CreditStudent.class);
    }

    @Override
    protected Document findOldQuery(Long aLong) {
        return new Document("studentID", aLong);
    }

    public Set<CreditStudent> getCreditStudent() {
        return objects.stream()
                .filter(student -> student instanceof CreditStudent)
                .map(student -> (CreditStudent) student)
                .collect(Collectors.toSet());
    }

    @Override
    public Long getID(Student element) {
        return element.getStudentID();
    }

    private RuntimeTypeAdapterFactory<Course> setAdapter() {
        RuntimeTypeAdapterFactory<Course> adapter =
                RuntimeTypeAdapterFactory
                        .of(Course.class, "type")
                        .registerSubtype(Course.class, Course.class.getSimpleName())
                        .registerSubtype(CreditCourse.class, CreditCourse.class.getSimpleName());
        return adapter;
    }

    @Override
    protected Gson gsonCreator() {
        JsonSerializer<CreditMajor> serializer = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getMajorCode());
            return jsonElement;
        };
        JsonSerializer<Course> serializerCourse = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getCourseCode());
            return jsonElement;
        };
        JsonSerializer<CreditCourse> serializerCreditCourse = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getCourseCode());
            return jsonElement;
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(setAdapter())
                .registerTypeAdapter(CreditMajor.class, serializer)
                .registerTypeAdapter(CreditMajor.class, new CreditMajorDeserializer())
                .registerTypeAdapter(Course.class, serializerCourse)
                .registerTypeAdapter(Course.class, new CourseDeserializer())
                .registerTypeAdapter(CreditCourse.class, serializerCreditCourse)
                .registerTypeAdapter(CreditCourse.class, new CreditCourseDeserializer())
                .registerTypeAdapter(AnnualStudent.class, new AnnualStudentDeserializer())
                .registerTypeAdapter(CreditStudent.class, new CreditStudentDeserializer())
                .create();
        return gson;
    }

}
