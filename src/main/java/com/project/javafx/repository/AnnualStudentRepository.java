package com.project.javafx.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Student;

import java.lang.reflect.Type;
import java.util.Set;

public class AnnualStudentRepository extends AbstractRepository<AnnualStudent> {

    private static final String path = "src/main/resources/public/AnnualStudents.json";
    private static AnnualStudentRepository instance = new AnnualStudentRepository(path);
    public AnnualStudentRepository(String filepath) {
        super(filepath);
    }

    public static AnnualStudentRepository getInstance() {
        return instance;
    }

    @Override
    public String converter(AnnualStudent element) {
        return String.valueOf(element.getStudentID());
    }

    @Override
    public Type setListType() {
        return new TypeToken<Set<AnnualStudent>>() {}.getType();
    }

    @Override
    public void gSonSave() {
        AnnualStudentRepository.getInstance().getList().clear();
        for (Student student : StudentRepository.getInstance().getList()) {
            if (student instanceof AnnualStudent) {
                AnnualStudentRepository.getInstance().addElement((AnnualStudent) student);
            }
        }
        super.gSonSave();

    }

    @Override
    public void gSonLoad() {
        super.gSonLoad();
        StudentRepository.getInstance().getList().addAll(AnnualStudentRepository.getInstance().getList());
    }
}
