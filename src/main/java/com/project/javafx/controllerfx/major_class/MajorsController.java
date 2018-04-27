package com.project.javafx.controllerfx.major_class;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ObservableList<CreditCourse> majorCourseObservableList = FXCollections.observableArrayList();
    private ObservableList<CreditCourse> minorCourseObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXRadioButton idFilter;

    @FXML
    private ToggleGroup filter;

    @FXML
    private JFXRadioButton nameFilter;

    @FXML
    private TextField txtSearchField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private TableView<CreditMajor> tblMajor;

    @FXML
    private TableColumn<CreditMajor, String> colMajorCode;

    @FXML
    private TableColumn<CreditMajor, String> colMajorTitle;

    @FXML
    private TableView<CreditCourse> tblMajorCourseList;

    @FXML
    private TableView<CreditCourse> tblMinorCourseList;

    @FXML
    private TableColumn<CreditCourse, String> colMajorCC;

    @FXML
    private TableColumn<CreditCourse, String> colMajorCN;

    @FXML
    private TableColumn<CreditCourse, String> colMinorCC;

    @FXML
    private TableColumn<CreditCourse, String> colMinorCN;

    @FXML
    private JFXButton btnViewCourseList;

    @FXML
    private JFXButton btnRefresh1;

    @FXML
    private JFXButton btnModifyList;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRemove;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblMajor.setItems(majorObservableList);
        tblMajorCourseList.setItems(majorCourseObservableList);
        tblMinorCourseList.setItems(minorCourseObservableList);
        initCols();
        majorObservableList.setAll(CreditMajorRepository.getInstance().findAll());
    }

    private void initCols() {
        colMajorCode.setCellValueFactory(param -> {
            CreditMajor c = param.getValue();
            return new SimpleStringProperty(c.getMajorCode());
        });
        colMajorTitle.setCellValueFactory(param -> {
            CreditMajor c= param.getValue();
            return new SimpleStringProperty(c.getMajorTitle());
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
    void handleViewCourseList(ActionEvent event) {
        if (event.getSource().equals(btnViewCourseList)) {
            CreditMajor selectedItem = tblMajor.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                majorCourseObservableList.setAll(selectedItem.getMajorCatalog());
                minorCourseObservableList.setAll(selectedItem.getMinorCatalog());
            }
        }
    }

    @FXML
    void addMajor(ActionEvent event) {
        loadWindow(ViewConstants.ADD_MAJOR_VIEW, "Add Major", null);
    }

    @FXML
    void modifyCourseList(ActionEvent event) {
        CreditMajor selectedItem = tblMajor.getSelectionModel().getSelectedItem();
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
    }



    @FXML
    void handleSearchAction(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {
        tblMajor.getItems().clear();
        tblMajorCourseList.getItems().clear();
        tblMinorCourseList.getItems().clear();
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
