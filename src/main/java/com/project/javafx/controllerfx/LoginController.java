package com.project.javafx.controllerfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.project.javafx.MainApp;
import com.project.javafx.ulti.ViewConstants;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    JFXTextField accountField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXButton signInButton;
    // TODO: 10/03/2018 add gif progress
    @FXML
    ImageView imgProgress;

    private String fakeID = "Admin";
    private String fakePass = "qweasd";
    private String fakeStudentID = "20143798";

    // Reference to the main application
    private MainApp mainApp;
    private Stage loginStage;

    /**
     * Init the controller class.
     * This method is automatically called after fxml file has been loaded
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleValidation();
        accountField.setText(fakeID);
        passwordField.setText(fakePass);
    }
    private double xOffset = 0;
    private double yOffset = 0;
    void loadWindow(String location, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            // adding movable borderless window
            //grab your root here
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            //move around here
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(ViewConstants.APP_ICON));
            stage.initOwner(accountField.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
//            AlertMaker.showErrorMessage(e);
            e.printStackTrace();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) accountField.getScene().getWindow();
        stage.close();
    }

    @FXML
    // TODO: 23/03/2018 improve login stage
    private void handleButtonLogin(ActionEvent actionEvent) {
        if (accountField.getText().equals(fakeID)) {
            if (passwordField.getText().equals(fakePass)) {
                closeStage();
                loadWindow(ViewConstants.ADMIN_DASHBOARD_VIEW, ViewConstants.APP_NAME + " - Admin");
            } else {

            }
        } else {
//            AlertMaker.showNotification("Login Failed!","Your Username or Password is incorrect", AlertMaker.image_cross);
        }

    }

    /**
     * Sample for input validator listener with jfoenix node
     */
    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        accountField.getValidators().add(fieldValidator);
        accountField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                accountField.validate();
            }
        });

        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        fieldValidator2.setMessage("Input required");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        passwordField.getValidators().add(fieldValidator2);
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                passwordField.validate();
            }
        });
    }


}
