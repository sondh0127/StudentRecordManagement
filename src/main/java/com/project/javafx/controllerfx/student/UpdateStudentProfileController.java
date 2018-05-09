package com.project.javafx.controllerfx.student;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Student;
import com.project.javafx.model.Student.Gender;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateStudentProfileController implements Initializable {

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnBack;

    @FXML
    private RadioButton radioMale;

    @FXML
    private ToggleGroup genderRadioGroup1;

    @FXML
    private RadioButton radioFemale;

    @FXML
    private DatePicker dtpBirthday;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAddress;

    @FXML
    private Label txtStudentID;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    private Student student;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student = (Student) resources.getObject("updateStudent");
        initField();
    }

    public void initField() {
        txtStudentID.setText(String.valueOf(student.getStudentID()));
        txtFirstName.setText(student.getFirstName());
        txtLastName.setText(student.getLastName());
        if (student.getGender().equals(Gender.FEMALE)) {
            radioFemale.setSelected(true);
//            radioMale.setSelected(false);
//            radioMale.setDisable(true);
        } else if (student.getGender().equals(Gender.MALE)) {
            radioMale.setSelected(true);
//            radioFemale.setSelected(false);
//            radioFemale.setDisable(true);
        }
        dtpBirthday.setValue(student.getBirthday());
        txtPhone.setText(student.getPhone());
        txtEmail.setText(student.getEmail());
        txtAddress.setText(student.getAddress());
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void updateDetails(ActionEvent event) {
        String firstName;
        String lastName;
        Gender gender;
        LocalDate birthdayDate;
        String phone;
        String email;
        String address;
        try {
            if (txtFirstName.getText().trim().isEmpty()) throw new IllegalArgumentException("First name is empty !");
            else firstName = txtFirstName.getText();
            if (txtLastName.getText().trim().isEmpty()) throw new IllegalArgumentException("Last name is empty!");
            else lastName = txtLastName.getText();
            if (radioFemale.isSelected()) gender = Gender.FEMALE;
            else gender = Gender.MALE;
            if (dtpBirthday.getValue() == null) throw new IllegalArgumentException("Birth day is empty!");
            else birthdayDate = dtpBirthday.getValue();
            if (txtPhone.getText().trim().isEmpty()) throw new IllegalArgumentException("Phone number is empty!");
            else phone = txtPhone.getText();
            if (txtEmail.getText().trim().isEmpty()) throw new IllegalArgumentException("Email is empty!");
            else email = txtEmail.getText();
            if (txtAddress.getText().trim().isEmpty()) throw new IllegalArgumentException("Address is empty!");
            else address = txtAddress.getText();
            student.updateStudentProfile(firstName, lastName, gender, birthdayDate,phone, email, address);
            StudentRepository.getInstance().update(student);
            AlertMaker.showNotification("Success", "Student Profile updated successfully !", AlertMaker.image_checked);
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Adding Error", e.getMessage());
        }
    }


}
