package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.DateUtil;
import com.project.javafx.ulti.ViewConstants;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    /**
     * The data as observable list of student data from databases
     */
    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, Number> studentID;

    @FXML
    private TableColumn<Student, String> firstName;

    @FXML
    private TableColumn<Student, String> lastName;

    @FXML
    private TableColumn<Student, String> educationSystem;

    @FXML
    private JFXRadioButton idFilter;

    @FXML
    private JFXRadioButton nameFilter;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private ToggleGroup filter;

    @FXML
    private Label lbl_FullName;

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
    private Label lblMajorClass;

    @FXML
    private VBox detailsBox;

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
        studentTableView.setItems(studentObservableList);
        initCols();

        showStudentDetail(null);
        // adding listener for some observable
        studentTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStudentDetail(newValue));
        filter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(idFilter)) {
                searchField.setPromptText("Search by ID");
            } else if (newValue.equals(nameFilter)) {
                searchField.setPromptText("Search by Name");
            }
        });
    }

    private void initCols() {
        //Initialize the student table, The cell must know which part of "studentTableView" it needs to display
        studentObservableList.addAll(StudentRepository.getInstance().findAll());

        studentID.setCellValueFactory(param -> {
            Student s = param.getValue();
            return new SimpleLongProperty(s.getStudentID());
        });
        firstName.setCellValueFactory(param -> {
            Student s = param.getValue();
            return new SimpleStringProperty(s.getFirstName());
        });
        lastName.setCellValueFactory(param -> {
            Student s = param.getValue();
            return new SimpleStringProperty(s.getLastName());
        });
        educationSystem.setCellValueFactory(param -> {
            Student s = param.getValue();
            return new SimpleStringProperty(s.getEducationString());
        });
    }

    @FXML
    private void searchHandle(ActionEvent event) {
        studentObservableList.clear();
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            studentObservableList.setAll(StudentRepository.getInstance().findAll());             // empty string  => show all students
        } else {
            for (Student s : StudentRepository.getInstance().findAll()) {
                if (filter.getSelectedToggle().equals(idFilter)) {
                    String id = String.valueOf(s.getStudentID());
                    if (id.equals(searchText)) {
                        studentObservableList.add(s);
                        break;
                    }
                } else {
                    // TODO: 18/04/2018 improve search engine
                    String studentName = s.getLastName() + " " + s.getFirstName();
                    if (studentName.toLowerCase().contains(searchText.toLowerCase())) {
                        studentObservableList.add(s);
                    }
                }
            }
        }
    }

    private void showStudentDetail(Student student) {
        if (student != null) {
            lbl_FullName.setText(student.getLastName() + " " + student.getFirstName());
            lblEmail.setText(student.getEmail());
            lblPhone.setText(student.getPhone());
            lblAddress.setText(student.getAddress());
            lblGender.setText(student.getGender());
            lblBirdthday.setText(DateUtil.format(student.getBirthday()));

            if (student instanceof CreditStudent) {
                int num = ((CreditStudent) student).getTakenCredits();
                String totalCredit = String.valueOf(num);
                lblMajorClass.setText("Major: " + ((CreditStudent) student).getCreditMajor().getTitleMajor());
                lblAddition.setText("Credit Taken: " + totalCredit);
            } else if (student instanceof AnnualStudent) {
                String years = ((AnnualStudent) student).getStudyYearStr();
                lblMajorClass.setText("Class: " + ((AnnualStudent) student).getAnnualClass().getClassName());
                lblAddition.setText("Year: " + years);
            }
        } else {
            lbl_FullName.setText("");
            lblEmail.setText("");
            lblPhone.setText("");
            lblAddress.setText("");
            lblGender.setText("");
            lblBirdthday.setText("");
            lblAddition.setText("");
            lblMajorClass.setText("");
        }
    }

    @FXML
    void addStudent(ActionEvent event) {
        loadWindow(ViewConstants.ADD_STUDENT_VIEW, "Add Student");
    }

    @FXML
    void updateStudent(ActionEvent event) {

    }

    @FXML
    void refreshTable(ActionEvent event) {
        studentTableView.getItems().clear();
        // TODO: 18/04/2018 improve refresh => to preserve the current stage of table;
//        List<Long> studentIDList = new ArrayList<>();
//        for (Student student : studentObservableList) {
//            studentIDList.add(student.getStudentID());
//        }
        studentObservableList.setAll(StudentRepository.getInstance().findAll());
    }

    @FXML
    void removeStudent(ActionEvent event) {
        // TODO: 18/04/2018 alert ??
        Student removeStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (removeStudent != null) {
            StudentRepository.getInstance().delete(removeStudent);
            refreshTable(event);
//            AlertMaker.showNotification("Successful", "Student Deleted", AlertMaker.image_movie_frame);
        } else {
//            AlertMaker.showNotification("Error", "No  Student Selected", AlertMaker.image_cross);
        }
    }

    private void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
//            AlertMaker.showErrorMessage(ex);
        }
    }
}
