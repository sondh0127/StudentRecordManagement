package com.project.javafx.controllerfx.annual;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.Course;
import com.project.javafx.model.YearOfStudy;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AddYearCourseListController implements Initializable {

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    private ObservableList<Course> yearCourseObservableList = FXCollections.observableArrayList();

    @FXML
    private Label lblYear;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private TableView<Course> tblCourse;

    @FXML
    private TableColumn<Course, String> colCourseCode;

    @FXML
    private TableColumn<Course, String> colCourseName;

    @FXML
    private TableView<Course> tblYearCourseList;

    @FXML
    private TableColumn<Course, String> colCourseCC;

    @FXML
    private TableColumn<Course, String> colCourseCN;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<YearOfStudy> cbxYear;

    @FXML
    private JFXButton btnViewCourseList;

    private AnnualClass annualClass;

    private List<AnnualStudent> toUpdateStudents = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.annualClass = (AnnualClass) resources.getObject("annual");
//        System.out.println(annualClass.getClassName());
        initCols();
        courseObservableList.setAll(CourseRepository.getInstance().findAllNormalCourse());
        initComboBox();
        refreshTable();

    }

    private void initComboBox() {
        cbxYear.getItems().addAll(YearOfStudy.values());
        cbxYear.getSelectionModel().selectFirst();
        cbxYear.getItems().remove(YearOfStudy.GRADUATED);
        cbxYear.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblYear.setText(newValue.toString().toUpperCase());
            refreshTable();
        });
    }

    private void initCols() {
        tblCourse.setItems(courseObservableList);
        colCourseCode.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
        tblYearCourseList.setItems(yearCourseObservableList);
        colCourseCC.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseCN.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
    }

    private void refreshTable() {
        tblYearCourseList.getItems().clear();
        yearCourseObservableList.setAll(this.annualClass.getCoursesCatalog(cbxYear.getValue()));
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void handleRemoveCourse(ActionEvent event) {
        Course removeCourse = tblYearCourseList.getSelectionModel().getSelectedItem();
        if (removeCourse != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course \n" +
                    "\"" + removeCourse.getCourseName() + "\" ?");
            if (confirmation) {
                YearOfStudy yearValue = cbxYear.getValue();
                this.annualClass.getCoursesCatalog(yearValue).remove(removeCourse);
                List<AnnualStudent> students = annualClass.getStudents();
                for (AnnualStudent student : students) {
                    if (student.getStudyYear().equals(yearValue)) {
                        student.removeYearCourse(removeCourse);
                        toUpdateStudents.add(student);
                    }
                }
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No course Selected", AlertMaker.image_cross);
        }
    }

    @FXML
    void handleSearchAction(KeyEvent event) {
        Set<Course> all = CourseRepository.getInstance().findAllNormalCourse();
        ObservableList<Course> temp = FXCollections.observableArrayList();
        if (event.getSource().equals(txtCode)) {
            String courseCode = txtCode.getText().toUpperCase();
            if (courseCode.isEmpty()) temp.addAll(all);
            else {
                for (Course course : all) {
                    if (course.getCourseCode().contains(courseCode)) {
                        temp.add(course);
                    }
                }
            }
        } else if (event.getSource().equals(txtName)) {
            String courseName = txtName.getText().toUpperCase();
            if (courseName.isEmpty()) temp.addAll(all);
            else {
                for (Course course : all) {
                    if (course.getCourseName().contains(courseName)) {
                        temp.add(course);
                    }
                }
            }
        }
        courseObservableList.setAll(temp);
    }

    @FXML
    void handleAddCourse(ActionEvent event) {
        Course selectCourse = tblCourse.getSelectionModel().getSelectedItem();
        if (notExist(selectCourse)) {
            if (selectCourse != null) {
                YearOfStudy yearValue = cbxYear.getValue();
                annualClass.addAnnualCourseCatalog(selectCourse, yearValue);
                List<AnnualStudent> students = annualClass.getStudents();
                for (AnnualStudent student : students) {
                    if (student.getStudyYear().equals(yearValue)) {
                        student.addYearCourse(selectCourse);
                        toUpdateStudents.add(student);
                    }
                }
            } else {
                AlertMaker.showErrorMessage("Error", "No course selected !");
            }
        } else {
            AlertMaker.showErrorMessage("Duplicate", "Existing course !");
        }
        refreshTable();
    }

    private boolean notExist(Course selectedItem) {
        YearOfStudy[] yearOfStudies = YearOfStudy.values();
        for (YearOfStudy yearOfStudy : yearOfStudies) {
            if (!yearOfStudy.equals(YearOfStudy.GRADUATED)) {
                if (this.annualClass.getCoursesCatalog(yearOfStudy).contains(selectedItem)) {
                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    void submitDetails(ActionEvent event) {
        if (annualClass != null) {
            if (AnnualClassRepository.getInstance().update(annualClass)) {
                for (AnnualStudent student : toUpdateStudents) {
                    StudentRepository.getInstance().update(student);
                }
                AlertMaker.showNotification("Success", "Annual Class update successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Something wrong, try again!");
            }
        }
    }
}
