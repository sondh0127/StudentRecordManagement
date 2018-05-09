package com.project.javafx.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Course;
import com.project.javafx.model.YearOfStudy;
import com.project.javafx.ulti.gsonUtil.AnnualStudentDeserializer;
import com.project.javafx.ulti.gsonUtil.CourseDeserializer;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.List;

public class AnnualClassRepository extends AbstractRepository<AnnualClass, String> {

    private static AnnualClassRepository instance = null;

    private AnnualClassRepository() {
        super(AnnualClass.class, MongoDBHandler.CLASS_COLL);
    }

    public static AnnualClassRepository getInstance() {
        if (instance == null) synchronized (AnnualClassRepository.class) {
            if (instance == null) instance = new AnnualClassRepository();
        }
        return instance;
    }

    public void deleteCourseForAllClass(Course course) {
        for (AnnualClass annualClass : objects) {
            List<Course> FIRST_YEAR = annualClass.getCoursesCatalog(YearOfStudy.FIRST_YEAR);
            List<Course> SECOND_YEAR = annualClass.getCoursesCatalog(YearOfStudy.SECOND_YEAR);
            List<Course> THIRD_YEAR = annualClass.getCoursesCatalog(YearOfStudy.THIRD_YEAR);
            List<Course> FOURTH_YEAR = annualClass.getCoursesCatalog(YearOfStudy.FOURTH_YEAR);

            if (FIRST_YEAR.remove(course)
                    || SECOND_YEAR.remove(course)
                    || THIRD_YEAR.remove(course)
                    || FOURTH_YEAR.remove(course)) {
                update(annualClass);
            }

        }
    }

    public AnnualClass getAnnualClassOf(AnnualStudent student) {
        return findAll().stream()
                .filter(annualClass -> annualClass.haveStudent(student))
                .findFirst()
                .orElse(null);
    }

    public void removeAStudentFromClass(AnnualStudent removeStudent) {
        AnnualClass annualClassOf = getAnnualClassOf(removeStudent);
        annualClassOf.removeStudent(removeStudent);
        update(annualClassOf);
    }

    @Override
    public String getID(AnnualClass element) {
        return element.getClassCode();
    }

    @Override
    protected Document findOldQuery(String s) {
        return new Document("classCode", s);
    }

    @Override
    protected Gson gsonCreator() {
        Type studentListType = new TypeToken<List<AnnualStudent>>() {
        }.getType();
        JsonSerializer<List<AnnualStudent>> serializer = (src, typeOfSrc, context) -> {
            JsonArray jsonStudents = new JsonArray();
            for (AnnualStudent annualStudent : src) {
                jsonStudents.add(annualStudent.getStudentID());
            }
            return jsonStudents;
        };
        JsonSerializer<Course> serializer2 = (src, typeOfSrc, context) -> {
            JsonPrimitive jsonElement = new JsonPrimitive(src.getCourseCode());
            return jsonElement;
        };
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(studentListType, serializer)
                .registerTypeAdapter(Course.class, serializer2)
                .registerTypeAdapter(AnnualStudent.class, new AnnualStudentDeserializer())
                .registerTypeAdapter(Course.class, new CourseDeserializer())
                .create();
        return gson;
    }
}
