package com.project.javafx.controllerfx;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.project.javafx.model.User;
import com.project.javafx.repository.UserRepository;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.ViewConstants;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // TODO: 10/03/2018 add gif progress
    @FXML
    ImageView imgProgress;
    @FXML
    private JFXTextField txtAdminUser;

    @FXML
    private JFXPasswordField txtAdminPass;

    @FXML
    private JFXTextField txtStudentUser;

    @FXML
    private JFXPasswordField txtStudentPass;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab tabAdmin;

    @FXML
    private Tab tabStudent;


    private String fakeID = "admin";
    private String fakePass = "adminpass";

    // Reference to the main application
//    private MainApp mainApp;
//    private Stage loginStage;

    /**
     * Init the controller class.
     * This method is automatically called after fxml file has been loaded
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleValidation(txtAdminUser);
        handleValidation(txtStudentUser);
        handleValidation(txtAdminPass);
        handleValidation(txtStudentPass);

        // create hotkey for login button
//        initLoginButton(); // replace by action one password field
        txtAdminUser.setText(fakeID);
        txtAdminPass.setText(fakePass);
        txtStudentUser.setText("student001");
        txtStudentPass.setText("student001");
    }

    void loadWindow(String location, String title) {
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource(location), new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.equals("stage"))
                        return stage;
                    if (key.equals("user")) {
                        return txtStudentUser.getText();
                    }
                    return null;
                }

                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }
            });


            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(ViewConstants.APP_ICON));
            stage.initOwner(txtAdminUser.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            AlertMaker.showErrorMessage(e);
            e.printStackTrace();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) txtAdminUser.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLoginAction(ActionEvent actionEvent) {
        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
        if (selectedItem.equals(tabAdmin)) {
            String username = txtAdminUser.getText();
            String password = txtAdminPass.getText();
            try {
                if (isLogin(username, password)) {
                    closeStage();
                    loadWindow(ViewConstants.ADMIN_DASHBOARD_VIEW, ViewConstants.APP_NAME + " - Admin");
                }
            } catch (IllegalArgumentException e) {
                AlertMaker.showNotification("Login Failed!", e.getMessage(), AlertMaker.image_cross);
            }
        } else if (selectedItem.equals(tabStudent)) {
            String username = txtStudentUser.getText();
            String password = txtStudentPass.getText();
            try {
                if (username.equals("")) throw new IllegalArgumentException("Username required");
                if (password.equals("")) throw new IllegalArgumentException("Password required");
                if (isLogin(username, password)) {
                    closeStage();
                    loadWindow(ViewConstants.USER_DASHBOARD_VIEW, ViewConstants.APP_NAME + " - " + username);
                }
            } catch (IllegalArgumentException e) {
                AlertMaker.showNotification("Login Failed!", e.getMessage(), AlertMaker.image_cross);
            }
        }

    }

    private boolean isLogin(String username, String password) {
        User user = UserRepository.getInstance().findById(username);
        if (user == null) throw new IllegalArgumentException("User not found");
        else {
            if (user.getPassword().equals(password))
                return true;
            else
                throw new IllegalArgumentException("Incorrect Password !");
        }
    }

    /**
     * Sample for input validator listener with jfoenix node
     */
    private void handleValidation(JFXTextField node) {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        node.getValidators().add(fieldValidator);
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                node.validate();
            }
        });
    }

    private void handleValidation(JFXPasswordField node) {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        node.getValidators().add(fieldValidator);
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                node.validate();
            }
        });
    }


}
