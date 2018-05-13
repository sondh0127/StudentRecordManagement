package com.project.javafx.controllerfx.annual;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.AnnualClass;
import com.project.javafx.model.Course;
import com.project.javafx.model.StudyLevel;
import com.project.javafx.repository.AnnualClassRepository;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class AnnualController implements Initializable {

    private ObservableList<AnnualClass> annualClassObservableList = FXCollections.observableArrayList();
    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private Label lblYear;

    @FXML
    private TableView<Course> tblCourseList;

    @FXML
    private TableColumn<Course, String> colCourseCC;

    @FXML
    private TableColumn<Course, String> colCourseCN;

    @FXML
    private JFXButton btnViewCourseList;

    @FXML
    private JFXButton btnModifyList;

    @FXML
    private TableView<AnnualClass> tblClass;

    @FXML
    private TableColumn<AnnualClass, String> colClassCode;

    @FXML
    private TableColumn<AnnualClass, String> colClassName;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private ComboBox<StudyLevel> cbxYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblClass.setItems(annualClassObservableList);
        tblCourseList.setItems(courseObservableList);
        initCols();
        annualClassObservableList.setAll(AnnualClassRepository.getInstance().findAll());
        initComboBox();
    }

    private void initComboBox() {
        cbxYear.getItems().addAll(StudyLevel.values());
        cbxYear.getSelectionModel().selectFirst();
        cbxYear.getItems().remove(StudyLevel.GRADUATED);
        cbxYear.valueProperty().addListener((observable, oldValue, newValue) ->
                lblYear.setText(newValue.toString().toUpperCase()));
    }

    private void initCols() {
        colClassCode.setCellValueFactory(param -> {
            AnnualClass c = param.getValue();
            return new SimpleStringProperty(c.getClassCode());
        });
        colClassName.setCellValueFactory(param -> {
            AnnualClass c = param.getValue();
            return new SimpleStringProperty(c.getClassName());
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

    @FXML
    void addClass(ActionEvent event) {
        loadWindow(ViewConstants.ADD_ANNUAL_VIEW, "Add Annual Class", null);
    }

    @FXML
    void handleViewCourseList(ActionEvent event) {
        if (event.getSource().equals(btnViewCourseList)) {
            AnnualClass selectedItem = tblClass.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                StudyLevel value = cbxYear.getValue();
                if (value != null) {
                    courseObservableList.setAll(selectedItem.getCoursesCatalog(value));
                }
            }
        }
    }

    @FXML
    void modifyCourseList(ActionEvent event) {
        if (event.getSource().equals(btnModifyList)) {
            try {
                AnnualClass selectedItem = tblClass.getSelectionModel().getSelectedItem();
                if (selectedItem == null) throw new IllegalArgumentException("No Annual Class Selected !");
                ResourceBundle bundle = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.equals("annual")) {
                            return selectedItem;
                        }
                        return null;
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return null;
                    }
                };
                loadWindow(ViewConstants.ADD_COURSE_LIST_VIEW, "Add Class", bundle);
            } catch (IllegalArgumentException e) {
                AlertMaker.showErrorMessage("Error.", e.getMessage());
            }
        }
    }

    @FXML
    public void refreshTable() {
        tblClass.getItems().clear();
        tblCourseList.getItems().clear();
        annualClassObservableList.setAll(AnnualClassRepository.getInstance().findAll());
    }

    @FXML
    void removeAnnual(ActionEvent event) {
        AnnualClass removeAnnual = tblClass.getSelectionModel().getSelectedItem();
        if (removeAnnual != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Annual Class",
                    "Are you sure to delete class " + "\"" + removeAnnual.getClassName() + "\" ?");
            if (confirmation) {
                AnnualClassRepository.getInstance().delete(removeAnnual);
                refreshTable();
                AlertMaker.showNotification("Deleted",
                        "Annual Class deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error.", "No Annual Class Selected !", AlertMaker.image_cross);
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
