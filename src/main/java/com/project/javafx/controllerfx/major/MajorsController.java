package com.project.javafx.controllerfx.major;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.CreditCourse;
import com.project.javafx.model.CreditMajor;
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

public class MajorsController implements Initializable {

    private ObservableList<CreditMajor> majorObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> courseObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<CreditMajor> tblMajor;

    @FXML
    private TableColumn<CreditMajor, String> colMajorCode;

    @FXML
    private TableColumn<CreditMajor, String> colMajorTitle;

    @FXML
    private TableView<CreditCourse> tblCourseList;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCC;

    @FXML
    private TableColumn<CreditCourse, String> colCourseCN;

    @FXML
    private JFXButton btnViewCourseList;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXButton btnModifyList;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private Label lblMajorMinor;

    @FXML
    private ComboBox<String> cbxMajorMinor;

    private String majorList = "Major List";
    private String minorList = "Minor List";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        majorObservableList.setAll(CreditMajorRepository.getInstance().findAll());

        initComboBox();
    }

    private void initComboBox() {
        cbxMajorMinor.getItems().add(majorList);
        cbxMajorMinor.getItems().add(minorList);
        cbxMajorMinor.getSelectionModel().selectFirst();
        cbxMajorMinor.valueProperty().addListener((observable, oldValue, newValue) ->
                lblMajorMinor.setText(newValue.toUpperCase()));
    }

    private void initCols() {
        tblMajor.setItems(majorObservableList);
        colMajorCode.setCellValueFactory(param -> {
            CreditMajor c = param.getValue();
            return new SimpleStringProperty(c.getMajorCode());
        });
        colMajorTitle.setCellValueFactory(param -> {
            CreditMajor c = param.getValue();
            return new SimpleStringProperty(c.getMajorTitle());
        });
        tblCourseList.setItems(courseObservableList);
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
    void handleViewCourseList(ActionEvent event) {
        if (event.getSource().equals(btnViewCourseList)) {
            CreditMajor selectedItem = tblMajor.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String value = cbxMajorMinor.getValue();
                if (value.equals(majorList)) {
                    courseObservableList.setAll(selectedItem.getMajorCatalog());
                }
                if (value.equals(minorList)) {
                    courseObservableList.setAll(selectedItem.getMinorCatalog());
                }
            }
        }
    }

    @FXML
    void addMajor(ActionEvent event) {
        loadWindow(ViewConstants.ADD_MAJOR_VIEW, "Add Major", null);
    }

    @FXML
    void modifyCourseList(ActionEvent event) {
        try {
            CreditMajor selectedItem = tblMajor.getSelectionModel().getSelectedItem();
            if (selectedItem == null) throw new IllegalArgumentException("No Annual Class Selected !");
            ResourceBundle bundle = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.equals("major")) {
                        return selectedItem;
                    }
                    return null;
                }

                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }
            };
            loadWindow(ViewConstants.ADD_COURSE_LIST_MAJOR_VIEW, "Add Major", bundle);
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Error.", e.getMessage());
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        tblMajor.getItems().clear();
        tblCourseList.getItems().clear();
        majorObservableList.setAll(CreditMajorRepository.getInstance().findAll());
    }

    @FXML
    void removeMajor(ActionEvent event) {
        CreditMajor removeMajor = tblMajor.getSelectionModel().getSelectedItem();
        if (removeMajor != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Major", "Are you sure to delete major " + removeMajor.getMajorTitle() + " ?");
            if (confirmation) {
                CreditMajorRepository.getInstance().delete(removeMajor);
                refreshTable(event);
                AlertMaker.showNotification("Deleted", "Major deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No Major Selected", AlertMaker.image_cross);
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
            System.out.println(ex.getMessage());
        }
    }


}
