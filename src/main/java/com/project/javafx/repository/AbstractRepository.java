package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.javafx.model.*;
import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRepository<T> {

    private Set<T> dataList;
    private String filepath;
    public AbstractRepository() {
        this.dataList = new HashSet<>();
    }

    public AbstractRepository(String filepath) {
        this.dataList = new HashSet<>();
        this.filepath = filepath;
    }

    public Set<T> getList() {
        return dataList;
    }

    public void createList(Set<T> dataList) {
        this.dataList = dataList;
    }

    public boolean addElement(T newElement) {
        boolean isExist = false;
        String newIdentify = converter(newElement);
        for (T T : dataList) {
            String converted = converter(T);
            if (converted.equals(newIdentify)) {
                isExist = true;
            }
        }
        if (!isExist) {
            dataList.add(newElement);
            return true;
        }
        return false;
    }

    public void deleteElement(T removeElement) {
        dataList.remove(removeElement);
    }

    public void deleteElement(String element) {
        for (T T : dataList) {
            String converted = converter(T);
            if (converted.equals(element)) {
                deleteElement(T);
                break;
            }
        }

    }

    public T getElement(String element) {
        for (T T : dataList) {
            String converted = converter(T);
            if (converted.equals(element)) {
                return T;
            }
        }
        return null;
    }

    public abstract String converter(T element);

    public abstract Type setListType();

    public void xStreamSave() {
        XStream stream = new XStream();
        aliasXStream(stream);
        String xml = stream.toXML(dataList);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
            byte[] bytes = xml.getBytes("UTF-8");
            fos.write(bytes);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(fos!=null) {
                try{
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void xStreamLoad() {
        XStream stream = new XStream();
        aliasXStream(stream);
        try{
            FileInputStream xmlFile = new FileInputStream(filepath);
            dataList = (Set<T>) stream.fromXML(xmlFile);
        }catch(Exception e){
            System.err.println("Error in XML Read: " + e.getMessage());
        }
    }

    private void aliasXStream(XStream stream) {
        stream.alias("creditStudent", CreditStudent.class);
        stream.alias("creditCourse", CreditCourse.class);
        stream.alias("creditMajor", CreditMajor.class);

        stream.alias("annualStudent", AnnualStudent.class);
        stream.alias("annualClass", AnnualClass.class);
        stream.alias("annualCourse", AnnualCourse.class);

    }

    public void gSonSave() {
        // Get Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // create JSON String from Object
        String json = gson.toJson(this.getList());
//        System.out.print(json);
        try {
            OutputStream stream = new FileOutputStream(new File(filepath));
            stream.write(json.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gSonLoad() {
        // Get Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // read JSON file data as String
        String fileData;
        try {
            fileData = new String(Files.readAllBytes(Paths.get(filepath)));
            // parse json string to object\
            this.createList(gson.fromJson(fileData, setListType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
