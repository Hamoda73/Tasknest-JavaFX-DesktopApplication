package com.tasknest.controllers;

import com.tasknest.models.complaint;
import com.tasknest.models.respond;
import com.tasknest.services.ComplaintService;
import com.tasknest.services.RespondService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public class adminController {

    @FXML
    private TableView<complaint> admindatatable;

    @FXML
    private TextField adminresponse;

    @FXML
    private Button respondbutton;

    @FXML
    private Button updateadminbutton;

    @FXML
    private Button deleteadminbutton;

    @FXML
    private TextFlow adminmessage;

    @FXML
    private TextFlow admintype;

    @FXML
    private TableColumn<complaint, String> typecolumn;

    @FXML
    private TableColumn<complaint, String> messagecolumn;

    @FXML
    private TableColumn<complaint, String> responsecolumn;

    private complaint selectedComplaint;

    private ComplaintService database;

    RespondService respondService = new RespondService();
    private respond selectedRespond;

    @FXML
    public void initialize() {
        database = new ComplaintService();

        messagecolumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        typecolumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        responsecolumn.setCellValueFactory(new PropertyValueFactory<>("responseMessage"));
        // Load data into table
        loadData();

        // Set TableView selection listener
        admindatatable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<complaint>() {
            @Override
            public void changed(ObservableValue<? extends complaint> observable, complaint oldValue, complaint newValue) {
                if (newValue != null) {
                    selectedComplaint = newValue;
                    admintype.getChildren().clear(); // Clear existing content
                    admintype.getChildren().add(new Text(newValue.getType())); // Add new Text object with the updated type
                    adminmessage.getChildren().clear(); // Clear existing content
                    adminmessage.getChildren().add(new Text(newValue.getMessage())); // Add new Text object with the updated message
                    adminresponse.setText(newValue.getResponseMessage());
                    selectedRespond = newValue.getResponse();
                }
            }
        });


    }
    private void loadData() {
        List<complaint> complaints = database.afficher();
        admindatatable.getItems().addAll(complaints);
    }




    @FXML
    void deleterespondfunc(MouseEvent event) {

        if (selectedComplaint != null) {
            database.supprimer(selectedComplaint); // Implement delete method in ComplaintService
            // Refresh TableView
            refreshTable();
        }

    }


    @FXML
    void respondfunc(MouseEvent event) {
        String text = adminresponse.getText();

        // Check if response is empty or too short (less than 5 characters, for example)
        if (text.isEmpty() || text.length() < 5) {
            // Create and configure an alert
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Response");
            alert.setHeaderText(null);
            alert.setContentText("Response is empty or too short. Please provide a valid response.");

            // Show the alert dialog
            alert.showAndWait();

            return; // Exit the method if response is not valid
        }

        if (selectedComplaint != null) {
            int complaintId = selectedComplaint.getId(); // Assuming getId() returns the complaint ID
            respond com = new respond(complaintId, text);
            respondService.ajouter(com);
            System.out.println("Response added successfully!");
            // You may want to update the UI or provide feedback to the user here
        } else {
            System.out.println("No complaint selected.");
            // You may want to inform the user that they need to select a complaint before responding
        }
        refreshTable();
    }


    private void refreshTable() {
        // Clear TableView and reload data
        admindatatable.getItems().clear();
        loadData();
    }


    @FXML
    void updaterespondfunc(MouseEvent event) {
        // Get the updated message from the adminresponse TextField
        String updatedMessage = adminresponse.getText();

        // Check if the selected complaint is not null
        if (selectedComplaint != null) {
            // Update the response message of the selected complaint
            selectedComplaint.setResponseMessage(updatedMessage);

            // Call the modifier method in the ComplaintService to update the response in the database
            database.modifier(selectedComplaint);

            // Optionally, update the UI to reflect the changes
            adminmessage.getChildren().clear(); // Clear previous message
            Text updatedText = new Text(selectedComplaint.getResponseMessage());
            adminmessage.getChildren().add(updatedText); // Add updated message to adminmessage TextFlow

            // Clear the adminresponse TextField or perform any other necessary UI updates
            adminresponse.clear();
            admindatatable.refresh();

            System.out.println("Response updated successfully!");
        } else {
            // Handle the case where no complaint is selected
            System.out.println("No complaint selected.");
        }
    }










}
