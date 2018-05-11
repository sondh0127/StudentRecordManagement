package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.model.Student.Gender;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditMajorRepository;
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


public class AddStudentController implements Initializable {

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private RadioButton radioMale;

    @FXML
    private ToggleGroup genderRadioGroup;

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
    private ToggleGroup studentTypeRadioGroup;

    @FXML
    private RadioButton radioCredit;

    @FXML
    private RadioButton radioAnnual;

    @FXML
    private ComboBox<AnnualClass> cbxClass;

    @FXML
    private ComboBox<CreditMajor> cbxMajor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBox();
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

    @FXML
    private void clearForm(ActionEvent event) {
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
    private void submitDetails() {
        long studentID;
        String firstName;
        String lastName;
        Gender gender;
        LocalDate birthdayDate;
        String phone;
        String email;
        String address;
        CreditMajor majorValue = null;
        AnnualClass classValue = null;
        Student newStudent = null;
        try {
            if (txtStudentID.getText().trim().isEmpty()) throw new RuntimeException("Enter student id !");
            else studentID = Long.parseLong((txtStudentID.getText()));
            if (txtFirstName.getText().trim().isEmpty()) throw new RuntimeException("Enter first name !");
            else firstName = txtFirstName.getText();
            if (txtLastName.getText().trim().isEmpty()) throw new RuntimeException("Enter last name !");
            else lastName = txtLastName.getText();
            if (radioFemale.isSelected()) gender = Gender.FEMALE;
            else gender = Gender.MALE;
            if (dtpBirthday.getValue() == null) throw new RuntimeException("Enter birth day !");
            else birthdayDate = dtpBirthday.getValue();
            if (txtPhone.getText().trim().isEmpty()) throw new RuntimeException("Enter phone number !");
            else phone = txtPhone.getText();
            if (txtEmail.getText().trim().isEmpty()) throw new RuntimeException("Enter email !");
            else email = txtEmail.getText();
            if (txtAddress.getText().trim().isEmpty()) throw new RuntimeException("Enter address !");
            else address = txtAddress.getText();
            if (radioCredit.isSelected()) {
                majorValue = cbxMajor.getValue();
                if (majorValue == null) throw new NullPointerException("Select a major for student !");
            } else if (radioAnnual.isSelected()) {
                classValue = cbxClass.getValue();
                if (classValue == null) throw new NullPointerException("Select a class for student !");
            }
            // after checking add student to data
            if (majorValue != null) {
                newStudent = new CreditStudent(studentID, firstName, lastName, gender, birthdayDate, phone, email, address, majorValue);
            } else if (classValue != null) {
                newStudent = new AnnualStudent(studentID, firstName, lastName, gender, birthdayDate, phone, email, address);
                if (!classValue.addStudentToClass((AnnualStudent) newStudent))
                    throw new RuntimeException("Class is full !");
                AnnualClassRepository.getInstance().update(classValue);
            }
            if (newStudent != null) {
                if (StudentRepository.getInstance().save(newStudent)) {
                    // TODO: 11/05/2018 find here for auto create user
//                    UserRepository.getInstance().save(new User(studentID, studentID, UserType.USER));
                    AlertMaker.showNotification("Success", "Student info inserted successfully !", AlertMaker.image_checked);
                } else {
                    AlertMaker.showErrorMessage("Failed!", "Student ID is exist !");
                }
            } else {
                AlertMaker.showErrorMessage("Failed!", "Can't add student!");
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Adding Error", "Student ID must be number only !");
        } catch (NullPointerException e1) {
            AlertMaker.showErrorMessage("Adding Error", e1.getMessage());
        } catch (RuntimeException e2) {
            AlertMaker.showErrorMessage("Adding Error", e2.getMessage());
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }
}