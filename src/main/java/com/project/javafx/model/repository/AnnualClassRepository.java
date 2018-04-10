package com.project.javafx.model.repository;

import com.project.javafx.model.AnnualClass;

public class AnnualClassRepository extends AbstractRepository<AnnualClass> {

    private static final String path = "src/main/resources/public/class.xml";
    private static AnnualClassRepository instance = new AnnualClassRepository(path);

    public AnnualClassRepository(String filepath) {
        super(filepath);
    }

    @Override
    public String converter(AnnualClass element) {
        return element.getClassName();
    }

    public static AnnualClassRepository getInstance() {
        return instance;
    }
}
