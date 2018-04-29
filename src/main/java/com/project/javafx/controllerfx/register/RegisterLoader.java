package com.project.javafx.controllerfx.register;

import com.project.javafx.ulti.ViewConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterLoader extends Application {
    @Override
    public void start(Stage loaderStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewConstants.REGISTER_VIEW));

        Scene scene = new Scene(root);

        loaderStage.setScene(scene);

        loaderStage.show();
//        new Thread(MongoDBHandler::getInstance).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
