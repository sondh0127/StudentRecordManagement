package com.project.javafx.controllerfx.course;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AddPrerequisiteCourseController implements Initializable {

    private ObservableList<CreditCourse> creditCourseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> prerequisiteCourseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private TableView<CreditCourse> tblCreditCourse;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCode;

    @FXML
    private TableColumn<CreditCourse, String> colCourseName;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private JFXButton btnAddCourseList;

    @FXML
    private Label lblMajorMinor;

    @FXML
    private TableView<CreditCourse> tblPrerequisiteCourses;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCC;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCN;

    private CreditCourse creditCourse;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void handleAddCourse(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                if (!creditCourse.addPrerequisite(selectedItem))
                    AlertMaker.showErrorMessage("Duplicated", "Existing course !");
            } catch (IllegalArgumentException e) {
                AlertMaker.showErrorMessage("Error", e.getMessage());
            }
        } else {
            AlertMaker.showErrorMessage("Input Error", "No course selected !");
        }


        refreshTable();
    }

    private void refreshTable() {
        tblPrerequisiteCourses.getItems().clear();
        prerequisiteCourseObservableList.setAll(creditCourse.getPrerequisiteCourse());
    }

    @FXML
    void handleRemoveCourse(ActionEvent event) {
        CreditCourse removeCourse = tblPrerequisiteCourses.getSelectionModel().getSelectedItem();
        if (removeCourse != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course \n" +
                    "\"" + removeCourse.getCourseName() + "\" ?");
            if (confirmation) {
                creditCourse.getPrerequisiteCourse().remove(removeCourse);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No course Selected", AlertMaker.image_cross);
        }
    }

    @FXML
    void handleSearchAction(KeyEvent event) {
        Set<CreditCourse> all = CourseRepository.getInstance().findAllCreditCourse();
        ObservableList<CreditCourse> temp = FXCollections.observableArrayList();
        if (event.getSource().equals(txtCode)) {
            String courseCode = txtCode.getText().toUpperCase();
            if (courseCode.isEmpty()) temp.addAll(all);
            else {
                for (CreditCourse course : all) {
                    if (course.getCourseCode().contains(courseCode)) {
                        temp.add(course);
                    }
                }
            }
        } else if (event.getSource().equals(txtName)) {
            String courseName = txtName.getText().toUpperCase();
            if (courseName.isEmpty()) temp.addAll(all);
            else {
                for (CreditCourse course : all) {
                    if (course.getCourseName().contains(courseName)) {
                        temp.add(course);
                    }
                }
            }
        }
        creditCourseObservableList.setAll(temp);
    }

    @FXML
    void submitDetails(ActionEvent event) {
        if (creditCourse != null) {
            if (CourseRepository.getInstance().update(creditCourse)) {
                AlertMaker.showNotification("Success", "Prerequisite Course update successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Something wrong, try again!");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.creditCourse = (CreditCourse) resources.getObject("course");
        System.out.println(creditCourse);
        initCols();
        creditCourseObservableList.setAll(CourseRepository.getInstance().findAllCreditCourse());
        refreshTable();

        Course cc11 = CourseRepository.getInstance().findById("CC11");
        Course cc12 = CourseRepository.getInstance().findById("CC12");
        if (cc11 instanceof CreditCourse) {
            boolean equals = ((CreditCourse) cc11).getPrerequisiteCourse().get(0).equals(cc12);
            System.out.println();
        }
    }

    private void initCols() {
        tblCreditCourse.setItems(creditCourseObservableList);
        colCourseCode.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
        tblPrerequisiteCourses.setItems(prerequisiteCourseObservableList);
        colCourseCC.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseCN.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
    }
}
