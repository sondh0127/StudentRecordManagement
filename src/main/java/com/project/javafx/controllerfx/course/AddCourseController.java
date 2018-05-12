package com.project.javafx.controllerfx.course;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCourseController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtCourseScale;

    @FXML
    private RadioButton radioCredit;

    @FXML
    private ToggleGroup courseTypeGroup;

    @FXML
    private RadioButton radioAnnual;

    @FXML
    private TextField txtCreditNum;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void clearForm(ActionEvent event) {
        if (event.getSource().equals(btnClear)) {
            txtCourseCode.clear();
            txtCourseName.clear();
            txtCourseScale.clear();
            txtCreditNum.clear();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) throws IllegalArgumentException {
        String courseCode = txtCourseCode.getText().trim().toUpperCase();
        String courseName = txtCourseName.getText().trim();
        String scaleStr = txtCourseScale.getText().trim();
        String creditNumStr = txtCreditNum.getText().trim();
        int credit = 0;
        double scale = 0;
        Course course = null;
        try {
            if (courseCode.isEmpty()) throw new IllegalArgumentException("Enter course Code !");
            if (courseName.isEmpty()) throw new IllegalArgumentException("Enter course Name !");
            if (scaleStr.isEmpty()) throw new IllegalArgumentException("Enter course scale !");
            if (creditNumStr.isEmpty()) throw new IllegalArgumentException("Enter credit number !");
            if (scaleStr.matches("[0-9 .]+")) {
                scale = Double.parseDouble(scaleStr);
            } else throw new IllegalArgumentException("Scale must be number !");
            if (scale <= 0 || scale >= 1.0) throw new IllegalArgumentException("Scale must be in range (0 - 1.0)");
            if (creditNumStr.matches("[0-9]+"))
                credit = Integer.parseInt(creditNumStr);
            else throw new IllegalArgumentException("Credit must be natural number!");
            if (credit <= 0 || credit >= 100) throw new IllegalArgumentException("Invalid credit number - too large");
            if (radioCredit.isSelected()) {
                course = new CreditCourse(courseCode, courseName, credit, scale);
            } else if (radioAnnual.isSelected()) {
                course = new Course(courseCode, courseName, credit, scale);
            }
            if (course != null) {
                if (CourseRepository.getInstance().save(course)) {
                    AlertMaker.showNotification("Success", "Course inserted successfully !", AlertMaker.image_checked);
                } else {
                    AlertMaker.showErrorMessage("Failed!", "Course Code is exist !");
                }
            }
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }

}
