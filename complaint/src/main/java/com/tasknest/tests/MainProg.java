package com.tasknest.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainProg extends Application {

        public static void main(String[] args) {
                launch(args);
        }


        @Override
        public void start(Stage primaryStage) throws IOException {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
                Parent root = fxmlLoader.load();

                // Set up the scene
                Scene scene = new Scene(root, 1340, 700);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Tasknest");
                primaryStage.show();
        }


}
