package com.tasknest.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.tasknest.models.complaint;
import java.io.IOException;
import java.util.List;
import com.tasknest.services.ComplaintService;

public class consultController {

    @FXML
    private Hyperlink sendnav;

    @FXML
    private TableView<complaint> datatable;

    @FXML
    private TableColumn<complaint, String> typecolumn;

    @FXML
    private TableColumn<complaint, String> messagecolumn;

    @FXML
    private TableColumn<complaint, String> responsecolumn;

    @FXML
    private ComboBox<String> complainttype2;

    @FXML
    private TextField complaintmessage2;

    @FXML
    private Button updatecomplaint;

    @FXML
    private Button deletecomplaint;

    private complaint selectedComplaint;

    private ComplaintService database;

    @FXML
    public void initialize() {
        database = new ComplaintService();

        messagecolumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        typecolumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        responsecolumn.setCellValueFactory(new PropertyValueFactory<>("responseMessage"));
        // Load data into table
        loadData();

        // Set TableView selection listener
        datatable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<complaint>() {
            @Override
            public void changed(ObservableValue<? extends complaint> observable, complaint oldValue, complaint newValue) {
                if (newValue != null) {
                    selectedComplaint = newValue;
                    complainttype2.setValue(newValue.getType());
                    complaintmessage2.setText(newValue.getMessage());
                }
            }
        });


    }
    private void loadData() {
        List<complaint> complaints = database.afficher();
        datatable.getItems().addAll(complaints);
    }


    @FXML
    void sendnavfunc(MouseEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/complaint.fxml"));
            Parent root = fxmlLoader.load();

            // Get the reference to the current scene
            Scene scene = sendnav.getScene();

            // Set the root of the scene to the content loaded from complaint.fxml
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void updateComplaint(MouseEvent event) {
        if (selectedComplaint != null) {
            String newType = complainttype2.getValue();
            String newMessage = complaintmessage2.getText().trim(); // Trim whitespace
            if (newType == null || newType.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Complaint Type", "Please select a complaint type.");
                return;
            }
            if (newMessage.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Complaint Message", "Please enter a complaint message.");
                return;
            }
            // Update complaint details
            selectedComplaint.setType(newType);
            selectedComplaint.setMessage(newMessage);
            database.modifier(selectedComplaint); // Implement update method in ComplaintService
            // Refresh TableView
            refreshTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Complaint Updated", "The complaint has been updated successfully.");
        }
    }


    @FXML
    void deleteComplaint(MouseEvent event) {
        if (selectedComplaint != null) {
            database.supprimer(selectedComplaint); // Implement delete method in ComplaintService
            // Refresh TableView
            refreshTable();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    private void refreshTable() {
        // Clear TableView and reload data
        datatable.getItems().clear();
        loadData();
    }











}
