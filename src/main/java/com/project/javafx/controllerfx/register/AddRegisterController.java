package com.project.javafx.controllerfx.register;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AddRegisterController implements Initializable {

    private ObservableList<CreditCourse> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private TableView<CreditCourse> tblCreditCourse;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCode;

    @FXML
    private TableColumn<CreditCourse, String> colCourseName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        tblCreditCourse.setItems(courseObservableList);
    }

    private void initCols() {
        colCourseCode.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void handleRegister(ActionEvent event) {
        Student student = null;
        try {
            String studentID = txtStudentId.getText();
            String courseCode = txtCourseCode.getText().toUpperCase();
            student = StudentRepository.getInstance().findById(Long.valueOf(studentID));
            Course course = CourseRepository.getInstance().findById(courseCode);
            if (student == null) throw new IllegalArgumentException("Could not found student !");
            if (course == null) throw new IllegalArgumentException("Could not found course !");
            if (student instanceof CreditStudent
                    && course instanceof CreditCourse) {
                    ((CreditStudent) student).registerCourse((CreditCourse) course);
            }
            if (StudentRepository.getInstance().update(student)) {
                AlertMaker.showNotification("Success", "Register update successfully !", AlertMaker.image_checked);
            }
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Error!", e.getMessage());
        }
    }

    @FXML
    void handleSearchAction(KeyEvent event) {
        Set<CreditCourse> all = CourseRepository.getInstance().findAllCreditCourse();
        ObservableList<CreditCourse> temp = FXCollections.observableArrayList();
        if (event.getSource().equals(txtCourseCode)) {
            String courseCode = txtCourseCode.getText().toUpperCase();
            if (courseCode.isEmpty()) temp.addAll(all);
            else {
                for (CreditCourse course : all) {
                    if (course.getCourseCode().contains(courseCode)) {
                        temp.add(course);
                    }
                }
            }
        }
        courseObservableList.clear();
        courseObservableList.addAll(temp);
    }

    @FXML
    void submitDetails(ActionEvent event) {
    }
}
