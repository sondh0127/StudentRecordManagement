package com.project.javafx.model;

import com.project.javafx.repository.GenericRepository;

import java.util.HashSet;
import java.util.Set;

public class CoursesCatalog<T extends Course> implements GenericRepository<T> {

    private Set<T> courseDataSet = new HashSet<>();

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

    @Override
    public T findById(String id) {
        return null;
    }

    @Override
    public Set<T> findAll() {
        return null;
    }

    @Override
    public boolean update(T entity) {
        return false;
    }

    @Override
    public boolean save(T entity) {
        return false;
    }

    @Override
    public boolean delete(T entity) {
        return false;
    }

    @Override
    public boolean deleteByID(String id) {
        return false;
    }
}
