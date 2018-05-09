package com.project.javafx.controllerfx.annual;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAnnualController {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Button btnDefault;

    @FXML
    private TextField txtClassCode;

    @FXML
    private TextField  txtClassName;

    @FXML
    private TextField txtCapacity;

    @FXML
    void handleCapacity(ActionEvent event) {
        txtCapacity.setText("40");
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) {
        String classCode;
        String className;
        int capacity;
        AnnualClass annualClass;
        try {
            if (txtClassCode.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Class Code !");
            else classCode = txtClassCode.getText().toUpperCase();
            if (txtClassName.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Class Name !");
            else className = txtClassName.getText();
            if (txtCapacity.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Capacity !");
            else capacity = Integer.parseInt(txtCapacity.getText());
            annualClass = new AnnualClass(classCode, className, capacity);
            if (AnnualClassRepository.getInstance().save(annualClass)) {
                AlertMaker.showNotification("Success", "Class inserted successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Class Code is exist !");
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Input Error", "Capacity must be number !");
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }
}
