package com.project.javafx.ulti;

import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;

public class RepositoryController {
    public static void saveData() {
        StudentRepository.getInstance().gSonSave();
        CourseRepository.getInstance().gSonSave();
        AnnualClassRepository.getInstance().gSonSave();
        CreditMajorRepository.getInstance().gSonSave();
    }
}
