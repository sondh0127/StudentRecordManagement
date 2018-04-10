package com.project.javafx.model.repository;

import com.project.javafx.model.*;
import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRepository<T> {

    private Set<T> dataList;
    private String filepath;

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
        for (T t : dataList) {
            String converted = converter(t);
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
        for (T t : dataList) {
            String converted = converter(t);
            if (converted.equals(element)) {
                deleteElement(t);
                break;
            }
        }

    }

    public T getElement(String element) {
        for (T t : dataList) {
            String converted = converter(t);
            if (converted.equals(element)) {
                return t;
            }
        }
        return null;
    }

    public abstract String converter(T element);

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

}
