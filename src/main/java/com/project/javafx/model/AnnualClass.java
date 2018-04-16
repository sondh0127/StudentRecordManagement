package com.project.javafx.model;

import com.project.javafx.model.AnnualStudent.StudyYear;

import java.util.ArrayList;
import java.util.List;

public class AnnualClass {

    @Override
    public String toString() {
        return className;
    }

    private final String classCode;
    private final String className;
    private CoursesCatalog<Course> coursesCatalog1;
    private CoursesCatalog<Course> coursesCatalog2;
    private CoursesCatalog<Course> coursesCatalog3;
    private CoursesCatalog<Course> coursesCatalog4;

    private List<Long> studentList = new ArrayList<>();

    public AnnualClass(String classCode, String className) {
        this.classCode = classCode;
        this.className = className;
    }

    // main method
    public void createCatalog(Course course, StudyYear year) {
        switch (year) {
            case FIRST_YEAR:
                coursesCatalog1.addCourse(course);
                break;
            case SECOND_YEAR:
                coursesCatalog2.addCourse(course);
                break;
            case THIRD_YEAR:
                coursesCatalog3.addCourse(course);
                break;
            case FOURTH_YEAR:
                coursesCatalog4.addCourse(course);
                break;
        }
    }

    // getter and setter
    public String getClassName() {
        return className;
    }

    public CoursesCatalog<Course> getCoursesCatalog(StudyYear year) {
        switch (year) {
            case FIRST_YEAR:
                return coursesCatalog1;
            case SECOND_YEAR:
                return coursesCatalog2;
            case THIRD_YEAR:
                return coursesCatalog3;
            case FOURTH_YEAR:
                return coursesCatalog4;
        }
        return null;
    }

    public void setCoursesCatalog(CoursesCatalog<Course> coursesCatalog, StudyYear year) {
        switch (year) {
            case FIRST_YEAR:
                coursesCatalog = coursesCatalog;
            case SECOND_YEAR:
                coursesCatalog = coursesCatalog;
            case THIRD_YEAR:
                coursesCatalog = coursesCatalog;
            case FOURTH_YEAR:
                coursesCatalog = coursesCatalog;
        }
    }

    /**
     * call when add a class to student.
     * @param student: AnnualStudent
     */
    public void addStudent(AnnualStudent student) {
        if (!studentList.contains(student.getStudentID())) {
            studentList.add(student.getStudentID());
            StudyYear year = student.getStudyYear();
            switch (year) {
                case FIRST_YEAR:
                    addCourseListForStudent(student, coursesCatalog1);
                    break;
                case SECOND_YEAR:
                    addCourseListForStudent(student, coursesCatalog2);
                    break;
                case THIRD_YEAR:
                    addCourseListForStudent(student, coursesCatalog3);
                    break;
                case FOURTH_YEAR:
                    addCourseListForStudent(student, coursesCatalog4);
                    break;
            }
        }
    }

    private void addCourseListForStudent(AnnualStudent student, CoursesCatalog<Course> catalog) {
//        for (Course course : catalog.getCourseDataSet()) {
//            student.registerCourse(course);
//        }
    }

}
