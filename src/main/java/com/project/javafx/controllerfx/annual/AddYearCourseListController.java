package com.project.javafx.controllerfx.annual;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddYearCourseListController implements Initializable {

    private ObservableList<Course> repoCourseObservableList = FXCollections.observableArrayList();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.annualClass = (AnnualClass) resources.getObject("annual");
//        System.out.println(annualClass.getClassName());

        tblCourse.setItems(repoCourseObservableList);
        tblYearCourseList.setItems(yearCourseObservableList);
        initCols();

        repoCourseObservableList.setAll(CourseRepository.getInstance().findAllNormalCourse());
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
        colCourseCode.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });

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
                this.annualClass.getCoursesCatalog(cbxYear.getValue()).remove(removeCourse);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No course Selected", AlertMaker.image_cross);
        }
    }

    @FXML
    void handleSearchAction(KeyEvent event) {

    }

    @FXML
    void handleAddCourse(ActionEvent event) {
        Course selectedItem = tblCourse.getSelectionModel().getSelectedItem();
        // TODO: 29/04/2018 alert for exist (for add major too)
        if (notExist(selectedItem) && selectedItem != null) {
            this.annualClass.addAnnualCourseCatalog(selectedItem, cbxYear.getValue());
        }
        refreshTable();
    }

    private boolean notExist(Course selectedItem) {
        List<YearOfStudy> yearOfStudies = Arrays.asList(YearOfStudy.values());
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
                AlertMaker.showNotification("Success", "Annual Class update successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Something wrong, try again!");
            }
        }
    }
}
