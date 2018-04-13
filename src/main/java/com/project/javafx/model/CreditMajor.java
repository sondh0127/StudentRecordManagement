package com.project.javafx.model;

public class CreditMajor {

    private final int DEFAULT_MAJOR_CREDITS = 120;
    private final int DEFAULT_MINOR_CREDITS = 10;

    private final String majorCode;
    private final String majorTitle;
    private final int majorCreditsRequired;
    private final int minorCreditsRequired;

    private CoursesCatalog<CreditCourse> majorCatalog;
    private CoursesCatalog<CreditCourse> minorCatalog;

    public CreditMajor(String majorCode, String majorTitle) {
        this.majorCode = majorCode;
        this.majorTitle = majorTitle;
        this.majorCreditsRequired = DEFAULT_MAJOR_CREDITS;
        this.minorCreditsRequired = DEFAULT_MINOR_CREDITS;
    }

    public CreditMajor(String majorCode, String majorTitle, int majorCreditsRequired, int minorCreditsRequired) {
        this.majorCode = majorCode;
        this.majorTitle = majorTitle;
        this.majorCreditsRequired = majorCreditsRequired;
        this.minorCreditsRequired = minorCreditsRequired;
    }

    // main method
    public void createMajorList() {

    }

    // TODO: 13/04/2018  ae add them method nhe


    // getter and setter
    public String getTitleMajor() {
        return majorTitle;
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
        return majorTitle;
    }
}
