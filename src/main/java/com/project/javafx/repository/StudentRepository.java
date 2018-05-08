package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.ulti.gsonUtil.AnnualStudentDeserializer;
import com.project.javafx.ulti.gsonUtil.CreditMajorDeserializer;
import com.project.javafx.ulti.gsonUtil.RuntimeTypeAdapterFactory;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

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

    public void initSomeStudent() {

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
        Set<CreditStudent> students = new HashSet<>();
        for (Student student : objects) {
            if (student instanceof CreditStudent) {
                students.add((CreditStudent) student);
            }
        }
        return students;
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
        Type majorType = new TypeToken<CreditMajor>() {}.getType();
        JsonSerializer<CreditMajor> serializer = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getMajorCode());
            return jsonElement;
        };
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(setAdapter())
                .registerTypeAdapter(majorType, serializer)
                .registerTypeAdapter(AnnualStudent.class, new AnnualStudentDeserializer())
                .registerTypeAdapter(CreditMajor.class, new CreditMajorDeserializer())
                .create();
        return gson;
    }

}
