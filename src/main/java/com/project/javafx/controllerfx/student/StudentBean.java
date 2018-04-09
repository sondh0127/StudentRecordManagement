package com.project.javafx.controllerfx.student;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StudentBean {

    private int studentID;
    private String firstName;
    private String lastName;

    private final PropertyChangeSupport propertySupport;

    public StudentBean(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.propertySupport = new PropertyChangeSupport(this);
    }

    public StudentBean() {
        this(0,"","");
    }

    public void setStudentID(int studentID) {
        int oldstudentID = this.studentID;
        this.studentID = studentID;
        propertySupport.firePropertyChange("studentID", oldstudentID, studentID);
    }

    public void setFirstName(String firstName) {
        String oldfirstName = this.firstName;
        this.firstName = firstName;
        propertySupport.firePropertyChange("firstName", oldfirstName, firstName);

    }

    public void setLastName(String lastName) {
        String oldLastName = this.lastName;
        this.lastName = lastName;
        propertySupport.firePropertyChange("lastName", oldLastName, lastName);
    }

    public int getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
}
