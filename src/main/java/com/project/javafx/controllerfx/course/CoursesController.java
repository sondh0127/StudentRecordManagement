package com.project.javafx.controllerfx.course;


import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> courseCode;

    @FXML
    private TableColumn<Course, String> courseName;

    @FXML
    private TableColumn<Course, String> courseType;

    @FXML
    private TableColumn<Course, String> scale;

    @FXML
    private TableColumn<Course, String> creditNum;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblFindBy;

    @FXML
    private ToggleGroup filterToggle;

    @FXML
    private RadioButton majorFilter;

    @FXML
    private RadioButton classFilter;

    @FXML
    private ComboBox<Object> cbxClass;

    @FXML
    private ComboBox<Object> cbxMajor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxMajor.getItems().add("[All]");
        cbxMajor.getItems().addAll(CreditMajorRepository.getInstance().findAll());
        cbxClass.getItems().add("[All]");
        cbxClass.getItems().addAll(CreditMajorRepository.getInstance().findAll());
        initCols();
        filterTableView();

    }

    private void filterTableView() {
        cbxMajor.valueProperty().addListener((observable, oldValue, newValue) -> {
            courseObservableList.clear();
            if (newValue instanceof CreditMajor) {
                System.out.println(((CreditMajor) newValue).getMajorCode());
                courseObservableList.addAll(((CreditMajor) newValue).getMajorCatalog());
                courseObservableList.addAll(((CreditMajor) newValue).getMinorCatalog());
            } else {
                courseObservableList.addAll(CourseRepository.getInstance().findAll());
                System.out.println(newValue);
            }
        });
        cbxClass.valueProperty().addListener((observable, oldValue, newValue) -> {
            courseObservableList.clear();
            if (newValue instanceof AnnualClass) {
                System.out.println(((AnnualClass) newValue).getClassCode());
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(AnnualStudent.YearOfStudy.FIRST_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(AnnualStudent.YearOfStudy.SECOND_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(AnnualStudent.YearOfStudy.THIRD_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(AnnualStudent.YearOfStudy.FOURTH_YEAR));
            } else {
                courseObservableList.addAll(CourseRepository.getInstance().findAll());
                System.out.println(newValue);
            }
        });
//        filterToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.equals(majorFilter)) lblFindBy.setText("Find by " + newValue.getProperties().toString());
//            else if (newValue.equals(classFilter)) lblFindBy.setText("Find by Annual Class");
//        });

    }

    @FXML
    void handleToggle(MouseEvent event) {
        cbxClass.setVisible(false);
        cbxClass.getSelectionModel().selectFirst();
        cbxMajor.setVisible(false);
        cbxMajor.getSelectionModel().selectFirst();
        if (event.getSource().equals(majorFilter)) {
            lblFindBy.setText("Find by " + majorFilter.getText());
            cbxMajor.getSelectionModel().selectFirst();
            cbxMajor.setVisible(true);
        } else if (event.getSource().equals(classFilter)) {
            lblFindBy.setText("Find by " + classFilter.getText());
            cbxClass.setVisible(true);
            cbxClass.getSelectionModel().selectFirst();
        }
    }

    private void initCols() {
        courseTableView.setItems(courseObservableList);
        courseObservableList.addAll(CourseRepository.getInstance().findAll());

        courseCode.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        courseName.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
        courseType.setCellValueFactory(param -> {
            Course c = param.getValue();
            if (c instanceof CreditCourse) {
                return new SimpleStringProperty("Credit Course");
            } else return new SimpleStringProperty("Normal Course");
        });
        scale.setCellValueFactory(param -> {
            Course c = param.getValue();
            double scale = c.getScale();
            double _scale = 1 - scale;
            StringBuilder scaleStr = new StringBuilder();
            scaleStr.append(scale).append("/").append(_scale);
            return new SimpleStringProperty(scaleStr.toString());
        });

        creditNum.setCellValueFactory(param -> {
            Course c = param.getValue();
            if (c instanceof CreditCourse) {
                int creditHours = ((CreditCourse) c).getCreditHours();
                String creditStr = String.valueOf(creditHours);
                return new SimpleStringProperty(creditStr);
            } else return new SimpleStringProperty("");
        });
    }


    @FXML
    void handleSearchAction(ActionEvent event) {

    }

    @FXML
    void addCourse(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {

    }

    @FXML
    void removeCourse(ActionEvent event) {

    }

    @FXML
    void searchHandle(ActionEvent event) {

    }

    @FXML
    void updateCourse(ActionEvent event) {

    }

}
