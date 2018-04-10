package com.project.javafx.model;

public class CreditMajor {

    private final int DEFAULT_MAJOR_CREDITS = 120;
    private final int DEFAULT_MINOR_CREDITS = 10;

    private final String titleMajor;
    private final int majorCreditsRequired;
    private final int minorCreditsRequired;

    private CoursesCatalog<CreditCourse> majorCatalog;
    private CoursesCatalog<CreditCourse> minorCatalog;

    public CreditMajor(String titleMajor) {
        this.titleMajor = titleMajor;
        this.majorCreditsRequired = DEFAULT_MAJOR_CREDITS;
        this.minorCreditsRequired = DEFAULT_MINOR_CREDITS;
    }

    public CreditMajor(String titleMajor, int majorCreditsRequired, int minorCreditsRequired) {
        this.titleMajor = titleMajor;
        this.majorCreditsRequired = majorCreditsRequired;
        this.minorCreditsRequired = minorCreditsRequired;
    }

    public void createMajorList() {

    }

    public String getTitleMajor() {
        return titleMajor;
    }

    public int getMajorCreditsRequired() {
        return majorCreditsRequired;
    }

    public int getMinorCreditsRequired() {
        return minorCreditsRequired;
    }

    public CoursesCatalog<CreditCourse> getMajorCatalog() {
        return majorCatalog;
    }

    public void setMajorCatalog(CoursesCatalog<CreditCourse> majorCatalog) {
        this.majorCatalog = majorCatalog;
    }

    public CoursesCatalog<CreditCourse> getMinorCatalog() {
        return minorCatalog;
    }

    public void setMinorCatalog(CoursesCatalog<CreditCourse> minorCatalog) {
        this.minorCatalog = minorCatalog;
    }

    @Override
    public String toString() {
        return titleMajor;
    }
}
