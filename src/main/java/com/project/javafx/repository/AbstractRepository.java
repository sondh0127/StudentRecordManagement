package com.project.javafx.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static com.project.javafx.ulti.mongoDBUtil.MongoUtils.DB_NAME;

public abstract class AbstractRepository<T, ID> implements GenericRepository<T, ID> {

    private final Class<T> clazz;
    private String collectionName;

//    public Set<T> objects = new HashSet<>();
    private String filepath;

    public AbstractRepository(Class<T> clazz, String filepath, String collectionName) {
        this.clazz = clazz;
        this.filepath = filepath;
        this.collectionName = collectionName;
    }

    private Set<T> getObjectCollection(){
        MongoCollection<Document> collection = MongoUtils.mongoLoadCollectionTo(DB_NAME, collectionName);
        Set<T> objects = new HashSet<>();
        Gson gson = gsonCreator();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            T obj = gson.fromJson(doc.toJson(), clazz);
            objects.add(obj);
        }
        cursor.close();
        return objects;
    }

    private void updateObject(T obj, T newObj) {
        MongoCollection<Document> collection = MongoUtils.mongoLoadCollectionTo(DB_NAME, collectionName);
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);

        String json2 = gson.toJson(newObj);
        Document doc2 = gson.fromJson(json2, Document.class);

        collection.updateOne(doc, doc2);
    }

    private void insertObject(T obj) {
        MongoCollection<Document> collection = MongoUtils.mongoLoadCollectionTo(DB_NAME, collectionName);
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);
        collection.insertOne(doc);
    }

    private void deleteObject(T obj) {
        MongoCollection<Document> collection = MongoUtils.mongoLoadCollectionTo(DB_NAME, collectionName);
        Gson gson = gsonCreator();
        String json = gson.toJson(obj);
        Document doc = gson.fromJson(json, Document.class);
        collection.deleteOne(doc);
    }

    protected abstract ID getID(T e);

    protected abstract Gson gsonCreator();

    protected abstract Type setToken();

    // TODO: 21/04/2018 later
    public void addAll(Set<T> objects) {
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
    public boolean existsById(ID id) {
        for (T obj : getObjectCollection()) {
            ID objID = getID(obj);
            if (objID.equals(id)) return true;
        }
        return false;
    }

    @Override
    public Set<T> findAll() {
        return getObjectCollection();
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
            return true;
        }
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

    @Override
    public boolean delete(T entity) {
        deleteObject(entity);
        return getObjectCollection().remove(entity);
    }

//    public void gSonLoad() {
//        System.out.println("Loaded file: " + filepath);
//        Gson gson = gsonCreator();
//        String fileData;
//        try {
//            fileData = new String(Files.readAllBytes(Paths.get(filepath)));
//            addAll(gson.fromJson(fileData, setToken()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void gSonSave() {
//        System.out.println("Saved file: " + filepath);
//        Gson gson = gsonCreator();
//        String json = gson.toJson(getObjectCollection(), setToken());
//        try {
//            OutputStream stream = new FileOutputStream(new File(filepath));
//            stream.write(json.getBytes());
//            stream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void mongoDBSave() {
//        MongoCollection coll = MongoUtils.mongoLoadCollectionTo(MongoUtils.DB_NAME, "Majors");
//        Gson gson = gsonCreator();
//        for (T obj : getObjectCollection()) {
//            String json = gson.toJson(obj);
//            Document doc = gson.fromJson(json, Document.class);
//            coll.insertOne(doc);
//        }
//    }

//    protected void mongoDBLoad(){
//        MongoCollection coll = MongoUtils.mongoLoadCollectionTo(MongoUtils.DB_NAME, "Majors");
//        MongoCursor<Document> cursor = coll.find().iterator();
//        Gson gson = gsonCreator();
//        try {
//            while (cursor.hasNext()) {
//                Document doc = cursor.next();
//                T obj = gson.fromJson(doc.toJson(), clazz);
//                objects.add(obj);
//            }
//        } finally {
//            cursor.close();
//        }
//    }
}
