package com.tasknest.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Label reclambutton;



    @FXML
    void navigreclam(MouseEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/complaint.fxml"));
            Parent root = fxmlLoader.load();

            // Get the reference to the current scene
            Scene scene = reclambutton.getScene();

            // Set the root of the scene to the content loaded from complaint.fxml
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
