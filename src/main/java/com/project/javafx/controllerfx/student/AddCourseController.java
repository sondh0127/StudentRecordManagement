package com.project.javafx.controllerfx.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCourseController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField txtCourseCode;

    @FXML
    private JFXTextField txtCourseName;

    @FXML
    private JFXTextField txtCourseScale;

    @FXML
    private RadioButton radioCredit;

    @FXML
    private ToggleGroup studentTypeRadioGroup;

    @FXML
    private RadioButton radioAnnual;

    @FXML
    private JFXTextField txtCreditNum;


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
    void handleClickCouseType(MouseEvent event) {

    }

    @FXML
    void submitDetails(ActionEvent event) {
        String courseCodeStr = txtCourseCode.getText().trim();
        String courseNameStr = txtCourseName.getText().trim();
        String scaleStr = txtCourseScale.getText().trim();
        String creditNumStr = txtCreditNum.getText().trim();
        int credit = 0;
        double scale = 0;
        try {
            if (courseCodeStr.isEmpty()) throw new RuntimeException("Enter course Code !");
            if (courseNameStr.isEmpty()) throw new RuntimeException("Enter course Name !");
            if (scaleStr.isEmpty()) throw new RuntimeException("Enter course scale !");
            else scale = Double.parseDouble(scaleStr);
            if (radioCredit.isSelected()) {
                if (creditNumStr.isEmpty())
                    throw new RuntimeException("Enter the Credit number");
                else {
                    credit = Integer.parseInt(creditNumStr);
                }
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Input Error", "Student ID must be number only !");
//            AlertMaker.showErrorMessage("Input Error", "Student ID must be number only !");
        } catch (RuntimeException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }

        if (radioCredit.isSelected()) {
            CreditCourse creditCourse = new CreditCourse(courseCodeStr, courseNameStr, credit, scale);
        } else if (radioAnnual.isSelected()) {

        }

    }


}
