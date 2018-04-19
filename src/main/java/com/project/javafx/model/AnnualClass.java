package com.project.javafx.model;

import com.project.javafx.model.AnnualStudent.YearOfStudy;

import java.util.ArrayList;
import java.util.List;

public class AnnualClass {

    private final String classCode;
    private final String className;

    private List<List<Course>> courseCatalog = new ArrayList<>(4);

    private List<AnnualStudent> studentList = new ArrayList<>();

    public AnnualClass(String classCode, String className) {
        this.classCode = classCode;
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }

    // main method
    public void createCatalog(Course course, YearOfStudy year) {
        switch (year) {
            case FIRST_YEAR:
                courseCatalog.get(0).add(course);
                break;
            case SECOND_YEAR:
                courseCatalog.get(1).add(course);
                break;
            case THIRD_YEAR:
                courseCatalog.get(2).add(course);
                break;
            case FOURTH_YEAR:
                courseCatalog.get(3).add(course);
                break;
        }
    }

    // getter and setter
    public String getClassName() {
        return className;
    }

    public String getClassCode() {
        return classCode;
    }

    public List<Course> getCoursesCatalog(YearOfStudy year) {
        switch (year) {
            case FIRST_YEAR:
                return courseCatalog.get(0);
            case SECOND_YEAR:
                return courseCatalog.get(1);
            case THIRD_YEAR:
                return courseCatalog.get(2);
            case FOURTH_YEAR:
                return courseCatalog.get(3);
        }
        return null;
    }

    public void setCoursesCatalog(List<Course> coursesCatalog, YearOfStudy year) {
        switch (year) {
            case FIRST_YEAR:
                courseCatalog.add(0, coursesCatalog);
            case SECOND_YEAR:
                courseCatalog.add(1, coursesCatalog);
            case THIRD_YEAR:
                courseCatalog.add(2, coursesCatalog);
            case FOURTH_YEAR:
                courseCatalog.add(3, coursesCatalog);
        }
    }

    /**
     * call when add a class to student.
     *
     * @param student: AnnualStudent
     */
    public void addStudent(AnnualStudent student) {
        if (studentList.contains(student)) throw new IllegalArgumentException("Student is exists");
        else studentList.add(student);
    }

}
