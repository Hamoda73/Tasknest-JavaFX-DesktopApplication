package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainProg extends Application {



    Parent root1 = FXMLLoader.load(getClass().getResource("categories.fxml"));
    private Scene categoriesScene = new Scene(root1);



    public MainProg() throws IOException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(categoriesScene);
        stage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}