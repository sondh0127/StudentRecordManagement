package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;

import java.lang.reflect.Type;
import java.util.Set;

public class CreditStudentRepository extends AbstractRepository<CreditStudent, Long> {
    private static final String path = "src/main/resources/public/CreditStudents.json";
    private static CreditStudentRepository instance = new CreditStudentRepository(path);

    public CreditStudentRepository(String filepath) {
        super(CreditStudent.class, filepath,MongoUtils.CREDIT_STUDENT_COLL);
    }

    public static CreditStudentRepository getInstance() {
        return instance;
    }

    @Override
    protected Long getID(CreditStudent e) {
        return e.getStudentID();
    }

    @Override
    protected Gson gsonCreator() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();
        return gson;
    }

    @Override
    protected Type setToken() {
        return new TypeToken<Set<CreditStudent>>() {
        }.getType();
    }
}
