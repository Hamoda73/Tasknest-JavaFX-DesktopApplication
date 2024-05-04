package tasknest.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class TaskNestMainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

     FXMLLoader loader=new FXMLLoader(getClass().getResource("/CV/showAllCVS.fxml"));
        // FXMLLoader loader=new FXMLLoader(getClass().getResource("/CV/showAllCVsBACK.fxml"));

        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("TaskNest");
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
