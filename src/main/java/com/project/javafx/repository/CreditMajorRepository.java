package com.project.javafx.repository;

import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;

import java.util.ArrayList;
import java.util.List;

public class CreditMajorRepository extends AbstractRepository<CreditMajor, String> {

    private static CreditMajorRepository instance = null;

    private CreditMajorRepository() {
        super(CreditMajor.class,MongoDBHandler.MAJOR_COLL);
    }

    public static CreditMajorRepository getInstance() {
        if (instance == null) synchronized (CreditMajorRepository.class) {
            if (instance == null) instance = new CreditMajorRepository();
        }
        return instance;
    }

    public void initSomeMajor() {
        CreditMajor csMajor = new CreditMajor("CS01", "Computer Science", 120, 20);
        CreditMajor comMajor = new CreditMajor("CM02", "Communications", 120, 20);
        List<CreditCourse> csCatalogMajor = new ArrayList<>();
        List<CreditCourse> csCatalogMinor = new ArrayList<>();

        csCatalogMajor.add(new CreditCourse("CSC101", "Continuous mathematics", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC102", "Design and analysis of algorithms", 3, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC103", "Digital systems", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC104", "Discrete mathematics", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC105", "Functional programming", 3, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC106", "Imperative programming", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC107", "Introduction to formal proof", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC108", "Linear algebra", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC109", "Probability", 2, 0.2));
        //2nd year
        csCatalogMajor.add(new CreditCourse("CSC201", "Algorithms", 3, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC202", "Compilers", 3, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC203", "Concurrent programming", 2, 0.2));
        csCatalogMajor.add(new CreditCourse("CSC204", "Models of computation", 2, 0.2));

        csCatalogMinor.add(new CreditCourse("CSO205", "Computer architecture", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO206", "Computer graphics", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO207", "Computer networks", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO208", "Databases", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO209", "Intelligent systems", 2, 0.3));
        csCatalogMinor.add(new CreditCourse("CSO210", "Logic and proof", 2, 0.3));
        // 3rd year
        // 4th year

        csMajor.createMajorCatalog(csCatalogMajor);
        csMajor.createMinorCatalog(csCatalogMinor);

        comMajor.createMinorCatalog(csCatalogMajor);
        comMajor.createMajorCatalog(csCatalogMinor);

        save(csMajor);
        save(comMajor);
    }

    public void deleteCourseForAllMajor(CreditCourse course) {
        for (CreditMajor major : getObjectCollection()) {
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
}
