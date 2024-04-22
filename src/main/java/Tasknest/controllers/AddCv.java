package Tasknest.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import Tasknest.services.CvService;
import Tasknest.models.CV;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddCv {

    @FXML
    private TextField bioId;
    @FXML
    private TextField descriptionId;
    @FXML
    private CheckBox arabicCheckbox;
    @FXML
    private CheckBox frenchCheckbox;
    @FXML
    private CheckBox englishCheckbox;
    @FXML
    private CheckBox germanCheckbox;
    @FXML
    private TextField locationId;
    @FXML
    private TextField certificationId;
    @FXML
    private TextField contactId;
    @FXML
    private TextField user_idId;
    @FXML
    private TextField userNameId;
    CvService ps= new CvService();
    // Method to initialize the interface
    public void initialize() {
        try {
            // Fetch the user name using the provided user ID
            String userName = ps.fetchUserNameById((50)); // Assuming user ID is 24

            // Set the fetched user name in the user name field
            userNameId.setText(userName);
        } catch (SQLException e) {
            // Show error message for SQL exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error occurred while fetching user name.");
            alert.showAndWait();
        }
    }


    // You can create a method to retrieve the selected languages
   /* public String[] getSelectedLanguages() {
        // Assuming you want to return an array of language codes
        // Modify this according to your needs
        return new String[]{
                arabicCheckbox.isSelected() ? "Arabic" : "",
                frenchCheckbox.isSelected() ? "French" : "",
                englishCheckbox.isSelected() ? "English" : "",
                germanCheckbox.isSelected() ? "German" : ""
        };
    }*/
    public String[] getSelectedLanguages() {
        List<String> selectedLanguagesList = new ArrayList<>();

        if (arabicCheckbox.isSelected()) {
            selectedLanguagesList.add("Arabic");
        }
        if (frenchCheckbox.isSelected()) {
            selectedLanguagesList.add("French");
        }
        if (englishCheckbox.isSelected()) {
            selectedLanguagesList.add("English");
        }
        if (germanCheckbox.isSelected()) {
            selectedLanguagesList.add("German");
        }

        // Convert the list to an array
        return selectedLanguagesList.toArray(new String[0]);
    }



    @FXML
    void AjouterCV(ActionEvent event) {
        // Validate input fields
        if (!validateInput()) {
            return; // Return if validation fails
        }

        // Create a CV object with attributes
        CV cv = new CV(bioId.getText(), descriptionId.getText(), getSelectedLanguages(), locationId.getText(), certificationId.getText(), contactId.getText(), 50); // Assuming user ID is stored as an integer
        // Add the CV using the service
        ps.ajouter(cv);
        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CV Added");
        alert.setContentText("CV added successfully");
        alert.showAndWait();
        // Navigate to next page
        navigateToNextPage();
    }

   /* @FXML
    void AjouterCV(ActionEvent event) {
        try {
            // Create a CV object with attributes
            CV cv = new CV(bioId.getText(), descriptionId.getText(), getSelectedLanguages(), locationId.getText(), certificationId.getText(), contactId.getText(), 50); // Assuming user ID is stored as an integer
            // Add the CV using the service
            ps.ajouter(cv);
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CV Added");
            alert.setContentText("CV added successfully");
            alert.showAndWait();
            // Navigate to next page
            navigateToNextPage();
        } catch (NumberFormatException e) {
            // Show error message for number format exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a valid user ID.");
            alert.showAndWait();
        }
    }*/
    private boolean validateInput() {
        // Check if any of the required fields are empty
        if (bioId.getText().isEmpty() || descriptionId.getText().isEmpty() || locationId.getText().isEmpty() ||
                certificationId.getText().isEmpty() || contactId.getText().isEmpty() || userNameId.getText().isEmpty()) {
            showAlert("Error", "All fields are required.");
            return false;
        }

        // Check if user ID exists in CV table
        try {
            //int userId = Integer.parseInt(user_idId.getText());
            if (ps.userExistsInCVTable(50)) {
                showAlert("Error", "User already has a CV.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid user ID format.");
            return false;
        } catch (SQLException e) {
            showAlert("Error", "Error occurred while checking user ID.");
            return false;
        }

        // If all validations pass, return true
        return true;
    }

    // Helper method to show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void NextPage(ActionEvent event) {
        navigateToNextPage();
    }

    // Method to navigate to the next page
    private void navigateToNextPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCV.fxml"));
            Parent root = loader.load();
            bioId.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
