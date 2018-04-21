package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;

import static com.project.javafx.ulti.mongoDBUtil.MongoUtils.DB_NAME;

public abstract class AbstractRepository<T, ID> implements GenericRepository<T, ID> {

    private final Class<T> clazz;
    private String collectionName;

    public AbstractRepository(Class<T> clazz, String collectionName) {
        this.clazz = clazz;
        this.collectionName = collectionName;
    }

    protected Gson gsonCreator() {
        return new GsonBuilder().
                setPrettyPrinting().
                create();
    }

    protected MongoCollection<Document> getCollection() {
        return MongoUtils.mongoLoadCollectionTo(DB_NAME, collectionName);
    }

    protected abstract ID getID(T e);

    public Set<T> getObjectCollection(Document query, Class<? extends T> aClass) {
        Set<T> objects = new HashSet<>();
        FindIterable<Document> cursor = getCollection().find(query);
        for (Document doc : cursor) {
            T o = gsonCreator().fromJson(doc.toJson(), aClass);
            objects.add(o);
        }
        return objects;
    }

    protected Set<T> getObjectCollection() {
        Document query = new Document();
        return getObjectCollection(query, clazz);
    }

    @Override
    public T findById(ID id) {
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) return obj;
        }
        return null;
    }

    @Override
    public Set<T> findAll() {
        return getObjectCollection();
    }

    @Override
    public boolean existsById(ID id) {
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) return true;
        }
        return false;
    }

    private void updateObject(T obj, T newObj) {
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);

        String json2 = gson.toJson(newObj);
        Document doc2 = gson.fromJson(json2, Document.class);

        getCollection().replaceOne(doc, doc2);
    }

    @Override
    public boolean update(T entity) {
        ID id = getID(entity);
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) {
                updateObject(obj, entity);
                return true;
            }
        }
        return false;
    }

    private void insertObject(T obj) {
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);
        getCollection().insertOne(doc);
    }

    @Override
    public boolean save(T entity) {
        boolean isExist = false;
        ID id = getID(entity);
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) {
                isExist = true;
            }
        }
        if (!isExist) {
            insertObject(entity);
            System.out.println("Saved successfully");
            return true;

        }
        System.out.println("Saved failed");
        return false;
    }

    private void deleteObject(T obj) {
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);
        getCollection().deleteOne(doc);
    }

    @Override
    public boolean delete(T entity) {
        if (existsById(getID(entity))) {
            deleteObject(entity);
            System.out.println("Delete successfully");
            return true;
        }
        System.out.println("Saved failed");
        return false;
    }

    @Override
    public boolean deleteByID(ID id) {
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) {
                return delete(obj);
            }
        }
        return false;
    }
}
