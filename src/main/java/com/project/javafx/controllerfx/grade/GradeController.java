package com.project.javafx.controllerfx.grade;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.Student;
import com.project.javafx.model.StudentResult;
import com.project.javafx.repository.StudentRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    private ArrayList<GradeModel> gradeModels = new ArrayList<>();
    private ObservableList<GradeModel> gradeObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField txtCourseCode;

    @FXML
    private Label labelCourse;

    @FXML
    private TableView<GradeModel> tblGrade;

    @FXML
    private TableColumn<GradeModel, Number> colStudentID;

    @FXML
    private TableColumn<GradeModel, String> colFullName;

    @FXML
    private TableColumn<GradeModel, Number> colMidterm;

    @FXML
    private TableColumn<GradeModel, Number> colFinal;

    @FXML
    private TableColumn<GradeModel, Number> colScore;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    void refreshTable() {
        tblGrade.getItems().clear();
        initData();
        System.out.println(gradeModels.size());
    }

    @FXML
    void updateMark(ActionEvent event) {
        for (GradeModel gradeModel : gradeObservableList) {
            Long studentID = gradeModel.getStudentID();
            Student student = StudentRepository.getInstance().findById(studentID);
            student.updateStudentResult(gradeModel.getCourse(), gradeModel.getMidTermPoint(), gradeModel.getFinalPoint());
            StudentRepository.getInstance().update(student);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
        tblGrade.setItems(gradeObservableList);
        refreshTable();

    }

    private void initData() {
        gradeModels.clear();
        for (Student s : StudentRepository.getInstance().findAll()) {
            long studentID = s.getStudentID();
            String fullName = s.getLastName() + " " + s.getFirstName();
            for (StudentResult result : s.getTakenResult()) {
                Course course = result.getCourse();
                Double midtermPoint = result.getMidtermPoint();
                Double finalPoint = result.getFinalPoint();
                Double score = result.getScore();
                gradeModels.add(new GradeModel(studentID, fullName, course, midtermPoint, finalPoint, score));
            }
        }
    }

    private void initCols() {
        colStudentID.setCellValueFactory(param -> {
            GradeModel s = param.getValue();
            return new SimpleLongProperty(s.getStudentID());
        });
        colFullName.setCellValueFactory(param -> {
            GradeModel s = param.getValue();
            return new SimpleStringProperty(s.getStudentName());
        });
        colMidterm.setCellValueFactory(param -> {
            GradeModel s = param.getValue();
            return new SimpleDoubleProperty(s.getMidTermPoint());
        });
        colFinal.setCellValueFactory(param -> {
            GradeModel s = param.getValue();
            return new SimpleDoubleProperty(s.getFinalPoint());
        });

        colScore.setCellValueFactory(param -> {
            GradeModel s = param.getValue();
            return new SimpleDoubleProperty(s.getTotalPoint());
        });
        colMidterm.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        colMidterm.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            GradeModel gradeModel = event.getTableView().getItems().get(row);
            gradeModel.setMidTermPoint(event.getNewValue().doubleValue());
        });

        colFinal.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        colFinal.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            GradeModel gradeModel = event.getTableView().getItems().get(row);
            gradeModel.setFinalPoint(event.getNewValue().doubleValue());
        });
    }

    @FXML
    void handleSearchAction(Event event) {
        ObservableList<GradeModel> temp = FXCollections.observableArrayList();
        String courseCode = txtCourseCode.getText().toUpperCase();
        System.out.println(courseCode);
        for (GradeModel gradeModel : gradeModels) {
            if (gradeModel.getCourse().getCourseCode().equals(courseCode)) {
                temp.add(gradeModel);
            }
        }
        gradeObservableList.clear();
        gradeObservableList.addAll(temp);
    }


    public class GradeModel {

        private final Long studentID;
        private final String studentName;
        private final Course course;
        private Double midTermPoint;
        private Double finalPoint;
        private Double totalPoint;

        public GradeModel(Long studentID, String studentName, Course course, Double midTermPoint, Double finalPoint, Double totalPoint) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.course = course;
            this.midTermPoint = midTermPoint;
            this.finalPoint = finalPoint;
            this.totalPoint = totalPoint;
        }

        public Long getStudentID() {
            return studentID;
        }

        public String getStudentName() {
            return studentName;
        }

        public Double getMidTermPoint() {
            return midTermPoint;
        }

        public Double getFinalPoint() {
            return finalPoint;
        }

        public Double getTotalPoint() {
            return totalPoint;
        }

        public Course getCourse() {
            return course;
        }

        public void setMidTermPoint(Double midTermPoint) {
            this.midTermPoint = midTermPoint;
        }

        public void setFinalPoint(Double finalPoint) {
            this.finalPoint = finalPoint;
        }
    }


}
