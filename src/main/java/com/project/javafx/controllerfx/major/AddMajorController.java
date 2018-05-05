package com.project.javafx.controllerfx.major;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMajorController {
    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField txtMajorCode;

    @FXML
    private TextField txtMajorTitle;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) {
        String majorCode = null;
        String majorTitle = null;
        CreditMajor newMajor = null;
        try {
            if (txtMajorCode.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Major Code !");
            else majorCode = txtMajorCode.getText().toUpperCase();
            if (txtMajorTitle.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Major Title !");
            else majorTitle = txtMajorTitle.getText();
            newMajor = new CreditMajor(majorCode, majorTitle);
            if (CreditMajorRepository.getInstance().save(newMajor)) {
                AlertMaker.showNotification("Success", "Major inserted successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Major is exist !");
            }
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }
}
