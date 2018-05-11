package com.project.javafx.controllerfx.creditClass;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditClass;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.repository.CourseRepository;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.ViewConstants;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Set;

public class CreditClassesController implements Initializable {

    private ObservableList<CreditCourse> courseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditClass> classObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<CreditClass> tblCreditClassList;

    @FXML
    private TableColumn<CreditClass, String> colClassCode;

    @FXML
    private TableColumn<CreditClass, String> colCourse;

    @FXML
    private TableColumn<CreditClass, Number> colEnrolled;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private JFXButton btnViewClassList;

    @FXML
    private TableView<CreditCourse> tblCreditCourse;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCode;

    @FXML
    private TableColumn<CreditCourse, String> colCourseName;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    void addClass(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showNotification("Error", "No  Course Selected", AlertMaker.image_cross);
        } else {
            loadWindow(ViewConstants.ADD_CREDIT_CLASS, "Add Credit Class", new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.equals("creditCourse")) {
                        return selectedItem;
                    }
                    return null;
                }

                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }
            });
        }
    }

    @FXML
    void handleSearchAction(KeyEvent event) {
        Set<CreditCourse> all = CourseRepository.getInstance().findAllCreditCourse();
        ObservableList<CreditCourse> temp = FXCollections.observableArrayList();
        if (event.getSource().equals(txtCode)) {
            String courseCode = txtCode.getText().toUpperCase();
            System.out.println(courseCode);
            if (courseCode.isEmpty()) temp.addAll(all);
            else {
                all.stream().filter(course -> course.getCourseCode().contains(courseCode))
                        .forEach(temp::add);
            }
        } else if (event.getSource().equals(txtName)) {
            String courseName = txtName.getText().toUpperCase();
            if (courseName.isEmpty()) temp.addAll(all);
            else {
                all.stream().filter(course -> course.getCourseName().contains(courseName))
                        .forEach(temp::add);
            }
        }
        courseObservableList.setAll(temp);
    }

    @FXML
    void handleViewClassList(ActionEvent event) {
        CreditCourse selectedItem = tblCreditCourse.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showNotification("Error", "No  Course Selected", AlertMaker.image_cross);
        } else {
            classObservableList.setAll(CreditClassRepository.getInstance().getCreditClassOf(selectedItem));
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        courseObservableList.setAll(CourseRepository.getInstance().findAllCreditCourse());
    }

    @FXML
    void removeClass(ActionEvent event) {
        CreditClass selectedItem = tblCreditClassList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Class", "Are you sure to delete class " + selectedItem.getClassCode() + " ?");
            if (confirmation) {
                CreditClassRepository.getInstance().delete(selectedItem);
                AlertMaker.showNotification("Deleted", "Class deleted successfully", AlertMaker.image_trash_can);
                handleViewClassList(new ActionEvent());
            }
        } else {
            AlertMaker.showNotification("Error", "No Course Selected", AlertMaker.image_cross);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        refreshTable(new ActionEvent());
    }

    private void initCols() {
        tblCreditCourse.setItems(courseObservableList);
        colCourseCode.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            Course c = param.getValue();
            return new SimpleStringProperty(c.getCourseName());
        });
        tblCreditClassList.setItems(classObservableList);

        colClassCode.setCellValueFactory(param -> {
            CreditClass c = param.getValue();
            return new SimpleStringProperty(c.getClassCode());
        });

        colCourse.setCellValueFactory(param -> {
            CreditClass c = param.getValue();
            return new SimpleStringProperty(c.getCourse().getCourseCode());
        });

        colEnrolled.setCellValueFactory(param -> {
            CreditClass c = param.getValue();
            return new SimpleIntegerProperty(c.getEnrolled());
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

}
