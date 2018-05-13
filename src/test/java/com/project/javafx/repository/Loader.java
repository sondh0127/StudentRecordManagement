package com.project.javafx.repository;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loader extends Application {
    @Override
    public void start(Stage loaderStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/project/javafx/repository/testspread.fxml"));

        Scene scene = new Scene(root);

        loaderStage.setScene(scene);

        loaderStage.show();
//        new Thread(MongoDBHandler::getInstance).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
