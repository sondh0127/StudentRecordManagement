package com.project.javafx.controllerfx.major;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TextField txtMajorCredits;

    @FXML
    private Button btnDefault;

    @FXML
    private TextField txtMinorCredits;

    @FXML
    void handleDefault(ActionEvent event) {
        txtMajorCredits.setText("100");
        txtMinorCredits.setText("20");
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void submitDetails(ActionEvent event) {
        String majorCode = null;
        String majorTitle = null;
        int majorCredits;
        int minorCredits;
        CreditMajor newMajor = null;
        try {
            if (txtMajorCode.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Major Code !");
            else majorCode = txtMajorCode.getText().toUpperCase();
            if (txtMajorTitle.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Major Title !");
            else majorTitle = txtMajorTitle.getText();
            if (txtMajorCredits.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Major Credit !");
            else majorCredits = Integer.parseInt(txtMajorCredits.getText());
            if (txtMajorCredits.getText().trim().isEmpty()) throw new IllegalArgumentException("Enter Minor Credit!");
            else minorCredits = Integer.parseInt(txtMinorCredits.getText());
            newMajor = new CreditMajor(majorCode, majorTitle, majorCredits, minorCredits);
            if (CreditMajorRepository.getInstance().save(newMajor)) {
                AlertMaker.showNotification("Success", "Major inserted successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Major is exist !");
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Input Error", "Credit number must be number !");
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Input Error", e.getMessage());
        }
    }
}
