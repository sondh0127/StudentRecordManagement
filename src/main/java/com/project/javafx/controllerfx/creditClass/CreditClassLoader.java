package com.project.javafx.controllerfx.creditClass;

import com.project.javafx.ulti.ViewConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreditClassLoader extends Application {
    @Override
    public void start(Stage loaderStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewConstants.CREDIT_CLASS));

        Scene scene = new Scene(root);

        loaderStage.setScene(scene);
//        loaderStage.setTitle("Course");
        loaderStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
