package com.project.javafx.controllerfx.grade;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.Course;
import com.project.javafx.model.CreditClass;
import com.project.javafx.model.Student;
import com.project.javafx.model.StudentResult;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    private ArrayList<GradeModel> gradeModels = new ArrayList<>();
    private ObservableList<GradeModel> gradeObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TextField txtClassCode;

    @FXML
    private Label lblClass;

    @FXML
    private Label lblClassCode;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblCourseCode;

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
    public void refreshTable() {
        tblGrade.getItems().clear();
        lblClassCode.setText("");
        lblClass.setVisible(false);
        lblClassCode.setVisible(false);

        lblCourseName.setText("");
        lblCourseCode.setText("");
        initData();
        System.out.println(gradeModels.size());
    }

    @FXML
    void updateMark(ActionEvent event) {
        try {
            for (GradeModel gradeModel : gradeObservableList) {
                Long studentID = gradeModel.getStudentID();
                Student student = StudentRepository.getInstance().findById(studentID);
                student.updateStudentResult(gradeModel.getCourse(), gradeModel.getMidTermPoint(), gradeModel.getFinalPoint());
                StudentRepository.getInstance().update(student);
            }
            AlertMaker.showNotification("Done", "Update mark successfully !", AlertMaker.image_checked);
            initData();
            if (txtClassCode.getText().isEmpty()) {
                handleSearchByCourse(event);
            } else if (txtCourseCode.getText().isEmpty()) {
                handleSearchByClass(event);
            }
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Mark error", e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCols();
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

        TableRowExpanderColumn<GradeModel> expander = new TableRowExpanderColumn<>(this::createEditor);

        tblGrade.getColumns().addAll(expander);

        tblGrade.setItems(gradeObservableList);
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
//        colMidterm.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        colMidterm.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            GradeModel gradeModel = event.getTableView().getItems().get(row);

            double midtermPoint = event.getNewValue().doubleValue();
            try {
                if (midtermPoint > 10 || midtermPoint < 0)
                    throw new IllegalArgumentException("Invalid Score. Must be in range (0-10)");
                else {
                    gradeModel.setMidTermPoint(midtermPoint);
                }
            } catch (IllegalArgumentException e) {
                AlertMaker.showErrorMessage("Error Grade", e.getMessage());
                handleSearchAction(event);
            }
        });
//        colFinal.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        colFinal.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            GradeModel gradeModel = event.getTableView().getItems().get(row);
            double finalPoint = event.getNewValue().doubleValue();
            try {
                if (finalPoint > 10 || finalPoint < 0) {
                    throw new IllegalArgumentException("Invalid Score. Must be in range (0-10)");
                } else {
                    gradeModel.setFinalPoint(finalPoint);
                }
            } catch (IllegalArgumentException e) {
                AlertMaker.showErrorMessage("Error Grade", e.getMessage());
                handleSearchAction(event);
            }
        });
    }

    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<GradeModel> param) {
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        GradeModel gradeModel = param.getValue();

        TextField txtMidPoint = new TextField(gradeModel.getMidTermPoint().toString());
        TextField txtFinalPoint = new TextField(gradeModel.getFinalPoint().toString());

        editor.addRow(0, new Label("Midterm"), txtMidPoint);
        editor.addRow(1, new Label("Final"), txtFinalPoint);

        Button save = new Button("Save");
        save.setOnAction(event -> {
            try {
                double midtermPoint = Double.parseDouble(txtMidPoint.getText());
                double finalPoint = Double.parseDouble(txtFinalPoint.getText());
                if (midtermPoint > 10 || midtermPoint < 0 || finalPoint > 10 || finalPoint < 0)
                    throw new IllegalArgumentException("Score must be in range (0-10) !");
                else {
                    gradeModel.setMidTermPoint(midtermPoint);
                    gradeModel.setFinalPoint(finalPoint);
                    gradeModel.calculateScore();
                }
            } catch (NumberFormatException e) {
                AlertMaker.showErrorMessage("Invalid Number", "Mark is not a number! ");
            } catch (IllegalArgumentException e) {
                AlertMaker.showErrorMessage("Invalid Score", e.getMessage());
            }
            param.toggleExpanded();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e1 -> param.toggleExpanded());

        editor.addRow(2, save,cancel);
        return editor;

    }

    @FXML
    void handleSearchAction(Event event) {
        try {
            if (event.getSource().equals(txtCourseCode)) {
                txtClassCode.setText("");
                lblClass.setVisible(false);
                lblClassCode.setVisible(false);
                handleSearchByCourse(event);
            } else if (event.getSource().equals(txtClassCode)) {
                txtCourseCode.setText("");
                lblClass.setVisible(true);
                lblClassCode.setVisible(true);
                handleSearchByClass(event);
            }
        } catch (IllegalArgumentException e) {
            AlertMaker.showErrorMessage("Error!", e.getMessage());
        }
    }

    private void handleSearchByCourse(Event event) {
        ObservableList<GradeModel> temp = FXCollections.observableArrayList();
        String courseCode = txtCourseCode.getText().toUpperCase();
        boolean found = false;
        String courseName = "";
        for (GradeModel gradeModel : gradeModels) {
            if (gradeModel.getCourse().getCourseCode().equals(courseCode)) {
                temp.add(gradeModel);
                courseName = gradeModel.getCourse().getCourseName();
                found = true;
            }
        }
        if (!found) throw new IllegalArgumentException("Could not found course or " +
                courseCode + " has no register");
        else {
            lblCourseName.setText(courseName);
            lblCourseCode.setText(courseCode);
            gradeObservableList.clear();
            gradeObservableList.addAll(temp);
        }
    }

    private void handleSearchByClass(Event event) {
        ObservableList<GradeModel> temp = FXCollections.observableArrayList();
        String classCode = txtClassCode.getText().toUpperCase();
        CreditClass creditClass = CreditClassRepository.getInstance().findById(classCode);

        if (creditClass == null) {
            throw new IllegalArgumentException("Could not found class !");
        } else {
            lblClassCode.setText(creditClass.getClassCode());
            lblCourseName.setText(creditClass.getCourse().getCourseName());
            lblCourseCode.setText(creditClass.getCourse().getCourseCode());
            for (GradeModel gradeModel : gradeModels) {
                if (gradeModel.getCourse().equals(creditClass.getCourse())) {
                    temp.add(gradeModel);
                }
            }
            gradeObservableList.clear();
            gradeObservableList.addAll(temp);
        }
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

        public void setMidTermPoint(Double midTermPoint) {
            this.midTermPoint = midTermPoint;
        }

        public Double getFinalPoint() {
            return finalPoint;
        }

        public void setFinalPoint(Double finalPoint) {
            this.finalPoint = finalPoint;
        }

        public Double getTotalPoint() {
            return totalPoint;
        }

        public Course getCourse() {
            return course;
        }

        private void calculateScore() {
            double scale = course.getScale();
            BigDecimal scaleBD = BigDecimal.valueOf(scale);
            double _scale = BigDecimal.ONE.subtract(scaleBD).doubleValue();
            if (midTermPoint != -1 && finalPoint != -1) {
                double result = midTermPoint * scale + finalPoint * _scale;
                totalPoint =  Math.round(result * 10.0) / 10.0;
            }
        }
    }


}
