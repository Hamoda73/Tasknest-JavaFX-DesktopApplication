package tasknest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tasknest.models.users;
import tasknest.services.AdminService;
import tasknest.services.FreelancerService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddFreelancer {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker myDatePicker;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField profileImageField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button submitButton;
    private FreelancerService freelancerService;

    public void getDate(ActionEvent actionEvent) {
        LocalDate myDate = myDatePicker.getValue();
    }

    @FXML
    private void initialize() {
        freelancerService = new FreelancerService();
        String constantImagePath = "file:C:/Users/21694/Desktop/planet.png";
        ImageView ImageView = new ImageView(new Image(constantImagePath));
        ImageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        ImageView.setFitHeight(200); // Set height to 200 (adjust as needed)
    }


    @FXML
    private void addAdmin() {
        // Get the text from all text fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String profileImage = profileImageField.getText();
        String password = passwordField.getText();
        LocalDate birthdate = myDatePicker.getValue();

        // Check if any of the required fields are empty
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || profileImage.isEmpty() || password.isEmpty() || birthdate.toString().isEmpty()) {
            // If any field is empty, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
            return; // Exit the method
        }

        int PhoneNum = Integer.parseInt(phoneNumber);
        Date dateOfBirth = Date.from(birthdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Create the admin object
        users newFreelancer = new users( email,  firstName,  lastName,  PhoneNum,  dateOfBirth,  "ROLE_FREELANCER",  password,  profileImage,  false);

        // Add the offer
        freelancerService.ajouter(newFreelancer);
        clearFields();

        // Show a confirmation message
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Success");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Freelancer added successfully!");
        confirmation.showAndWait();
    }



    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        myDatePicker = null;
        phoneNumberField.clear();
        profileImageField.clear();
        passwordField.clear();
        emailField.clear();
    }
}
