package com.project.javafx.controllerfx.course;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.javafx.model.Course;
import com.project.javafx.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterCourseController implements Initializable {
    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTextField courseField;

    @FXML
    private JFXTextField studentField;

    private AutoCompletionBinding<Student> autoCompletionStudent;
    private AutoCompletionBinding<Course> autoCompletionCourse;

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnBack.getScene().getWindow()).close();
    }

    // TODO: 28/03/2018 make this right 
    @FXML
    void submitEnroll(ActionEvent event) {
//        Student student = StudentOperations.getInstance().getData(studentField.getText());
//        Course course = null;
//        Registration newRegistration = null;
//        String courseCode = courseField.getText();
//        if (student instanceof CreditStudent) {
//            course = ((CreditStudent) student).getMajor().getMajorCatalog().getCourse(courseCode);
//        } else if (student instanceof FixedStudent) {
//            course = ((FixedStudent) student).getFixedClass().getCoursesCatalog().getCourse(courseCode);
//        }
//        if (course != null) {
//            // FIXME: 28/03/2018 exception
//            newRegistration = new Registration(Registration.numOfReg,student,course);
//            RegistrationOperations.getInstance().addData(newRegistration);
//        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTextField();
    }

    private void initTextField() {
//        autoCompletionStudent = TextFields.bindAutoCompletion(
//                studentField,
//                StudentOperations.getInstance().getDataList().toArray(new Student[0]));
//
//        studentField.textProperty().addListener((observable, oldValue, newValue) -> {
//            Student student = StudentOperations.getInstance().getData(newValue);
//            if (student instanceof CreditStudent) {
//                autoCompletionCourse = TextFields.bindAutoCompletion(
//                        courseField,
//                        ((CreditStudent) student).getMajor().getMajorCatalog().getCourseDataSet().toArray(new Course[0]));
//            } else if (student instanceof FixedStudent) {
//                autoCompletionCourse = TextFields.bindAutoCompletion(
//                        courseField,
//                        ((FixedStudent) student).getFixedClass().getCoursesCatalog().getCourseDataSet().toArray(new Course[0]));
//            }
//        });



        // TODO: 28/03/2018 add learning word (needed ??)
//        studentField.setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case ENTER:
//                    autoCompletionLearnWord(studentField.getText().trim());
//                    break;
//                default:
//                    break;
//
//            }
//        });

    }



}
