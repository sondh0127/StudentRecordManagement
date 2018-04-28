package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.validation.RequiredFieldValidator;
import com.project.javafx.model.*;
import com.project.javafx.model.Student.Gender;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.repository.UserRepository;
import com.project.javafx.ulti.AlertMaker;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
//        initValidation();
    }

    private void initValidation() {
        initValidationForField(txtStudentID);
        initValidationForField(txtFirstName);
        initValidationForField(txtLastName);
        initValidationForField(txtPhone);
        initValidationForField(txtAddress);
        initValidationForField(txtEmail);
    }

    private void initValidationForField(TextField node) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size("1em")
                .styleClass("error")
                .build());
//        node.getValidators().add(validator);
//        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {
//                node.validate();
//            }
//        });
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
        // TODO: 18/04/2018 make it simple LUL
        long studentID = 0;
        String firstName = null;
        String lastName = null;
        Gender gender;
        LocalDate birthdayDate = null;
        String phone = null;
        String email = null;
        String address = null;
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
                // TODO: 13/04/2018  add student to list of major
            } else if (classValue != null) {
                newStudent = new AnnualStudent(studentID, firstName, lastName, gender, birthdayDate, phone, email, address, classValue);
                // TODO: 13/04/2018  add student to list of class
            }
            if (newStudent != null) {
                if (StudentRepository.getInstance().save(newStudent)) {
                    UserRepository.getInstance().save(new User(studentID, studentID, UserType.USER));
                    AlertMaker.showNotification("Success", "Student info inserted successfully !", AlertMaker.image_checked);
                } else {
                    AlertMaker.showErrorMessage("Failed!", "Student ID is exist !");
                }
            } else {
                AlertMaker.showErrorMessage("Failed!", "Can't add student!");
            }
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Input Error", "Student ID must be number only !");
        } catch (NullPointerException e1) {
            AlertMaker.showErrorMessage("Input Error", e1.getMessage());
        } catch (RuntimeException e2) {
            AlertMaker.showErrorMessage("Input Error", e2.getMessage());
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