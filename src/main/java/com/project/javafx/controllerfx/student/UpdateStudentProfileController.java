package com.project.javafx.controllerfx.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.*;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStudentProfileController implements Initializable {

    private Student student;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnBack;

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
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private RadioButton radioCredit;

    @FXML
    private ToggleGroup studentTypeRadioGroup;

    @FXML
    private RadioButton radioAnnual;

    @FXML
    private JFXComboBox<AnnualClass> cbxClass;

    @FXML
    private JFXComboBox<CreditMajor> cbxMajor;

    @FXML
    private JFXDatePicker dtpBirthday;

    @FXML
    private JFXButton btnGetImg;

    @FXML
    private ImageView viewAvatar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StudentsController.setUpdateStudentProfileController(this);
    }

    public void initField(Student student) {
        this.student = student;
        txtStudentID.setText(String.valueOf(student.getStudentID()));
        txtFirstName.setText(student.getFirstName());
        txtLastName.setText(student.getLastName());
        if ("Female".equals(student.getGender())) {
            radioFemale.setSelected(true);
            radioMale.setSelected(false);
            radioMale.setDisable(true);
        } else if ("Male".equals(student.getGender())) {
            radioMale.setSelected(true);
            radioFemale.setSelected(false);
            radioFemale.setDisable(true);
        }
        dtpBirthday.setValue(student.getBirthday());        // TODO: 19/04/2018 bug exception :((
        txtPhone.setText(student.getPhone());
        txtEmail.setText(student.getEmail());
        txtAddress.setText(student.getAddress());

        // TODO: 19/04/2018 fix complicated
        cbxClass.setVisible(false);
        cbxMajor.setVisible(false);
        if (student instanceof CreditStudent) {
            radioCredit.setSelected(true);
            radioAnnual.setSelected(false);
            radioAnnual.setDisable(true);
            cbxMajor.setVisible(true);
            cbxMajor.getItems().add(((CreditStudent) student).getCreditMajor());
            cbxMajor.getSelectionModel().selectFirst();
        } else if (student instanceof AnnualStudent) {
            radioAnnual.setSelected(true);
            radioCredit.setSelected(false);
            radioCredit.setDisable(true);
            cbxClass.setVisible(true);
            cbxClass.getItems().add(((AnnualStudent) student).getAnnualClass());
            cbxClass.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void updateDetails(ActionEvent event) {
        String phoneText = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        student.updateStudentProfile(phoneText, email, address);
        StudentRepository.getInstance().gSonSave();
        AlertMaker.showNotification("Success", "Student Profile updated successfully !", AlertMaker.image_checked);

    }


}
