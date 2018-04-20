package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;

import java.lang.reflect.Type;
import java.util.Set;

public class AnnualStudentRepository extends AbstractRepository<AnnualStudent, Long> {

    private static final String path = "src/main/resources/public/AnnualStudents.json";
    private static AnnualStudentRepository instance = new AnnualStudentRepository(path);

    public AnnualStudentRepository(String filepath) {
        super(AnnualStudent.class, filepath,MongoUtils.CREDIT_STUDENT_COLL);
    }

    public static AnnualStudentRepository getInstance() {
        return instance;
    }

    @Override
    protected Long getID(AnnualStudent e) {
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
        return new TypeToken<Set<AnnualStudent>>() {
        }.getType();
    }
}
