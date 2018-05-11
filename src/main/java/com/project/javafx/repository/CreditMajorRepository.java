package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.ulti.gsonUtil.CreditCourseDeserializer;
import com.project.javafx.ulti.gsonUtil.CreditMajorDeserializer;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.util.List;

public class CreditMajorRepository extends AbstractRepository<CreditMajor, String> {

    private static CreditMajorRepository instance = null;
    private static final String path = "CreditMajors.json";

    private CreditMajorRepository() {
        super(CreditMajor.class,MongoDBHandler.MAJOR_COLL,path);
    }

    public static CreditMajorRepository getInstance() {
        if (instance == null) synchronized (CreditMajorRepository.class) {
            if (instance == null) instance = new CreditMajorRepository();
        }
        return instance;
    }

    public void deleteCourseForAllMajor(CreditCourse course) {
        for (CreditMajor major : objects) {
            List<CreditCourse> minorCatalog = major.getMinorCatalog();
            List<CreditCourse> majorCatalog = major.getMajorCatalog();
            if (minorCatalog.remove(course)
                    || majorCatalog.remove(course)) {
                update(major);
            }
        }
    }

    @Override
    public String getID(CreditMajor element) {
        return element.getMajorCode();
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("majorCode", s);
    }

    @Override
    protected Gson gsonCreator() {
        JsonSerializer<CreditCourse> serializer = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getCourseCode());
            return jsonElement;
        };

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(CreditCourse.class, serializer)
                .registerTypeAdapter(CreditCourse.class, new CreditCourseDeserializer())
                .registerTypeAdapter(CreditMajor.class, new CreditMajorDeserializer())
                .create();
        return gson;
    }
}
