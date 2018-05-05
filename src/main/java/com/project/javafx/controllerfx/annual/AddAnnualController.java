package com.project.javafx.controllerfx.annual;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddAnnualController {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField txtClassCode;

    @FXML
    private JFXTextField txtClassName;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) {
        String classCode = null;
        String className = null;
        AnnualClass annualClass = null;
        try {
            if (txtClassCode.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Class Code !");
            else classCode = txtClassCode.getText().toUpperCase();
            if (txtClassName.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Class Name !");
            else className = txtClassName.getText();
            annualClass = new AnnualClass(classCode, className);
            if (AnnualClassRepository.getInstance().save(annualClass)) {
                AlertMaker.showNotification("Success", "Class inserted successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Class is exist !");
            }
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }
}
