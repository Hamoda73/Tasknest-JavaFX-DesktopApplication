package tasknest.controllers.offer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tasknest.models.offers;
import tasknest.services.OfferService;

import java.io.IOException;
import java.util.regex.Pattern;

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
    private Button HomeButton;
    private OfferService offerService;
    private int static_user_id=35;

    @FXML
    private void initialize() {
        offerService = new OfferService();

        String constantImagePath = "file:C:/Users/21694/Desktop/loginpic.png";
        ImageView ImageView = new ImageView(new Image(constantImagePath));
        ImageView.setFitWidth(200);
        ImageView.setFitHeight(200);
    }

    @FXML
    private void addOffer() {

        int userId = static_user_id;

        String entrepriseName = entrepriseNameField.getText();
        String domain = domainField.getText();
        String post = postField.getText();
        String description = descriptionField.getText();
        String localisation = localisationField.getText();
        String period = periodField.getText();
        String salaryText = salaryField.getText();


        if (entrepriseName.isEmpty()) {
            showErrorAlert("Entreprise Name cannot be empty.");
            return;
        }

        if (domain.isEmpty()) {
            showErrorAlert("Domain cannot be empty.");
            return;
        }

        if (post.isEmpty()) {
            showErrorAlert("Post cannot be empty.");
            return;
        }

        if (description.isEmpty()) {
            showErrorAlert("Description cannot be empty.");
            return;
        }

        if (localisation.isEmpty()) {
            showErrorAlert("Localisation cannot be empty.");
            return;
        }

        if (period.isEmpty()) {
            showErrorAlert("Period cannot be empty.");
            return;
        }

        if (salaryText.isEmpty()) {
            showErrorAlert("Salary cannot be empty.");
            return;
        }


        if (!isValidInput(entrepriseName)) {
            showErrorAlert("Entreprise Name cannot contain special characters except spaces.");
            return;
        }

        if (!isValidInput(domain)) {
            showErrorAlert("Domain cannot contain special characters except spaces.");
            return;
        }

        if (!isValidInput(post)) {
            showErrorAlert("Post cannot contain special characters except spaces.");
            return;
        }

        if (!isValidInput(localisation)) {
            showErrorAlert("Localisation cannot contain special char    acters except spaces.");
            return;
        }

        if (!isValidInput(period)) {
            showErrorAlert("Period cannot contain special characters except spaces.");
            return;
        }


        float salary;
        try {
            salary = Float.parseFloat(salaryText);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid salary value. Please enter a valid number.");
            return;
        }


        offers newOffer = new offers(entrepriseName, domain, post, description, localisation, period, salary,userId); // Assuming user_id is 1 for now

        // Add the offer
        offerService.ajouter(newOffer);
        clearFields();

        //  confirmation message
        showConfirmationAlert("Offer added successfully!");

        //  next page
        navigateToNextPage();
    }

    private void navigateToNextPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/Displayoffuser.fxml"));
            Parent root = loader.load();
            domainField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Success");
        confirmation.setHeaderText(null);
        confirmation.setContentText(message);
        confirmation.showAndWait();
    }

    private boolean isValidInput(String input) {

        String regex = "^[a-zA-Z0-9 ]*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }



    @FXML
    private void navigateToDisplayAllOffers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            Parent root = loader.load();
            domainField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void navigateToDisplayuseroff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/Displayoffuser.fxml"));
            Parent root = loader.load();
            domainField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
