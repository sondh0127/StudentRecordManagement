package com.project.javafx.controllerfx.course;


import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditMajorRepository;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.ViewConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Set;

public class CoursesController implements Initializable {

    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnAddPrerequisite;

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
        initCols();
        refreshTable();
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
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(YearOfStudy.FIRST_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(YearOfStudy.SECOND_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(YearOfStudy.THIRD_YEAR));
                courseObservableList.addAll(((AnnualClass) newValue).getCoursesCatalog(YearOfStudy.FOURTH_YEAR));
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
            BigDecimal scale = BigDecimal.valueOf(c.getScale());
            BigDecimal _scale = BigDecimal.ONE.subtract(scale);

            StringBuilder scaleStr = new StringBuilder();
            scaleStr.append(_scale).append("/").append(scale);
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
    void handleSearchAction(KeyEvent event) {
        Set<Course> all = CourseRepository.getInstance().findAll();
        ObservableList<Course> temp = FXCollections.observableArrayList();
        if (event.getSource().equals(txtCode)) {
            String courseCode = txtCode.getText().toUpperCase();
            System.out.println(courseCode);
            if (courseCode.isEmpty()) temp.addAll(all);
            else {
                for (Course course : all) {
                    if (course.getCourseCode().contains(courseCode)) {
                        temp.add(course);
                    }
                }
            }
        } else if (event.getSource().equals(txtName)) {
            String courseName = txtName.getText().toLowerCase();
            if (courseName.isEmpty()) temp.addAll(all);
            else {
                for (Course course : all) {
                    if (course.getCourseName().toLowerCase().contains(courseName)) {
                        temp.add(course);
                    }
                }
            }
        }
        courseObservableList.clear();
        courseObservableList.addAll(temp);
    }

    @FXML
    void addCourse(ActionEvent event) {
        loadWindow(ViewConstants.ADD_COURSE_VIEW, "Add Course", null);
    }

    @FXML
    public void refreshTable() {
        courseTableView.getItems().clear();
        cbxMajor.getItems().clear();
        cbxClass.getItems().clear();
        cbxMajor.getItems().add("[All]");
        cbxMajor.getItems().addAll(CreditMajorRepository.getInstance().findAll());
        cbxClass.getItems().add("[All]");
        cbxClass.getItems().addAll(AnnualClassRepository.getInstance().findAll());
        courseObservableList.setAll(CourseRepository.getInstance().findAll());
    }

    @FXML
    void removeCourse(ActionEvent event) {
        Course removeCourse = courseTableView.getSelectionModel().getSelectedItem();
        if (removeCourse != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Course", "Are you sure to delete course " + removeCourse.getCourseCode() + " ?");
            if (confirmation) {
                CourseRepository.getInstance().delete(removeCourse);
                // TODO: 22/04/2018 xoa everywhere
                if (removeCourse instanceof CreditCourse)
                    CreditMajorRepository.getInstance().deleteCourseForAllMajor((CreditCourse) removeCourse);
                else AnnualClassRepository.getInstance().deleteCourseForAllClass(removeCourse);
                refreshTable();
                AlertMaker.showNotification("Deleted", "Course deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No Course Selected", AlertMaker.image_cross);
        }
    }

    @FXML
    void handleAddPrerequisite(ActionEvent event) {
        Course selectedItem = courseTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem instanceof CreditCourse) {
                loadWindow(ViewConstants.ADD_PREREQUISITE_COURSE, "Add PrerequisiteCourse", new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.equals("course"))
                            return selectedItem;
                        return null;
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return null;
                    }
                });
            } else {
                AlertMaker.showErrorMessage("Error!", "Cannot add prerequisite for Normal Course !");
            }
        } else {
            AlertMaker.showErrorMessage("Error!", "No Course Selected !");
        }
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
            ex.printStackTrace();
        }
    }


}
