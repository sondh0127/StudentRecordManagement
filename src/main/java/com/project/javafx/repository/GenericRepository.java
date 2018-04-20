package com.project.javafx.repository;

import java.util.Set;

public interface GenericRepository<T, ID> {
        T findById(ID id);
        Set<T> findAll();
        boolean existsById(ID id);
        boolean update(T entity);
        boolean save(T entity);
        boolean delete(T entity);
        boolean deleteByID(ID id);
}