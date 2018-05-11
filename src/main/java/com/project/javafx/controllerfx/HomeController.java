package com.project.javafx.controllerfx;

import com.jfoenix.controls.JFXButton;
import com.project.javafx.model.*;
import com.project.javafx.repository.AnnualClassRepository;
import com.project.javafx.repository.CreditClassRepository;
import com.project.javafx.repository.StudentRepository;
import com.project.javafx.ulti.AlertMaker;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable{

    @FXML
    private ImageView gear1;

    @FXML
    private ImageView gear2;

    @FXML
    private ImageView gear3;

    @FXML
    private JFXButton btnGraduate;

    private void rotategears()
    {
        RotateTransition rg1=new RotateTransition(Duration.seconds(5),gear1);
        rg1.setFromAngle(0);
        rg1.setToAngle(360);
        RotateTransition rg2=new RotateTransition(Duration.seconds(5),gear2);
        rg2.setFromAngle(360);
        rg2.setToAngle(0);
        RotateTransition rg3=new RotateTransition(Duration.seconds(5),gear3);
        rg3.setFromAngle(0);
        rg3.setToAngle(360);
        ParallelTransition pt=new ParallelTransition(rg1,rg2,rg3);
        pt.setCycleCount(ParallelTransition.INDEFINITE);
        pt.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rotategears();
    }

    Set<Student> noResult;

    public void updateStudy(ActionEvent event) {
        boolean confirmation = AlertMaker.getConfirmation("Closing year of study", "Are you sure to update this Year of Study");
        if (confirmation) {
            if (ableToUpdate()) {
                Set<AnnualClass> all = AnnualClassRepository.getInstance().findAll();
                for (AnnualClass annualClass : all) {
                    annualClass.updateStudyYear();
                }
                Set<CreditClass> all1 = CreditClassRepository.getInstance().findAll();
                for (CreditClass creditClass : all1) {
                    creditClass.clearClassEnrollment();
                    CreditClassRepository.getInstance().update(creditClass);
                }

                for (Student student : StudentRepository.getInstance().findAll()) {
                    if (student instanceof CreditStudent) {
                        ((CreditStudent) student).updatePassedCourseAll();
                        StudentRepository.getInstance().update(student);
                    }
                }
                AlertMaker.showNotification("Success", "Update successfully !", AlertMaker.image_checked);
            } else {
                ArrayList<String> stdID = noResult.stream()
                        .map(student -> String.valueOf(student.getStudentID()))
                        .collect(Collectors.toCollection(ArrayList::new));
                AlertMaker.showErrorMessage("Not able to update education info!", "Existence students do not have mark !\n" + stdID.toString());
            }
        }
    }

    private boolean ableToUpdate() {
        noResult = new HashSet<>();
        Set<Student> students = StudentRepository.getInstance().findAll();
        for (Student student : students) {
            List<StudentResult> takenResult = student.getTakenResult();
            takenResult.stream()
                    .filter(result -> result.getScore() < 0)
                    .forEach(result -> noResult.add(student));
        }
        return noResult.isEmpty();
    }
}
