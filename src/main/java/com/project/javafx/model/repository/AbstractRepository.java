package com.project.javafx.model.repository;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRepository<T> {

    private Type DATA_TYPE = this.setDATA_TYPE();
    private Set<T> unitDataList;
    private String filepath;

    public AbstractRepository() {
        this.unitDataList = new HashSet<>();
    }

    public Set<T> getList() {
        return unitDataList;
    }

    public void createList(Set<T> unitDataList) {
        this.unitDataList = unitDataList;
    }

    public boolean addUnit(T newElement) {
        boolean isExist = false;
        String newIdentify = converter(newElement);
        for (T t : unitDataList) {
            String converted = converter(t);
            if (converted.equals(newIdentify)) {
                isExist = true;
            }
        }
        if (!isExist) {
            unitDataList.add(newElement);
            return true;
        }
        return false;
    }

    public void deleteUnit(T removeElement) {
        unitDataList.remove(removeElement);
    }

    public void deleteUnit(String element) {
        for (T t : unitDataList) {
            String converted = converter(t);
            if (converted.equals(element)) {
                deleteUnit(t);
                break;
            }
        }

    }

    public T getUnit(String element) {
        for (T t : unitDataList) {
            String converted = converter(t);
            if (converted.equals(element)) {
                return t;
            }
        }
        return null;
    }

    public abstract String converter(T element);

    public void saveDataListToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(unitDataList);
        System.out.println(json);
        //2. Convert object to JSON string and save into a file directly
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(unitDataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readDataListFromFile() {

        Gson gson = new Gson();

        try (Reader reader = new FileReader(filepath)) {

            // Convert JSON to Java Object
            unitDataList = gson.fromJson(reader,this.DATA_TYPE);

            // Convert JSON to JsonElement, and later to String
            /*JsonElement json = gson.fromJson(reader, JsonElement.class);
            String jsonInString = gson.toJson(json);
            System.out.println(jsonInString);*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public abstract Type setDATA_TYPE();

}
