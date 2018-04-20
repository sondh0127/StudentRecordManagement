package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreditMajorRepository extends AbstractRepository<CreditMajor, String> {

    private static final String path = "src/main/resources/public/Majors.json";
    private static CreditMajorRepository instance = new CreditMajorRepository(path);

    private CreditMajorRepository(String filepath) {
        super(CreditMajor.class, filepath,MongoUtils.MAJOR_COLL);
    }

    public static CreditMajorRepository getInstance() {
        return instance;
    }

    public void initSomeMajor() {
        CreditMajor csMajor = new CreditMajor("CS01", "Computer Science", 120, 20);
        CreditMajor comMajor = new CreditMajor("CM02", "Communications", 120, 20);
        List<CreditCourse> csCatalogMajor = new ArrayList<>();
        List<CreditCourse> csCatalogMinor = new ArrayList<>();

        csCatalogMajor.add(new CreditCourse("CSC101", "Continuous mathematics", 2));
        csCatalogMajor.add(new CreditCourse("CSC102", "Design and analysis of algorithms", 3));
        csCatalogMajor.add(new CreditCourse("CSC103", "Digital systems", 2));
        csCatalogMajor.add(new CreditCourse("CSC104", "Discrete mathematics", 2));
        csCatalogMajor.add(new CreditCourse("CSC105", "Functional programming", 3));
        csCatalogMajor.add(new CreditCourse("CSC106", "Imperative programming", 2));
        csCatalogMajor.add(new CreditCourse("CSC107", "Introduction to formal proof", 2));
        csCatalogMajor.add(new CreditCourse("CSC108", "Linear algebra", 2));
        csCatalogMajor.add(new CreditCourse("CSC109", "Probability", 2));
        //2nd year
        csCatalogMajor.add(new CreditCourse("CSC201", "Algorithms", 3));
        csCatalogMajor.add(new CreditCourse("CSC202", "Compilers", 3));
        csCatalogMajor.add(new CreditCourse("CSC203", "Concurrent programming", 2));
        csCatalogMajor.add(new CreditCourse("CSC204", "Models of computation", 2));

        csCatalogMinor.add(new CreditCourse("CSO205", "Computer architecture", 2));
        csCatalogMinor.add(new CreditCourse("CSO206", "Computer graphics", 2));
        csCatalogMinor.add(new CreditCourse("CSO207", "Computer networks", 2));
        csCatalogMinor.add(new CreditCourse("CSO208", "Databases", 2));
        csCatalogMinor.add(new CreditCourse("CSO209", "Intelligent systems", 2));
        csCatalogMinor.add(new CreditCourse("CSO210", "Logic and proof", 2));
        // 3rd year
        // 4th year

        csMajor.createMajorCatalog(csCatalogMajor);
        csMajor.createMinorCatalog(csCatalogMinor);

        comMajor.createMinorCatalog(csCatalogMajor);
        comMajor.createMajorCatalog(csCatalogMinor);

        save(csMajor);
        save(comMajor);
    }

    @Override
    public String getID(CreditMajor element) {
        return element.getMajorCode();
    }

    @Override
    protected Gson gsonCreator() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
//                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();
        return gson;
    }

    @Override
    protected Type setToken() {
        return new TypeToken<Set<CreditMajor>>() {
        }.getType();
    }
}
