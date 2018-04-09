package com.project.javafx.controllerfx.course;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable{

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXRadioButton IDFilter;

    @FXML
    private ToggleGroup filter;

    @FXML
    private JFXRadioButton NameFilter;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> courseName;

    @FXML
    private TableColumn<Course, String> courseCode;

    @FXML
    private Label lbl_fullname;

    @FXML
    private VBox detailsBox;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblBirdthday;

    @FXML
    private Label lblAddition;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnRefresh;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        initCols();
        courseTableView.setItems(courseObservableList);



    }


//    private void initCols() {
//        courseName.setCellValueFactory(param -> param.getValue().courseNameProperty());
//        courseCode.setCellValueFactory(param -> param.getValue().courseCodeProperty());
//
//    }

    @FXML
    void addCourse(ActionEvent event) {

    }

    @FXML
    void modifyCourse(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {

    }

    @FXML
    void removeCourse(ActionEvent event) {

    }

    @FXML
    void searchHandle(ActionEvent event) {
//        courseTableView.getItems().clear();
////        courseObservableList.addAll((Course) MajorOperations.getInstance().getUnitDataList());
//        for (FixedClass aClass : FixedClassOperations.getInstance().getDataList()) {
//            courseObservableList.addAll(aClass.getCoursesCatalog().getCourseDataSet());
//        }
//
//        for (Major major : MajorOperations.getInstance().getDataList()) {
//            courseObservableList.addAll(major.getMajorCatalog().getCourseDataSet());
//        }


    }
}
