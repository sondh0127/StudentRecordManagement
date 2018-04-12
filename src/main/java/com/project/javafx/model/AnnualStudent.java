package com.project.javafx.model;

import java.time.LocalDate;

public class AnnualStudent extends Student{

    private AnnualClass annualClass ;
    private StudyYear studyYear;

    public AnnualStudent(int studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.ANNUAL);
    }

    public AnnualStudent(int studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, AnnualClass annualClass) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.ANNUAL);
        this.annualClass = annualClass;
        this.studyYear = StudyYear.FIRST_YEAR;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void dropCourse(Course course) {

    }

    public void updateStudyYear() {
        // check current list course, if all finish -> update year
    }

    public enum StudyYear {
        FIRST_YEAR("First Year"),
        SECOND_YEAR("Second Year"),
        THIRD_YEAR("Third Year"),
        FOURTH_YEAR("Fourth Year");

        private final String text;

        StudyYear(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public AnnualClass getAnnualClass() {
        return annualClass;
    }

    public void setAnnualClass(AnnualClass annualClass) {
        this.annualClass = annualClass;
    }

    public String getStudyYear() {
        return studyYear.toString();
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }
}
