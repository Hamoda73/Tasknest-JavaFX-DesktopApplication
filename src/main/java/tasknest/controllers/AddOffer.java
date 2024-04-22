// AddOfferController.java
package tasknest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tasknest.models.offers;
import tasknest.services.OfferService;

import java.time.LocalDate;

public class AddOffer {


    @FXML
    private TextField entrepriseNameField;

    @FXML
    private TextField domainField;

    @FXML
    private TextField postField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField localisationField;

    @FXML
    private TextField periodField;

    @FXML
    private TextField salaryField;

    @FXML
    private Button submitButton;

    private OfferService offerService;

    @FXML
    private void initialize() {
        offerService = new OfferService();

        String constantImagePath = "file:C:/Users/21694/Desktop/planet.png";
        ImageView ImageView = new ImageView(new Image(constantImagePath));
        ImageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        ImageView.setFitHeight(200); // Set height to 200 (adjust as needed)
    }


    @FXML
    private void addOffer() {
        // Get the text from all text fields
        String entrepriseName = entrepriseNameField.getText();
        String domain = domainField.getText();
        String post = postField.getText();
        String description = descriptionField.getText();
        String localisation = localisationField.getText();
        String period = periodField.getText();
        String salaryText = salaryField.getText();

        // Check if any of the required fields are empty
        if (entrepriseName.isEmpty() || domain.isEmpty() || post.isEmpty() || description.isEmpty() || localisation.isEmpty() || period.isEmpty() || salaryText.isEmpty()) {
            // If any field is empty, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
            return; // Exit the method
        }

        // Parse salary to float
        float salary = Float.parseFloat(salaryText);

        // Create the offer object
        offers newOffer = new offers(entrepriseName, domain, post, description, localisation, period, salary, 1); // Assuming user_id is 1 for now

        // Add the offer
        offerService.ajouter(newOffer);
        clearFields();

        // Show a confirmation message
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Success");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Offer added successfully!");
        confirmation.showAndWait();
    }



    private void clearFields() {
        entrepriseNameField.clear();
        domainField.clear();
        postField.clear();
        descriptionField.clear();
        localisationField.clear();
        periodField.clear();
        salaryField.clear();
    }


}
