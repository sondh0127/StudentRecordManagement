package com.project.javafx.repository;

import com.project.javafx.model.CoursesCatalog;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.ulti.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;

public class CreditMajorRepository extends AbstractRepository<CreditMajor> {

    private static final String path = "src/main/resources/public/Major.json";
    private static CreditMajorRepository instance = new CreditMajorRepository(path);

    public CreditMajorRepository(String filepath) {
        super(filepath);
    }

//    public CreditMajorRepository(String filepath) {
//        super(filepath);
//    }

    public static CreditMajorRepository getInstance() {
        return instance;
    }

    @Override
    public String converter(CreditMajor element) {
        return element.getTitleMajor();
    }

    @Override
    protected RuntimeTypeAdapterFactory<CreditMajor> setAdapter() {
        return null;
    }

    @Override
    protected Type setToken() {
        return null;
    }


//    @Override
//    public Type setTypeToken() {
//       return new TypeToken<Set<CreditMajor>>() {}.getType();
//    }

    public void initSomeMajor() {
        CreditMajor csMajor = new CreditMajor("Computer Science", 120, 20);
        CoursesCatalog<CreditCourse> csCatalog = new CoursesCatalog<>();

        csCatalog.addCourse(new CreditCourse("CSC101", "Continuous mathematics", 2));
        csCatalog.addCourse(new CreditCourse("CSC102", "Design and analysis of algorithms", 3));
        csCatalog.addCourse(new CreditCourse("CSC103", "Digital systems", 2));
        csCatalog.addCourse(new CreditCourse("CSC104", "Discrete mathematics", 2));
        csCatalog.addCourse(new CreditCourse("CSC105", "Functional programming", 3));
        csCatalog.addCourse(new CreditCourse("CSC106", "Imperative programming", 2));
        csCatalog.addCourse(new CreditCourse("CSC107", "Introduction to formal proof", 2));
        csCatalog.addCourse(new CreditCourse("CSC108", "Linear algebra", 2));
        csCatalog.addCourse(new CreditCourse("CSC109", "Probability", 2));
        //2nd year
        csCatalog.addCourse(new CreditCourse("CSC201", "Algorithms", 3));
        csCatalog.addCourse(new CreditCourse("CSC202", "Compilers", 3));
        csCatalog.addCourse(new CreditCourse("CSC203", "Concurrent programming", 2));
        csCatalog.addCourse(new CreditCourse("CSC204", "Models of computation", 2));

        csCatalog.addCourse(new CreditCourse("CSO205", "Computer architecture", 2));
        csCatalog.addCourse(new CreditCourse("CSO206", "Computer graphics", 2));
        csCatalog.addCourse(new CreditCourse("CSO207", "Computer networks", 2));
        csCatalog.addCourse(new CreditCourse("CSO208", "Databases", 2));
        csCatalog.addCourse(new CreditCourse("CSO209", "Intelligent systems", 2));
        csCatalog.addCourse(new CreditCourse("CSO210", "Logic and proof", 2));
        // 3rd year
        // 4th year

        csMajor.setMajorCatalog(csCatalog);
//        this.addData(csMajor);
        save(csMajor);
    }
}
