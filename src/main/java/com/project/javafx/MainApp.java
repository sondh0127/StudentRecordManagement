package com.project.javafx;

import com.project.javafx.controllerfx.AdminDashboardController;
import com.project.javafx.repository.*;
import com.project.javafx.ulti.ViewConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        showLoginStage();
    }

    public void showLoginStage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ViewConstants.LOGIN_VIEW));
        Scene scene = new Scene(root);

        primaryStage.setTitle(ViewConstants.APP_NAME);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(ViewConstants.APP_ICON));
        primaryStage.show();
        createReference();
    }

    private void createReference() {
        AdminDashboardController.setMainApp(this);
    }


    public static void main(String[] args) {
        // fix thứ tự
//        CourseRepository.getInstance().getObjectCollection();
        CourseRepository.getInstance().getObjectFromFile();
        CreditMajorRepository.getInstance().getObjectCollection();
        StudentRepository.getInstance().getObjectCollection();
//        StudentRepository.getInstance().getObjectCollection();

        AnnualClassRepository.getInstance().getObjectCollection();
        CreditClassRepository.getInstance().getObjectCollection();
        UserRepository.getInstance().getObjectCollection();

        launch(args);
    }
}
