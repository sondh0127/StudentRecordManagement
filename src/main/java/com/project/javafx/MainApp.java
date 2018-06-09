package com.project.javafx;

import com.project.javafx.controllerfx.AdminDashboardController;
import com.project.javafx.controllerfx.UserDashboardController;
import com.project.javafx.repository.*;
import com.project.javafx.ulti.AlertMaker;
import com.project.javafx.ulti.ViewConstants;
import com.project.javafx.ulti.WorkIndicatorDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private Stage primaryStage;
    private WorkIndicatorDialog wd = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadWindow(ViewConstants.ADMIN_DASHBOARD_VIEW, ViewConstants.APP_NAME + " - Admin");
        wd = new WorkIndicatorDialog(primaryStage, "Loading Application Data...");

        wd.addTaskEndNotification(result -> {
            System.out.println(result);
            wd = null; // don't keep the object, cleanup
        });

        wd.exec("1", inputParam -> {
            // Thinks to do...
            CourseRepository.getInstance().getObjectCollection();
//        CourseRepository.getInstance().getObjectFromFile();
            CreditMajorRepository.getInstance().getObjectCollection();
//        CreditMajorRepository.getInstance().getObjectFromFile();
            StudentRepository.getInstance().getObjectCollection();
//        StudentRepository.getInstance().getObjectFromFile();
            AnnualClassRepository.getInstance().getObjectCollection();
//        AnnualClassRepository.getInstance().getObjectFromFile();
            CreditClassRepository.getInstance().getObjectCollection();
//        CreditClassRepository.getInstance().getObjectFromFile();
            UserRepository.getInstance().getObjectCollection();
//            UserRepository.getInstance().getObjectFromFile();
            // NO ACCESS TO UI ELEMENTS!
            return new Integer(1);
        });
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
        UserDashboardController.setMainApp(this);
    }

    void loadWindow(String location, String title) {
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Parent root = FXMLLoader.load(getClass().getResource(location), new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if (key.equals("stage"))
                        return stage;
                    return null;
                }

                @Override
                public Enumeration<String> getKeys() {
                    return null;
                }
            });


            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(ViewConstants.APP_ICON));
            stage.show();
        } catch (IOException e) {
            AlertMaker.showErrorMessage(e);
            e.printStackTrace();
        }
    }
}
