package com.project.javafx.controllerfx.register;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.StudentResult;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.ViewConstants;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RegisterController implements Initializable {

    private ArrayList<RegisterModel> registerModels = new ArrayList<>();
    private ObservableList<RegisterModel> registerObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TableView<RegisterModel> tblRegister;

    @FXML
    private TableColumn<RegisterModel, Number> colStudentID;

    @FXML
    private TableColumn<RegisterModel, String> colFullName;

    @FXML
    private TableColumn<RegisterModel, String> colCourseCode;

    @FXML
    private TableColumn<RegisterModel, String> colCourseName;

    @FXML
    private TableColumn<RegisterModel, Number> colCredit;

    @FXML
    private TableColumn<RegisterModel, String> colStatus;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnRefresh1;

    @FXML
    void handleAdd(ActionEvent event) {
        loadWindow(ViewConstants.ADD_REGISTER_VIEW, "Add Register", null);
    }

    @FXML
    void handleRemove(ActionEvent event) {

    }

    @FXML
    void handleSearchAction(KeyEvent event) {

    }

    @FXML
    public void refreshTable() {
        tblRegister.getItems().clear();
        initData();
        registerObservableList.setAll(registerModels);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        tblRegister.setItems(registerObservableList);
        refreshTable();
    }

    private void initData() {
        registerModels.clear();
        for (CreditStudent creditStudent : StudentRepository.getInstance().getCreditStudent()) {
//            System.out.println(creditStudent.getTakenResult().get(0).getCourse());
            long studentID = creditStudent.getStudentID();
            String fullName = creditStudent.getLastName() + " " + creditStudent.getFirstName();
            for (Course course : creditStudent.getTakenResult().stream()
                    .map(StudentResult::getCourse)
                    .collect(Collectors.toList())) {
                if (course instanceof CreditCourse) {
                    String courseCode = course.getCourseCode();
                    String courseName = course.getCourseName();
                    int creditHours = ((CreditCourse) course).getCreditHours();
                    registerModels.add(new RegisterModel(studentID, fullName, courseCode, courseName, creditHours));
                }
            }
        }
    }

    private void initCols() {
        colStudentID.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleLongProperty(s.getStudentID());
        });
        colFullName.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleStringProperty(s.getStudentName());
        });
        colCourseCode.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleStringProperty(s.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleStringProperty(s.getCourseName());
        });
        colCredit.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleIntegerProperty(s.getCreditNum());
        });
    }

    private void loadWindow(String loc, String title, ResourceBundle bundle) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc), bundle);
            Stage stage = new Stage(StageStyle.DECORATED); // Default Style
            stage.setTitle(title);
            stage.getIcons().add(new Image(ViewConstants.APP_ICON));
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private class RegisterModel {

        private final Long studentID;
        private final String studentName;
        private final String courseCode;
        private final String courseName;
        private final Integer creditNum;

        private RegisterModel(Long studentID, String studentName, String courseCode, String courseName, Integer creditNum) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.creditNum = creditNum;
        }

        public Long getStudentID() {
            return studentID;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public Integer getCreditNum() {
            return creditNum;
        }
    }
}
