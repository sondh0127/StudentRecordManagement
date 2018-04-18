package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.alert.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class AddStudentController implements Initializable {

    private File file;

    @FXML
    private ImageView viewAvatar;

    @FXML
    private JFXButton btnGetImg;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private RadioButton radioMale;

    @FXML
    private ToggleGroup genderRadioGroup;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private JFXDatePicker dtpBirthday;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private ToggleGroup studentTypeRadioGroup;

    @FXML
    private RadioButton radioCredit;

    @FXML
    private RadioButton radioAnnual;

    @FXML
    private JFXComboBox<AnnualClass> cbxClass;

    @FXML
    private JFXComboBox<CreditMajor> cbxMajor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBox();
        System.out.println("Init Add Menu");
    }

    private void initComboBox() {
        cbxClass.getItems().setAll(AnnualClassRepository.getInstance().findAll());
        cbxMajor.getItems().setAll(CreditMajorRepository.getInstance().findAll());
        cbxMajor.setVisible(false);
        studentTypeRadioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            cbxMajor.getSelectionModel().select(null);
            cbxClass.getSelectionModel().select(null);
            if (newValue.equals(radioAnnual)) {
                cbxMajor.setVisible(false);
                cbxClass.setVisible(true);
            }
            if (newValue.equals(radioCredit)) {
                cbxMajor.setVisible(true);
                cbxClass.setVisible(false);
            }
        });

    }

    // TODO: 24/03/2018 need add validation (NumberFormatException for id) + new button for clearform
    @FXML
    void clearForm(ActionEvent event) {
        if (event.getSource().equals(btnClear)) {
            txtStudentID.clear();
            txtFirstName.clear();
            txtLastName.clear();
            dtpBirthday.setValue(null);
            txtPhone.clear();
            txtEmail.clear();
            txtAddress.clear();
            cbxMajor.getSelectionModel().select(null);
            cbxClass.getSelectionModel().select(null);
        }
    }


    @FXML
    void submitDetails() {
        // TODO: 18/04/2018 add diaglog
        int studentID = Integer.parseInt(txtStudentID.getText());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();

        String genderString = "Male";
        if (radioFemale.isSelected()) genderString = "Female";
        LocalDate birthdayDate = dtpBirthday.getValue();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        Student newStudent;
        if (radioCredit.isSelected()) {
            newStudent = new CreditStudent(studentID, firstName, lastName, genderString, birthdayDate, phone, email, address);
            if (cbxMajor.getValue() != null) {
                ((CreditStudent) newStudent).setCreditMajor(cbxMajor.getValue());
                // TODO: 13/04/2018  add student to list of major
            }
        } else {
            newStudent = new AnnualStudent(studentID, firstName, lastName, genderString, birthdayDate, phone, email, address);
            if (cbxClass.getValue() != null) {
                ((AnnualStudent) newStudent).setAnnualClass(cbxClass.getValue());
                // TODO: 13/04/2018  add student to list of major
            }
        }
        if (StudentRepository.getInstance().save(newStudent)) {
            AlertMaker.showNotification("Success", "Student info inserted successfully ", AlertMaker.image_movie_frame);
        } else {
            AlertMaker.showErrorMessage("Failed!", "Student ID is exist");
        }
    }

    @FXML
    void getAvatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        try {
            file = fileChooser.showOpenDialog(txtStudentID.getScene().getWindow());
            Image image = new Image(file.toURI().toString());
            viewAvatar.setImage(image);
        } catch (Exception ex) {
//            AlertMaker.showErrorMessage(ex);
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }
}