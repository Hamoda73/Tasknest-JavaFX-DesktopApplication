package tasknest.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tasknest.models.users;
import tasknest.services.AdminService;
import tasknest.services.UserService;
import tasknest.tests.MainFx;
import tasknest.utils.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import tasknest.models.userInfo;
public class DisplayAdminProfile {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Button DeleteAccountBtn;
    @FXML
    private Button LogoutBtn;
    @FXML
    private Button UpdateAccountBtn;

    private final AdminService adminService = new AdminService(); // Assuming UserService has method to fetch user details

    public users getLoggedInUser() {

        return MainFx.getSession();

    }

    @FXML
    public void initialize() {
        // Fetch the logged-in user details from your database
        users loggedInUser = getLoggedInUser(); // Assuming a method to get the logged-in user

        // Display user details in the labels
        if (loggedInUser != null) {
            firstNameLabel.setText(loggedInUser.getFname());
            lastNameLabel.setText(loggedInUser.getLname());
            emailLabel.setText(loggedInUser.getEmail());
            phoneNumberLabel.setText(String.valueOf(loggedInUser.getPhonenumber()));
        }
    }

    @FXML
    void LoginToUpdateAccount(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateAccount.fxml"));
            Parent root = loader.load();

            Scene scene = UpdateAccountBtn.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteAccount() {
        users loggedInUser = getLoggedInUser();

        if (loggedInUser != null) {
            // Call the delete method from your service class
            adminService.supprimer(loggedInUser);
            navigateToLogin();
        }
    }
    @FXML
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Login controller = loader.getController();

            // Get the current stage
            Stage stage = (Stage) DeleteAccountBtn.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Login controller = loader.getController();

            // Get the current stage
            Stage stage = (Stage) LogoutBtn.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
