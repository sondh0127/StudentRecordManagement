package com.project.javafx.model;


import java.util.ArrayList;
import java.util.List;

public class AnnualClass {

    private final String classCode;
    private final String className;

    private final int DEFAULT_CAPACITY = 40;
    private int capacity;

    private List<AnnualStudent> students = new ArrayList<>();

    private List<List<Course>> courseCatalog;

    public AnnualClass(String classCode, String className) {
        this.classCode = classCode;
        this.className = className;
        this.capacity = DEFAULT_CAPACITY;
        courseCatalog = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            courseCatalog.add(new ArrayList<>());
        }

//        studentList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return className;
    }

    // main method
    public void addAnnualCourseCatalog(Course course, YearOfStudy year) {
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

    public void addStudentToClass(AnnualStudent student) {
        students.add(student);
        student.registerYearList(courseCatalog.get(0));
    }

    public List<AnnualStudent> getStudents() {
        return students;
    }

    public boolean haveStudent(AnnualStudent student) {
        return students.contains(student);
    }

    public void updateStudyYear() {
        for (AnnualStudent student : students) {
            switch (student.getStudyYear()) {
                case FIRST_YEAR:
                    if (student.passedAllCourseInYear() && student.calculateAVG() > 5.0)
                        student.setStudyYear(YearOfStudy.SECOND_YEAR);
                    student.registerYearList(courseCatalog.get(1));
                    break;
                case SECOND_YEAR:
                    if (student.passedAllCourseInYear() && student.calculateAVG() > 5.0)
                        student.setStudyYear(YearOfStudy.THIRD_YEAR);
                    student.registerYearList(courseCatalog.get(2));
                    break;
                case THIRD_YEAR:
                    if (student.passedAllCourseInYear() && student.calculateAVG() > 5.0)
                        student.setStudyYear(YearOfStudy.FOURTH_YEAR);
                    student.registerYearList(courseCatalog.get(3));
                    break;
                case FOURTH_YEAR:
                    if (student.passedAllCourseInYear() && student.calculateAVG() > 5.0)
                        student.setStudyYear(YearOfStudy.GRADUATED);
                    student.takenCourses.clear();
                    break;
                default:
                    break;
            }
        }
    }

}
