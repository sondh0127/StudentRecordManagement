package com.project.javafx.controllerfx.major;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
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
import java.util.ResourceBundle;
import java.util.Set;

public class AddCourseListController implements Initializable {

    private ObservableList<CreditCourse> creditCourseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> majorCourseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

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
    private ComboBox<String> cbxMajorMinor;

    @FXML
    private JFXButton btnAddCourseList;

    @FXML
    private Label lblMajorMinor;

    @FXML
    private TableView<CreditCourse> tblMajorCourseList;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCC;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCN;

    private String majorList = "Major List";
    private String minorList = "Minor List";

    private CreditMajor creditMajor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.creditMajor = (CreditMajor) resources.getObject("major");
        System.out.println(creditMajor.getMajorTitle());
        initCols();
        creditCourseObservableList.setAll(CourseRepository.getInstance().findAllCreditCourse());
        initComboBox();
        refreshTable();
    }

    private void initComboBox() {
        cbxMajorMinor.getItems().add(majorList);
        cbxMajorMinor.getItems().add(minorList);
        cbxMajorMinor.getSelectionModel().selectFirst();
        cbxMajorMinor.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMajorMinor.setText(newValue.toUpperCase());
            refreshTable();
        });
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
        tblMajorCourseList.setItems(majorCourseObservableList);
        colCourseCC.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseCN.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void handleAddCourse(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (notExist(selectedItem)) {
            if (selectedItem != null) {
                String value = cbxMajorMinor.getValue();
                if (value.equals(majorList)) {
                    creditMajor.getMajorCatalog().add(selectedItem);
                }
                if (value.equals(minorList)) {
                    creditMajor.getMinorCatalog().add(selectedItem);
                }
            } else {
                AlertMaker.showErrorMessage("Error", "No course selected !");
            }
        } else {
            AlertMaker.showErrorMessage("Duplicate", "Existing course !");
        }
        refreshTable();
    }

    private boolean notExist(CreditCourse selectedItem) {
        if (creditMajor.getMajorCatalog().contains(selectedItem)) {
            return false;
        }
        if (creditMajor.getMinorCatalog().contains(selectedItem)) {
            return false;
        }
        return true;
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
        if (creditMajor != null) {
            if (CreditMajorRepository.getInstance().update(creditMajor)) {
                AlertMaker.showNotification("Success", "Major update successfully !", AlertMaker.image_checked);
            } else {
                AlertMaker.showErrorMessage("Failed!", "Something wrong, try again!");
            }
        }
    }

    private void refreshTable() {
        tblMajorCourseList.getItems().clear();
        if (cbxMajorMinor.getValue().equals(majorList)) {
            majorCourseObservableList.setAll(creditMajor.getMajorCatalog());
        }
        if (cbxMajorMinor.getValue().equals(minorList)) {
            majorCourseObservableList.setAll(creditMajor.getMinorCatalog());
        }
    }

    @FXML
    void handleRemoveCourse(ActionEvent event) {
        CreditCourse removeCourse = tblMajorCourseList.getSelectionModel().getSelectedItem();
        if (removeCourse != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course \n" +
                    "\"" + removeCourse.getCourseName() + "\" ?");
            if (confirmation) {
                this.creditMajor.getMajorCatalog().remove(removeCourse);
                this.creditMajor.getMinorCatalog().remove(removeCourse);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No course Selected", AlertMaker.image_cross);
        }
    }
}
