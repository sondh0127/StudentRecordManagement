package com.project.javafx.repository;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRepository<T> implements GenericRepository<T> {

    public Set<T> objects = new HashSet<>();
    private String filepath;

    public AbstractRepository(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public T findById(String id) {
        for (T e : objects) {
            String converted = converter(e);
            if (converted.equals(id)) {
                return e;
            }
        }
        return null;
    }

    protected abstract String converter(T e);

    public void setData(Set<T> objects) {
        this.objects = objects;
    }

    @Override
    public Set<T> findAll() {
        return objects;
    }

    // TODO: 12/04/2018 cứ sai sai =))
    @Override
    public boolean update(T entity) {
        String id = converter(entity);
        for (T e : objects) {
            String converted = converter(e);
            if (converted.equals(id)) {
                objects.remove(e);
                objects.add(entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(T entity) {
        boolean isExist = false;
        String newIdentify = converter(entity);
        for (T e : objects) {
            String converted = converter(e);
            if (converted.equals(newIdentify)) {
                isExist = true;
            }
        }
        if (!isExist) {
            objects.add(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(T entity) {
        return objects.remove(entity);
    }

    @Override
    public boolean deleteByID(String id) {
        for (T e : objects) {
            String converted = converter(e);
            if (converted.equals(id)) {
                return delete(e);
            }
        }
        return false;
    }

    public void gSonLoad() {
        System.out.println("Loaded file: " + filepath);
        Gson gson = gsonCreator();
        String fileData;
        try {
            fileData = new String(Files.readAllBytes(Paths.get(filepath)));
            setData(gson.fromJson(fileData, setToken()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gSonSave() {
        System.out.println("Saved file: " + filepath);
        Gson gson = gsonCreator();
        String json = gson.toJson(objects, setToken());
        try {
            OutputStream stream = new FileOutputStream(new File(filepath));
            stream.write(json.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract Gson gsonCreator();

    protected abstract Type setToken();

}
