package com.project.javafx.controllerfx.register;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private JFXTextField studentIdField;

    @FXML
    private JFXTextField courseIdField;

    @FXML
    private JFXButton filterButton;

    @FXML
    private TableView<?> registerTableView;

    @FXML
    private TableColumn<?, ?> studentID;

    @FXML
    private TableColumn<?, ?> courseCode;

    @FXML
    private TableColumn<?, ?> midtermScore;

    @FXML
    private TableColumn<?, ?> finalScore;

    @FXML
    private TableColumn<?, ?> totalScore;

    @FXML
    private Label lbl_fullname;

    @FXML
    private VBox detailsBox;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblBirdthday;

    @FXML
    private Label lblAddition;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    void addRegister(ActionEvent event) {

    }

    @FXML
    void handleFilter(ActionEvent event) {

    }

    public void updateRegister(ActionEvent actionEvent) {

    }


    @FXML
    void refreshTable(ActionEvent event) {

    }

    @FXML
    void removeRegister(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
