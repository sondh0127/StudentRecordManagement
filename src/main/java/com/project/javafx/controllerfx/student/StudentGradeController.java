package com.project.javafx.controllerfx.student;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.AnnualStudent;
import com.project.javafx.model.CreditStudent;
import com.project.javafx.model.Student;
import com.project.javafx.model.StudentResult;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentGradeController implements Initializable {

    private Student student;

    private ObservableList<StudentResult> studentResultObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnBack;

    @FXML
    private TableView<StudentResult> tblGrade;

    @FXML
    private TableColumn<StudentResult, String> colCourseCode;

    @FXML
    private TableColumn<StudentResult, String> colCourseName;

    @FXML
    private TableColumn<StudentResult, Number> colMidterm;

    @FXML
    private TableColumn<StudentResult, Number> colFinal;

    @FXML
    private TableColumn<StudentResult, Number> colScore;

    @FXML
    private TableColumn<StudentResult, String> colScoreLetter;

    @FXML
    private Label lblName;

    @FXML
    private Label lblStudentID;

    @FXML
    private Label lblAvg;

    @FXML
    private Label lblAvgValue;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    private void initCols() {
        tblGrade.setItems(studentResultObservableList);
        colCourseCode.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleStringProperty(s.getCourse().getCourseCode());
        });
        colCourseName.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleStringProperty(s.getCourse().getCourseName());
        });
        colMidterm.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleDoubleProperty(s.getMidtermPoint());
        });
        colFinal.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleDoubleProperty(s.getFinalPoint());
        });

        colScore.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleDoubleProperty(s.getScore());
        });
        colScoreLetter.setCellValueFactory(param -> {
            StudentResult s = param.getValue();
            return new SimpleStringProperty(s.getLetterScore());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student = (Student) resources.getObject("student");

        studentResultObservableList.setAll(student.getPassedCourses());
        System.out.println(student);

        initCols();
        lblStudentID.setText(String.valueOf(student.getStudentID()));
        lblName.setText(student.getLastName() + " " + student.getFirstName());
        if (student instanceof CreditStudent) {
            colScoreLetter.setVisible(true);
            lblAvg.setText("Lastest GPA: ");
            lblAvgValue.setText(String.valueOf(((CreditStudent) student).getGPA()));
        }
        if (student instanceof AnnualStudent) {
            colScoreLetter.setVisible(false);
            lblAvg.setText("AVG: ");
            lblAvgValue.setText(String.valueOf(((AnnualStudent) student).getAVG()));
        }

    }
}
