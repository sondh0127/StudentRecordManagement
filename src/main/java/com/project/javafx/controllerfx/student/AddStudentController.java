package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.CreditMajor;
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
import java.util.ResourceBundle;


public class AddStudentController implements Initializable {
    private File file;

    @FXML
    private ImageView avatar;

    @FXML
    private JFXButton btnGetImg;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField studentID;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private RadioButton male;

    @FXML
    private ToggleGroup genderRadio;

    @FXML
    private RadioButton female;

    @FXML
    private JFXDatePicker birthday;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField address;

    @FXML
    private ToggleGroup stypeRadio;

    @FXML
    private RadioButton credit;

    @FXML
    private RadioButton yearly;

    @FXML
    private JFXComboBox<AnnualClass> cbClass;

    @FXML
    private JFXComboBox<CreditMajor> cbMajor;

    @FXML
    private JFXButton btnClear;

    @FXML
    void clearForm(ActionEvent event) {
        if (event.getSource().equals(btnClear)) {
            studentID.clear();
            firstName.clear();
            lastName.clear();
            birthday.setValue(null);
            phone.clear();
            email.clear();
            address.clear();
            cbMajor.getSelectionModel().select(null);
            cbClass.getSelectionModel().select(null);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        initComboBox();

    }

//    private void initComboBox() {
//        cbClass.getItems().setAll(FixedClassOperations.getInstance().getDataList());
//        cbMajor.getItems().setAll(MajorOperations.getInstance().getDataList());
//        cbMajor.setVisible(false);
//        stypeRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            cbMajor.getSelectionModel().select(null);
//            cbClass.getSelectionModel().select(null);
//            if (newValue.equals(yearly)) {
//                cbMajor.setVisible(false);
//                cbClass.setVisible(true);
//            }
//            if (newValue.equals(credit)) {
//                cbMajor.setVisible(true);
//                cbClass.setVisible(false);
//            }
//        });
//
//    }

    // TODO: 24/03/2018 need add validation (NumberFormatException for id) + new button for clearform

    @FXML
    void submitDetails() {
//        String studentIDString = studentID.getText();
//        String firstNameString = firstName.getText();
//        String lastNameString = lastName.getText();
//        String genderString = "Male";
//        if (female.isSelected()) genderString = "Female";
//        LocalDate birthdayDate = birthday.getValue();
//        String phoneString = phone.getText();
//        String emailString = email.getText();
//        String addressString = address.getText();
//
//        Major major = cbMajor.getSelectionModel().getSelectedItem();
//        FixedClass fixedClass = cbClass.getSelectionModel().getSelectedItem();
//        Student newStudent = null;
//        if (credit.isSelected()) {
//            newStudent = new CreditStudent(Integer.parseInt(studentIDString), firstNameString, lastNameString, genderString, DateUtil.format(birthdayDate), phoneString, emailString, addressString, 0);
//            if (major != null) {
//                ((CreditStudent) newStudent).setMajor(major);
//
//            }
//
//        } else {
//            newStudent = new FixedStudent(Integer.parseInt(studentIDString), firstNameString, lastNameString, genderString, DateUtil.format(birthdayDate), phoneString, emailString, addressString, StudyYear.FIRST_YEAR);
//            if (fixedClass != null) {
//                ((FixedStudent) newStudent).setFixedClass(fixedClass);
//
//            }
//
//        }
//        if (StudentOperations.getInstance().addData(newStudent)) {
//            AlertMaker.showNotification("Success", "Student info inserted successfully ", AlertMaker.image_movie_frame);
//        } else {
//            AlertMaker.showErrorMessage("Failed!", "Student ID is exist");
//        }


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
            file = fileChooser.showOpenDialog(studentID.getScene().getWindow());
            Image image = new Image(file.toURI().toString());
            avatar.setImage(image);
        } catch (Exception ex) {
//            AlertMaker.showErrorMessage(ex);
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
            ((Stage) btnBack.getScene().getWindow()).close();
    }
}