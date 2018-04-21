package com.project.javafx.controllerfx;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnCourse;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnExit;

    @FXML
    private StackPane holderPane;

    @FXML
    private JFXButton btn_minimize;

    @FXML
    private JFXButton btn_maximize;

    @FXML
    private JFXButton btn_close;

    @FXML
    private JFXButton btn_option;

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void logOff(ActionEvent event) {

    }

    @FXML
    void maximize(MouseEvent event) {

    }

    @FXML
    void minimize(MouseEvent event) {

    }

    @FXML
    void openCourse(ActionEvent event) {

    }

    @FXML
    void openHome(ActionEvent event) {

    }

    @FXML
    void openRegister(ActionEvent event) {

    }

    @FXML
    void openSetting(ActionEvent event) {

    }

    @FXML
    void openStudent(ActionEvent event) {

    }
}
