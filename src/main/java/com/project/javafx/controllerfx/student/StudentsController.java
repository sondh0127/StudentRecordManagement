package com.project.javafx.controllerfx.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.repository.UserRepository;
import com.project.javafx.ulti.AlertMaker;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    public static UpdateStudentProfileController updateStudentProfileController;
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
    private TextField txtSearchField;
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
    private Label lblAvgCpa;
    @FXML
    private Label lblMajorClass;
    @FXML
    private VBox detailsBox;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXButton btnGraduate;

    @FXML
    void handleGraduate(ActionEvent event) {
        Student checkStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (checkStudent != null) {
            if (checkStudent.ableToGraduate()) {
                // TODO: 10/05/2018 more info
                AlertMaker.showSimpleAlert("Graduation", "Student " + checkStudent.getStudentID() + " able to graduated");
            } else {
                AlertMaker.showSimpleAlert("Graduation", "Student " + checkStudent.getStudentID() + " not able to graduated");
            }
        } else {
            AlertMaker.showNotification("Error", "No  Student Selected", AlertMaker.image_cross);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentTableView.setItems(studentObservableList);
        initCols();

        // adding listener for Student Information Tab
        showStudentDetail(null);
        studentTableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showStudentDetail(newValue));

        initSearching();
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
            String eduSys = "";
            if (s instanceof CreditStudent)
                eduSys = "Academic Credit";
            if (s instanceof AnnualStudent)
                eduSys = "Annual Curriculum";
            return new SimpleStringProperty(eduSys);
        });
    }

    /**
     * Create some user interface feature for search method
     */
    private void initSearching() {
        filter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(idFilter)) {
                txtSearchField.setPromptText("Search by ID");
            } else if (newValue.equals(nameFilter)) {
                txtSearchField.setPromptText("Search by Name");
            }
        });
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        studentObservableList.clear();
        String searchText = txtSearchField.getText();
        if (searchText.isEmpty()) {
            studentObservableList.setAll(StudentRepository.getInstance().findAll());
        } else {
            for (Student s : StudentRepository.getInstance().findAll()) {
                if (filter.getSelectedToggle().equals(idFilter)) {
                    String id = String.valueOf(s.getStudentID());
                    if (id.equals(searchText)) {
                        studentObservableList.add(s);
                        break;
                    }
                } else {
                    String studentName = s.getLastName() + " " + s.getFirstName();
                    if (studentName.toLowerCase().contains(searchText.toLowerCase())) {
                        studentObservableList.add(s);
                    }
                }
            }
        }
    }

    /**
     * Method help to showing all detail
     *
     * @param student
     */
    private void showStudentDetail(Student student) {
        if (student != null) {
            lbl_FullName.setText(student.getLastName() + " " + student.getFirstName());
            lblEmail.setText(student.getEmail());
            lblPhone.setText(student.getPhone());
            lblAddress.setText(student.getAddress());
            lblGender.setText(student.getGender().toString());
            lblBirdthday.setText(DateUtil.format(student.getBirthday()));
            btnGraduate.setVisible(true);

            if (student instanceof CreditStudent) {
                int num = ((CreditStudent) student).getPassedMajorCredits() + ((CreditStudent) student).getPassedMinorCredits();
                String totalCredit = String.valueOf(num);
                lblMajorClass.setText("Major: " + ((CreditStudent) student).getCreditMajor().getMajorTitle());
                lblAddition.setText("Credit Taken: " + totalCredit);
                lblAvgCpa.setText("CPA: " + ((CreditStudent) student).getCPA());
            } else if (student instanceof AnnualStudent) {
                String years = ((AnnualStudent) student).getStudyLevel().toString();
                String className = ((AnnualStudent) student).getAnnualClass().getClassName();
//                String className = AnnualClassRepository.getInstance().getAnnualClassOf((AnnualStudent) student).getClassName();
                lblMajorClass.setText("Class: " + className);
                lblAddition.setText("Year: " + years);
                lblAvgCpa.setText("AVG: " + ((AnnualStudent) student).getAVG());
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
            lblAvgCpa.setText("");
            btnGraduate.setVisible(false);
        }
    }

    @FXML
    void addStudent(ActionEvent event) {
        loadWindow(ViewConstants.ADD_STUDENT_VIEW, "Add Student", null);
    }

    @FXML
    void updateStudent(ActionEvent event) {
        Student updateStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (updateStudent == null) {
            AlertMaker.showNotification("Error", "No  Student Selected", AlertMaker.image_cross);
        } else {
            if (event.getSource().equals(btnUpdate))
                loadWindow(ViewConstants.UPDATE_STUDENT_PROFILE_VIEW, "Update Student Profile", new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        if (key.equals("updateStudent")) {
                            return updateStudent;
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
    public void refreshTable(ActionEvent event) {
        studentTableView.getItems().clear();
        studentObservableList.setAll(StudentRepository.getInstance().findAll());
    }

    @FXML
    void removeStudent(ActionEvent event) {
        Student removeStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (removeStudent != null) {
            boolean confirmation = AlertMaker.getConfirmation("Delete Student", "Are you sure to delete student " + removeStudent.getStudentID() + " ?");
            if (confirmation) {
                if (removeStudent instanceof AnnualStudent) {
                    AnnualClassRepository.getInstance().removeAStudentFromClass((AnnualStudent) removeStudent);
                }
                if (removeStudent instanceof CreditStudent) {
                    CreditClassRepository.getInstance().removeAStudentFromClass((CreditStudent) removeStudent);
                }
                StudentRepository.getInstance().delete(removeStudent);
                UserRepository.getInstance().deleteByID(String.valueOf(removeStudent.getStudentID()));

                refreshTable(event);
                AlertMaker.showNotification("Deleted", "Student info deleted successfully", AlertMaker.image_trash_can);
            }
        } else {
            AlertMaker.showNotification("Error", "No  Student Selected", AlertMaker.image_cross);
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
