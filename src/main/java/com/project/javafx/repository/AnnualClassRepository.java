package com.project.javafx.repository;

import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Course;
import com.project.javafx.model.YearOfStudy;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;

import java.util.ArrayList;
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

    public void initSomeClass() {
        AnnualClass newClass = new AnnualClass("PHY", "Physic");
        List<Course> coursesCatalog = new ArrayList<>();

        coursesCatalog.add(new Course("PHY130", "Physics I", 0.3));
        coursesCatalog.add(new Course("PHY132", "Physics I Lab", 0.3));
        coursesCatalog.add(new Course("PHY230", "Physics II", 0.3));
        coursesCatalog.add(new Course("PHY232", "Physics II Lab", 0.3));
        coursesCatalog.add(new Course("PHY245", "Physics III", 0.3));
        coursesCatalog.add(new Course("PHY246", "Physics III Lab", 0.3));
        coursesCatalog.add(new Course("CHE133", "College Chemistry I", 0.3));
        coursesCatalog.add(new Course("CHE134", "College Chemistry II", 0.3));
        coursesCatalog.add(new Course("CHE250", "Organic Chemistry I", 0.3));
        coursesCatalog.add(new Course("CHE251", "Organic Chemistry II", 0.3));
        coursesCatalog.add(new Course("BIO150", "Modern Biology I", 0.3));
        coursesCatalog.add(new Course("BIO152", "Modern Biology II", 0.3));
        coursesCatalog.add(new Course("ENG101", "Standard Freshman Composition", 0.3));
        coursesCatalog.add(new Course("ENG102", "Introduction to Literature", 0.3));
        coursesCatalog.add(new Course("ENG121", "Technical Writing", 0.3));

        newClass.setCoursesCatalog(coursesCatalog, YearOfStudy.FIRST_YEAR);
        save(newClass);
    }

    public void deleteCourseForAllClass(Course course) {
        for (AnnualClass annualClass : getObjectCollection()) {
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

    @Override
    public String getID(AnnualClass element) {
        return element.getClassCode();
    }

}
