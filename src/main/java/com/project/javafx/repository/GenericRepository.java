package com.project.javafx.repository;

import java.util.Set;

public interface GenericRepository<T> {
        public T findById(String id);
        public Set<T> findAll();
        public boolean update(T entity);
        public boolean save(T entity);
        public boolean delete(T entity);
        public boolean deleteByID(String id);
}