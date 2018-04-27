package com.project.javafx.controllerfx.major_class;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCourseListController implements Initializable {

    private ObservableList<CreditCourse> creditCourseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> majorCourseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> minorCourseObservableList = FXCollections.observableArrayList();

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
    private JFXButton btnAddMajor;

    @FXML
    private JFXButton btnAddMinor;

    @FXML
    private TableView<CreditCourse> tblMajorCourseList;

    @FXML
    private TableColumn<CreditCourse, String> colMajorCC;

    @FXML
    private TableColumn<CreditCourse, String> colMajorCN;

    @FXML
    private TableView<CreditCourse> tblMinorCourseList;

    @FXML
    private TableColumn<CreditCourse, String> colMinorCC;

    @FXML
    private TableColumn<CreditCourse, String> colMinorCN;

    private CreditMajor creditMajor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.creditMajor = (CreditMajor) resources.getObject("major");
        System.out.println(creditMajor.getMajorTitle());

        tblCreditCourse.setItems(creditCourseObservableList);
        tblMajorCourseList.setItems(majorCourseObservableList);
        tblMinorCourseList.setItems(minorCourseObservableList);
        initCols();
        creditCourseObservableList.setAll(CourseRepository.getInstance().findAllCreditCourse());
        refreshTable();
        tblMajorCourseList.setOnMouseClicked(event -> {
            tblMinorCourseList.getSelectionModel().select(null);
        });
        tblMinorCourseList.setOnMouseClicked(event -> {
            tblMajorCourseList.getSelectionModel().select(null);
        });

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

        colMajorCC.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colMajorCN.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });

        colMinorCC.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colMinorCN.setCellValueFactory(param -> {
            CreditCourse c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    @FXML
    void handleAddMajorList(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (notExist(selectedItem) && selectedItem != null) {
            this.creditMajor.getMajorCatalog().add(selectedItem);
        }
        refreshTable();
    }

    private boolean notExist(CreditCourse selectedItem) {
        if (this.creditMajor.getMajorCatalog().contains(selectedItem)) {
            return false;
        }
        if (this.creditMajor.getMinorCatalog().contains(selectedItem)) {
            return false;
        }
        return true;
    }

    @FXML
    void handleAddMinorList(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (notExist(selectedItem) && selectedItem != null) {
            this.creditMajor.getMinorCatalog().add(selectedItem);
        }
        refreshTable();
    }

    @FXML
    void handleSearchAction(KeyEvent event) {

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
        tblMinorCourseList.getItems().clear();
        majorCourseObservableList.setAll(this.creditMajor.getMajorCatalog());
        minorCourseObservableList.setAll(this.creditMajor.getMinorCatalog());
    }

    @FXML
    void handleRemoveCourse(ActionEvent event) {
        CreditCourse removeCourse = tblMajorCourseList.getSelectionModel().getSelectedItem();
        CreditCourse removeCourse1 = tblMinorCourseList.getSelectionModel().getSelectedItem();
        if (removeCourse != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course \n" +
                    "\"" + removeCourse.getCourseName() + "\" ?");
            if (confirmation) {
                this.creditMajor.getMajorCatalog().remove(removeCourse);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else if (removeCourse1 != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course \n" +
                    "\"" + removeCourse1.getCourseName() + "\" ?");
            if (confirmation) {
                this.creditMajor.getMinorCatalog().remove(removeCourse1);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No course Selected", AlertMaker.image_cross);
        }
    }
}
