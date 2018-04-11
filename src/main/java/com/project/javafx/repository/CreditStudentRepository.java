package com.project.javafx.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;

import java.lang.reflect.Type;
import java.util.Set;

public class CreditStudentRepository extends AbstractRepository<CreditStudent> {

    private static final String path = "src/main/resources/public/CreditStudents.json";
    private static CreditStudentRepository instance = new CreditStudentRepository(path);

    public CreditStudentRepository(String filepath) {
        super(filepath);
    }

    public static CreditStudentRepository getInstance() {
        return instance;
    }

    @Override
    public String converter(CreditStudent element) {
        return String.valueOf(element.getStudentID());
    }

    @Override
    public Type setListType() {
        return new TypeToken<Set<CreditStudent>>() {}.getType();
    }

    @Override
    public void gSonSave() {
        CreditStudentRepository.getInstance().getList().clear();
        for (Student student : StudentRepository.getInstance().getList()) {
            if (student instanceof CreditStudent) {
                CreditStudentRepository.getInstance().addElement((CreditStudent) student);
            }
        }
        super.gSonSave();
    }

    @Override
    public void gSonLoad() {
        super.gSonLoad();
        StudentRepository.getInstance().getList().addAll(CreditStudentRepository.getInstance().getList());
    }

}
