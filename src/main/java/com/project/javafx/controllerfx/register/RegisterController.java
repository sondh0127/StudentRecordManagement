package com.project.javafx.controllerfx.register;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.ViewConstants;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<RegisterModel, String> colClass;

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
        RegisterModel removeReg = tblRegister.getSelectionModel().getSelectedItem();
        if (removeReg != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Register", "Are you sure to delete select register ?\n"
                    + "StudentID: " + removeReg.getStudentID()
                    + ", Course: " + removeReg.getCourseName());
            if (confirmation) {
                Long studentID = removeReg.getStudentID();
                Student student = StudentRepository.getInstance().findById(studentID);
                try {
                    if (student instanceof AnnualStudent) {
                        throw new IllegalArgumentException("Annual Student can not remove register course !");
                    } else if (student instanceof CreditStudent){
                        CreditClass creditClass = CreditClassRepository.getInstance().findById(removeReg.getRegisterClass());
                        Course course = CourseRepository.getInstance().findById(removeReg.getCourseCode());
                        if (creditClass.removeStudent((CreditStudent) student)) {
                            ((CreditStudent) student).dropCourse((CreditCourse) course);
                            registerModels.remove(removeReg);
                            StudentRepository.getInstance().update(student);
                            CreditClassRepository.getInstance().update(creditClass);
                            AlertMaker.showNotification("Deleted", "Register deleted successfully", AlertMaker.image_trash_can);
                        }
                        refreshTable();
                    }
                } catch (IllegalArgumentException e) {
                    AlertMaker.showErrorMessage("Delete error", e.getMessage());
                }
            }
        } else {
            AlertMaker.showNotification("Error", "No Register Selected", AlertMaker.image_cross);
        }
    }

    @FXML
    void handleSearchAction(ActionEvent event) {
        ObservableList<RegisterModel> temp = FXCollections.observableArrayList();
        try {
            if (event.getSource().equals(txtStudentID)) {
                long studentID = Long.parseLong(txtStudentID.getText());
                if (txtStudentID.getText().isEmpty()) temp.addAll(registerModels);
                else {
                    for (RegisterModel registerModel : registerModels) {
                        if (registerModel.getStudentID().equals(studentID)) {
                            temp.add(registerModel);
                        }
                    }
                }
            } else if (event.getSource().equals(txtCourseCode)) {
                String courseName = txtCourseCode.getText().toUpperCase();
                if (courseName.isEmpty()) temp.addAll(registerModels);
                else {
                    for (RegisterModel registerModel : registerModels) {
                        if (registerModel.getCourseCode().equals(courseName)) {
                            temp.add(registerModel);
                        }
                    }
                }
            }
            registerObservableList.setAll(temp);
        } catch (NumberFormatException e) {
            AlertMaker.showErrorMessage("Invalid", "Student ID must be number !");
        }
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
        Long studentID;
        String studentName;
        String courseCode;
        String courseName;
        String registerClass;
        for (CreditClass creditClass : CreditClassRepository.getInstance().findAll()) {
            List<CreditStudent> studentList = creditClass.getStudentList();
            for (CreditStudent creditStudent : studentList) {
                studentID = creditStudent.getStudentID();
                studentName = creditStudent.getLastName() + " " + creditStudent.getFirstName();
                courseCode = creditClass.getCourse().getCourseCode();
                courseName = creditClass.getCourse().getCourseName();
                registerClass = creditClass.getClassCode();
                registerModels.add(new RegisterModel(studentID, studentName, courseCode, courseName, registerClass));
            }
        }
        for (Student student : StudentRepository.getInstance().findAll()) {
            if (student instanceof AnnualStudent) {
                studentID = student.getStudentID();
                studentName = student.getLastName() + " " + student.getFirstName();
                String className = ((AnnualStudent) student).getAnnualClass().getClassName();
                for (Course course : student.getTakenResult().stream()
                        .map(StudentResult::getCourse)
                        .collect(Collectors.toList())) {
                    courseCode = course.getCourseCode();
                    courseName = course.getCourseName();
                    registerModels.add(new RegisterModel(studentID, studentName, courseCode, courseName, className));
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
        colClass.setCellValueFactory(param -> {
            RegisterModel s = param.getValue();
            return new SimpleStringProperty(s.getRegisterClass());
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
        private final String registerClass;

        private RegisterModel(Long studentID, String studentName, String courseCode, String courseName, String registerClass) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.registerClass = registerClass;
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

        public String getRegisterClass() {
            return registerClass;
        }
    }
}
