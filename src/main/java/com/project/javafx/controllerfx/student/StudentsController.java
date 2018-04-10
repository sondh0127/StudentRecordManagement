package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.model.repository.StudentRepository;
import com.project.javafx.ulti.DateUtil;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
    private JFXRadioButton IDFilter;
    @FXML
    private JFXRadioButton NameFilter;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private ToggleGroup filter;
    @FXML
    private Label lbl_fullname;
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
    private ResultSet rs;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private JFXButton btnModify;
    @FXML
    private JFXButton btnRefresh;

    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentTableView.setItems(studentObservableList);
        initCols();

        showStudentDetail(null);
        // adding listener for some observable
        studentTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStudentDetail(newValue));
        filter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(IDFilter)) {
                searchField.setPromptText("Search by ID");
            } else if (newValue.equals(NameFilter)) {
                searchField.setPromptText("Search by Name");
            }
        });
    }

    private void initCols() {
        //Initialize the student table, The cell must know which part of "tableUser" it needs to display
        studentObservableList.addAll(StudentRepository.getInstance().getList());

        studentID.setCellValueFactory((CellDataFeatures<Student,Number> cdf) -> {
            Student q = cdf.getValue();
            return new SimpleIntegerProperty(q.getStudentID());
        });
        firstName.setCellValueFactory((CellDataFeatures<Student,String> cdf) -> {
            Student q = cdf.getValue();
            return new SimpleStringProperty(q.getFirstName());
        });
        lastName.setCellValueFactory((CellDataFeatures<Student,String> cdf) -> {
            Student q = cdf.getValue();
            return new SimpleStringProperty(q.getLastName());
        });
        System.out.println(studentTableView.getItems().size());
    }

    @FXML
    private void searchHandle(ActionEvent event) {
        studentObservableList.clear();
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            studentObservableList.setAll(StudentRepository.getInstance().getList());
        } else {
            for (Student student : StudentRepository.getInstance().getList()) {
                if (filter.getSelectedToggle().equals(IDFilter)) {
                    if (student.getStudentID() == Integer.parseInt(searchText)) {
                        studentObservableList.add(student);
                        break;
                    }
                } else {
                    String studentName = student.getLastName() + " " + student.getFirstName();
                    if (studentName.toLowerCase().contains(searchText.toLowerCase())) {
                        studentObservableList.add(student);
                    }
                }
            }
        }


    }

    private void showStudentDetail(Student student) {
        if (student != null) {
            lbl_fullname.setText(student.getLastName() + " " + student.getFirstName());
            lblEmail.setText(student.getEmail());
            lblPhone.setText(student.getPhone());
            lblAddress.setText(student.getAddress());
            lblGender.setText(student.getGender());
            lblBirdthday.setText(DateUtil.format(student.getBirthday()));
            if (student instanceof CreditStudent) {
                if (((CreditStudent) student).getCreditMajor() != null) {
                    int num = ((CreditStudent) student).getCurrentCredits();
                    String totalCredit = Integer.toString(num);
                    lblMajorClass.setText("Major: " + ((CreditStudent) student).getCreditMajor().getTitleMajor());
                    lblAddition.setText("Credit Taken: " + totalCredit);
                } else {
                    lblMajorClass.setText("No major yet");
                    lblAddition.setText("");
                }


            } else if (student instanceof AnnualStudent) {
                if (((AnnualStudent) student).getAnnualClass() != null) {
                    String years = ((AnnualStudent) student).getStudyYear();
                    lblMajorClass.setText("Class: " + ((AnnualStudent) student).getAnnualClass().getClassName());
                    lblAddition.setText("Year: " + years);
                } else {
                    lblMajorClass.setText("No class yet");
                    lblAddition.setText("");

                }

            }
        } else {
            lbl_fullname.setText("");
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
        studentObservableList.setAll(StudentRepository.getInstance().getList());
    }

    @FXML
    void removeStudent(ActionEvent event) {
        Student removeStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (removeStudent != null) {
            StudentRepository.getInstance().deleteElement(removeStudent);
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
