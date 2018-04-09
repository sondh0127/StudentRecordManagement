package com.project.javafx.model;

import java.util.HashSet;
import java.util.Set;

public class CoursesCatalog<T extends Course> {

    private Set<T> courseDataSet;

    public CoursesCatalog() {
        this.courseDataSet = new HashSet<>();
    }

    public void addCourse(T t) {
        this.courseDataSet.add(t);
    }

    public Set<T> getCourseDataSet() {
        return courseDataSet;
    }

    public void setCourseDataSet(Set<T> courseDataSet) {
        this.courseDataSet = courseDataSet;
    }

    public void deleteCourse(T t) {
        courseDataSet.remove(t);
    }

    public void deleteCourse(String courseCode) {
        for (T t : courseDataSet) {
            if (t.getCourseCode().equals(courseCode)) {
                deleteCourse(t);
                break;
            }
        }
    }

    public T getCourse(String courseCode) {
        for (T t : courseDataSet) {
            if (t.getCourseCode().equals(courseCode)) {
                return t;
            }
        }
        return null;
    }

}
