package com.project.javafx.model;


import java.util.ArrayList;
import java.util.List;

public class AnnualClass {

    private final String classCode;
    private final String className;

    private List<AnnualStudent> students;

    private List<List<Course>> courseCatalog;

    public AnnualClass(String classCode, String className, int capacity) {
        this.classCode = classCode;
        this.className = className;
        courseCatalog = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            courseCatalog.add(new ArrayList<>());
        }

        students = new ArrayList<>(capacity);
    }

    @Override
    public String toString() {
        return className;
    }

    // main method
    public void addAnnualCourseCatalog(Course course, StudyLevel year) {
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

    public List<Course> getCoursesCatalog(StudyLevel year) {
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
        return new ArrayList<>();
    }

    public void setCoursesCatalog(List<Course> coursesCatalog, StudyLevel year) {
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

    public void addStudentToClass(AnnualStudent student) {
        students.add(student);
        student.setAnnualClass(this);
        student.addYearCourseList(courseCatalog.get(0));
    }

    public void removeStudent(AnnualStudent student) {
        students.remove(student);
    }

    public List<AnnualStudent> getStudents() {
        return students;
    }

    public boolean haveStudent(AnnualStudent student) {
        return students.contains(student);
    }

}
