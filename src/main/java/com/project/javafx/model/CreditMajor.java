package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditMajor {

    private final String majorCode;
    private final String majorTitle;
    private final int majorCreditsRequired;
    private final int minorCreditsRequired;

    private List<CreditCourse> majorCatalog = new ArrayList<>();
    private List<CreditCourse> minorCatalog = new ArrayList<>();

    public CreditMajor(String majorCode, String majorTitle) {
        this.majorCode = majorCode;
        this.majorTitle = majorTitle;
        int DEFAULT_MAJOR_CREDITS = 120;
        int DEFAULT_MINOR_CREDITS = 10;
        this.majorCreditsRequired = DEFAULT_MAJOR_CREDITS;
        this.minorCreditsRequired = DEFAULT_MINOR_CREDITS;
    }

    public CreditMajor(String majorCode, String majorTitle, int majorCreditsRequired, int minorCreditsRequired) {
        this.majorCode = majorCode;
        this.majorTitle = majorTitle;
        this.majorCreditsRequired = majorCreditsRequired;
        this.minorCreditsRequired = minorCreditsRequired;
    }

    // GETTER AND SETTER

    public String getMajorCode() {
        return majorCode;
    }

    public String getMajorTitle() {
        return majorTitle;
    }

    public int getMajorCreditsRequired() {
        return majorCreditsRequired;
    }

    public int getMinorCreditsRequired() {
        return minorCreditsRequired;
    }

    public List<CreditCourse> getMajorCatalog() {
        return majorCatalog;
    }

    public List<CreditCourse> getMinorCatalog() {
        return minorCatalog;
    }

    public void setMajorCatalog(List<CreditCourse> majorCatalog) {
        this.majorCatalog = majorCatalog;
    }

    public void setMinorCatalog(List<CreditCourse> minorCatalog) {
        this.minorCatalog = minorCatalog;
    }

    @Override
    public String toString() {
        return majorTitle;
    }
}
