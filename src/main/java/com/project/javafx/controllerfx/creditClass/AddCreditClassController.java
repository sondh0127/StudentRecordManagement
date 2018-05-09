package com.project.javafx.controllerfx.creditClass;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.CreditClass;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCreditClassController implements Initializable {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private Button btnDefault;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField txtClassCode;

    @FXML
    private TextField txtCapacity;

    @FXML
    private Label txtCourseCode;

    @FXML
    private Label txtCourseName;

    private CreditCourse creditCourse;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) {
        String classCode = null;
        int capacity;
        CreditClass creditClass = null;
        try {
            if (txtClassCode.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Class Code !");
            else classCode = txtClassCode.getText().toUpperCase();
            if (txtCapacity.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Capacity !");
            else capacity = Integer.parseInt(txtCapacity.getText());
            creditClass = new CreditClass(classCode, creditCourse, capacity);
            if (CreditClassRepository.getInstance().save(creditClass)) {
                AlertMaker.showNotification("Success", "Credit class inserted successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Class Code is exist !");
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Input Error", "Capacity must be number !");
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }

    @FXML
    void handleCapacity(ActionEvent event) {
        txtCapacity.setText("40");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.creditCourse = (CreditCourse) resources.getObject("creditCourse");
        txtCourseCode.setText(creditCourse.getCourseCode());
        txtCourseName.setText(creditCourse.getCourseName());

    }
}
