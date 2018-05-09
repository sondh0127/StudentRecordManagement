package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.CreditClass;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.ulti.gsonUtil.CreditCourseDeserializer;
import com.project.javafx.ulti.gsonUtil.CreditStudentDeserializer;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreditClassRepository extends AbstractRepository<CreditClass, String> {

    private static CreditClassRepository instance = null;

    private CreditClassRepository() {
        super(CreditClass.class, MongoDBHandler.CREDIT_CLASS_COLL);
    }

    public static CreditClassRepository getInstance() {
        if (instance == null) synchronized (CreditClassRepository.class) {
            if (instance == null) instance = new CreditClassRepository();
        }
        return instance;
    }

    public Set<CreditClass> getCreditClassOf(CreditCourse course) {
        return findAll().stream()
                .filter(creditClass -> creditClass.getCourse().equals(course))
                .collect(Collectors.toSet());
    }

    public void removeAStudentFromClass(CreditStudent removeStudent) {
        for (CreditClass creditClass : objects) {
            List<CreditStudent> studentList = creditClass.getStudentList();
            if (studentList.contains(removeStudent)) {
                studentList.remove(removeStudent);
                update(creditClass);
            }
        }
    }


    @Override
    public String getID(CreditClass element) {
        return element.getClassCode();
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("classCode", s);
    }

    @Override
    protected Gson gsonCreator() {
        Type creditCourseList = new TypeToken<CreditCourse>() {
        }.getType();
        JsonSerializer<CreditCourse> serializer = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getCourseCode());
            return jsonElement;
        };
        Type creditStudentList = new TypeToken<CreditStudent>() {
        }.getType();
        JsonSerializer<CreditStudent> serializer2 = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getStudentID());
            return jsonElement;
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(creditCourseList, serializer)
                .registerTypeAdapter(creditStudentList, serializer2)
                .registerTypeAdapter(CreditCourse.class, new CreditCourseDeserializer())
                .registerTypeAdapter(CreditStudent.class, new CreditStudentDeserializer())
                .create();
        return gson;
    }
}
