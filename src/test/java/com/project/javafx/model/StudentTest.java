package com.project.javafx.model;

import com.project.javafx.ulti.DateUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentTest {

    @Test
    public void StudentTest() {
        AnnualClass annualClass = new AnnualClass("asdasd", "dasdasd");
        Student student = new AnnualStudent(345435, "asdasd", "asdasd", Student.Gender.MALE, DateUtil.parse("27/10/1996"),"34543543" , "4353454", "234542342", annualClass);
        assertEquals("asdasd", student.getFirstName());
        CreditMajor major = new CreditMajor("34534", "asdasd");
        Student student1 = new CreditStudent(53454, "serwfd", "asdasd", Student.Gender.FEMALE, DateUtil.parse("27/10/1996"), "435345", "34535", "3453454", major);

        assertEquals("serwfd", student1.getFirstName());

        CreditCourse course = new CreditCourse("4534", "5464564", 3, 0.7);
        major.getMajorCatalog().add((CreditCourse) course);

        ((CreditStudent) student1).registerCourse(course);

        assertEquals(student1.takenCourses.size(), 1);
        assertTrue(student1.takenCourses.containsKey(course));

    }

}