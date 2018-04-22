package com.project.javafx.controllerfx.course;

import com.project.javafx.ulti.ViewConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseLoader extends Application {
    @Override
    public void start(Stage loaderStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewConstants.COURSES_VIEW));

        Scene scene = new Scene(root);

        loaderStage.setScene(scene);
//        loaderStage.setTitle("Course");
        loaderStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
