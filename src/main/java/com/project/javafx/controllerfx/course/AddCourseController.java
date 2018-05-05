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
import javafx.scene.input.MouseEvent;
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
        txtCreditNum.setVisible(false);
        courseTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(radioCredit)) {
                txtCreditNum.setVisible(true);
            } else if (newValue.equals(radioAnnual)) {
                txtCreditNum.setVisible(false);
            }
        });
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
    void handleClickCouseType(MouseEvent event) {

    }

    @FXML
    void submitDetails(ActionEvent event) {
        String courseCode = txtCourseCode.getText().trim().toUpperCase();
        String courseName = txtCourseName.getText().trim();
        String scaleStr = txtCourseScale.getText().trim();
        String creditNumStr = txtCreditNum.getText().trim();
        int credit = 0;
        double scale = 0;
        Course course = null;
        try {
            if (courseCode.isEmpty()) throw new IllegalArgumentException("Enter course Code !");
            if (courseCode.contains(" ")) throw new IllegalArgumentException("Course code should not have space !");
            if (courseName.isEmpty()) throw new IllegalArgumentException("Enter course Name !");
            if (scaleStr.isEmpty()) throw new IllegalArgumentException("Enter course scale !");
            else {
                if (scaleStr.matches("[0-9 .]+")) { // TODO: 24/04/2018 should remove for simple
                    scale = Double.parseDouble(scaleStr);
                } else throw new IllegalArgumentException("Scale must be number !");
                if (scale <= 0 || scale >= 1.0) throw new IllegalArgumentException("Scale must be in range (0 - 1.0)");
            }
            if (radioCredit.isSelected()) {
                if (creditNumStr.isEmpty())
                    throw new IllegalArgumentException("Enter the Credit number");
                else {
                    if (creditNumStr.matches("[0-9]+"))
                        credit = Integer.parseInt(creditNumStr);
                    else throw new IllegalArgumentException("Credit must be natural number !");
                    if (credit <= 0 || credit >= 100) throw new IllegalArgumentException("Invalid credit number - too large");
                    course = new CreditCourse(courseCode, courseName, credit, scale);
                }
            } else if (radioAnnual.isSelected()) {
                course = new Course(courseCode, courseName, scale);
            }
            if (course != null) {
                if (CourseRepository.getInstance().save(course)) {
                    AlertMaker.showNotification("Success", "Course inserted successfully !", AlertMaker.image_checked);
                } else {
                    AlertMaker.showErrorMessage("Failed!", "Course Code is exist !");
                }
            }

//        } catch (NumberFormatException e) {
//
//            AlertMaker.showErrorMessage("Input Error", e.getMessage() +  "must be number !");
//            AlertMaker.showErrorMessage("Input Error", "Student ID must be number only !");
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }

        if (radioCredit.isSelected()) {
            CreditCourse creditCourse = new CreditCourse(courseCode, courseName, credit, scale);
        } else if (radioAnnual.isSelected()) {

        }

    }


}
