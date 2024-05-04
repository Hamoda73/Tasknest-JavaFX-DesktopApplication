package tasknest.controllers.applications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasknest.models.Application;
import tasknest.models.offers;
import tasknest.models.users;
import tasknest.services.ApplicationService;
import tasknest.services.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Apply{

    @FXML
    private  TextField firstNameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private TextField phoneNumberLabel;

    @FXML
    private TextField cvField;

    private ApplicationService applicationService;
    private UserService userService;


    private int offer_Id;
    @FXML
    private ImageView cvImageView;
    private int user_Id=25;           //freelancer same in offerApps**********************
    public void setUserAndOfferIds( int offerId) {

         offer_Id = offerId;
        System.out.println("hyyyyyyy"+offer_Id);
       initialize();
    }
    public void initialize() {
        applicationService = new ApplicationService();
        userService = new UserService();

        // Fetch user information

            users user = userService.getUserById(user_Id);
            if (user != null) {
                firstNameLabel.setText(user.getFname());
                lastNameLabel.setText(user.getLname());
                emailLabel.setText(user.getEmail());
                phoneNumberLabel.setText(String.valueOf(user.getPhonenumber()));
            }

    }



    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void uploadCV(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();


        fileChooser.setTitle("Choose PDF File");


        File selectedFile = fileChooser.showOpenDialog(null);

        // If a file is selected
        if (selectedFile != null) {
            // Get the file name
            String fileName = selectedFile.getName();


            if (fileName.toLowerCase().endsWith(".pdf")) {

                String filePath = selectedFile.getAbsolutePath();


                cvField.setText(filePath);
            } else {

                showAlert(Alert.AlertType.ERROR, "Error", "Invalid File Type", "Please select a PDF file.");
            }
        }
    }



   @FXML
   private void applyForOffer(ActionEvent event) {

       String cvContent = cvField.getText();
       String cvImagePath = cvField.getText();

       // Validate input fields
       if (cvContent.isEmpty() || cvImagePath.isEmpty()) {
           showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Form", "Please upload your CV.");


           return;
       }

       // Check if an application already exists for this user and offer
       if (applicationService.applicationExists(user_Id, offer_Id)) {
           showAlert(Alert.AlertType.ERROR, "Error", "Duplicate Application", "You have already applied for this offer.");

           navigateUserApp(event);
           return;
       }

       // Create a new Application object
       Application application = new Application(offer_Id, user_Id, cvImagePath);

       // Add the application to the database
       applicationService.ajouter(application);

       // Show success message
       showAlert(Alert.AlertType.INFORMATION, "Success", "Application Submitted", "Your application has been submitted successfully.");
       cvField.clear();
       navigateUserApp(event);

   }


   @FXML
    private void navigateAlloffers(MouseEvent event) {
        try {
            // Load the FXML file of the DisplayAllOffers view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateUserApps(MouseEvent mouseEvent){

        navigateUserAppss(mouseEvent,user_Id,offer_Id )  ;

    }

    public void navigateUserAppss(MouseEvent mouseEvent,int user_Id,int offer_Id ) {
        try {
            System.out.println("user_Idaz= "+user_Id);
            // Load the FXML file of the DisplayAllOffers view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/seeMyApps.fxml"));
            Parent root = loader.load();
            System.out.println("user_Idaz= "+user_Id);
            // Create a new scene
            Scene scene = new Scene(root);
            seeMyApps seeMyAppsController = loader.getController();
            seeMyAppsController.setUserApps(user_Id,offer_Id);
            // Get the current stage
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void navigateUserApp(ActionEvent event){

        navigateUserAppsss(event,user_Id,offer_Id )  ;

    }
    public void navigateUserAppsss(ActionEvent event,int user_Id,int offer_Id ) {
        try {
            System.out.println("user_Idaz= "+user_Id);
            // Load the FXML file of the DisplayAllOffers view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/seeMyApps.fxml"));
            Parent root = loader.load();
            System.out.println("user_Idaz= "+user_Id);
            // Create a new scene
            Scene scene = new Scene(root);
            seeMyApps seeMyAppsController = loader.getController();
            seeMyAppsController.setUserApps(user_Id,offer_Id);
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
