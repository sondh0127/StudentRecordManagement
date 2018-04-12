package com.project.javafx.repository;

import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.Course;
import com.project.javafx.model.CoursesCatalog;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.ulti.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;

public class AnnualClassRepository extends AbstractRepository<AnnualClass> {

    private static final String path = "src/main/resources/public/Class.json";
    private static AnnualClassRepository instance = new AnnualClassRepository(path);

    public AnnualClassRepository(String filepath) {
        super(filepath);
    }

    @Override
    public String converter(AnnualClass element) {
        return element.getClassName();
    }

    @Override
    protected RuntimeTypeAdapterFactory<AnnualClass> setAdapter() {
        return null;
    }

    @Override
    protected Type setToken() {
        return null;
    }


    public static AnnualClassRepository getInstance() {
        return instance;
    }

    public void initSomeClass() {
        AnnualClass newClass = new AnnualClass("Physic");
        CoursesCatalog<Course> coursesCatalog = new CoursesCatalog<>();

        coursesCatalog.addCourse(new Course("PHY130", "Physics I"));
        coursesCatalog.addCourse(new Course("PHY132", "Physics I Lab"));
        coursesCatalog.addCourse(new Course("PHY230", "Physics II"));
        coursesCatalog.addCourse(new Course("PHY232", "Physics II Lab"));
        coursesCatalog.addCourse(new Course("PHY245", "Physics III"));
        coursesCatalog.addCourse(new Course("PHY246", "Physics III Lab"));
        coursesCatalog.addCourse(new Course("CHE133", "College Chemistry I"));
        coursesCatalog.addCourse(new Course("CHE134", "College Chemistry II"));
        coursesCatalog.addCourse(new Course("CHE250", "Organic Chemistry I"));
        coursesCatalog.addCourse(new Course("CHE251", "Organic Chemistry II"));
        coursesCatalog.addCourse(new Course("BIO150", "Modern Biology I"));
        coursesCatalog.addCourse(new Course("BIO152", "Modern Biology II"));
        coursesCatalog.addCourse(new Course("ENG101", "Standard Freshman Composition"));
        coursesCatalog.addCourse(new Course("ENG102", "Introduction to Literature"));
        coursesCatalog.addCourse(new Course("ENG121", "Technical Writing"));

        newClass.setCoursesCatalog(coursesCatalog);
        save(newClass);
    }

    public static Course cst111 = new CreditCourse("CST111", "Introduction to Computer Science and Information Technology", 2);
    public static Course cst112 = new CreditCourse("CST112", "Introduction to Object-Oriented Programming", 2);
    public static Course cst121 = new CreditCourse("CST121", "Computer Organization and Assembly Language Programming", 2);
    public static Course cst125 = new CreditCourse("CST125", "Installing, Configuring and Administering Windows", 2);
    public static Course cst141 = new CreditCourse("CST141", "Principles of Computing Using Java I", 2);
    public static Course cst222 = new CreditCourse("CST222", "Computer Architecture", 2);
    public static Course cst227 = new CreditCourse("CST227", "Network Operating Systems and Network Infrastructure", 2);
    public static Course cst242 = new CreditCourse("CST242", "Advanced Programming and Problem Solving with Java", 3);
    public static Course cst246 = new CreditCourse("CST246", "Data Structures", 3);
    public static Course cst288 = new CreditCourse("CST288", "Internship for Information Technology", 2);
    // ELT courses
    public static Course elt112 = new CreditCourse("ELT112", "Electricity I", 2);
    public static Course elt150 = new CreditCourse("ELT150", "The Workings of Personal Computers", 2);
    public static Course elt151 = new CreditCourse("ELT151", "CISCO - Computer Networking I", 2);
    public static Course elt152 = new CreditCourse("ELT152", "CISCO - Computer Networking II", 2);
    public static Course elt224 = new CreditCourse("ELT224", "Electricity II", 2);
    // Math cours, 2es
    public static Course mat107 = new CreditCourse("MAT107", "Computer Mathematics Concepts", 2);
    public static Course mat141 = new CreditCourse("MAT141", "Calculus with Analytic Geometry I", 2);
    public static Course mat142 = new CreditCourse("MAT142", "Calculus with Analytic Geometry II", 2);
    public static Course mat203 = new CreditCourse("MAT203", "Calculus with Analytic Geometry III", 2);
    public static Course mat204 = new CreditCourse("MAT204", "Differential Equations", 2);
    public static Course mat205 = new CreditCourse("MAT205", "Discrete Mathematics", 2);
    // History courses
//    public static Course his101 = new Course("HIS101", "Western Civilization I");
//    public static Course his103 = new Course("HIS103", "Foundations of American History");
//    // Physical education courses
//    public static Course ped119 = new Course("PED119", "Fitness Walking");
//    public static Course ped126 = new Course("PED126", "Pilates");
//    // Freshman Seminar
//    public static Course col101 = new Course("COL101", "Freshman Seminar");
//
//    // Philosophy courses
//    public static Course phl105 = new Course("PHL105", "Logic");
}
