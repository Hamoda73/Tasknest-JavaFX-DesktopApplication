package tasknest.controllers.offer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.events.MouseEvent;
import tasknest.controllers.applications.Apply;
import tasknest.controllers.applications.OfferApps;
import tasknest.models.offers;
import tasknest.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Displayoffuser  {

    @FXML
    private ScrollPane offersScrollPane;

    private OfferService offerService;

    // Static user ID variable
    private  int STATIC_USER_ID = 2;

//for the edit

    private offers offer;

    public void initialize() {
        offerService = new OfferService();
        populateOffers();
        System.out.println("helloooooooooooo");


    }
//users offers
   /* private void populateOffers() {
        // Fetch offers posted by the static user ID
        List<offers> userOffers = offerService.afficheruseroffers(STATIC_USER_ID);

        VBox offersContainer = new VBox(10);

        for (offers offer : userOffers) {
            AnchorPane card = createOfferCard(offer);
            offersContainer.getChildren().add(card);
        }
       // offersContainer.getChildren().clear();
        offersScrollPane.setContent(offersContainer);
    }*/


    private void populateOffers() {
        // Fetch offers posted by the static user ID
        List<offers> userOffers = offerService.afficheruseroffers(STATIC_USER_ID);

        VBox offersContainer = new VBox(10);

        if (userOffers.isEmpty()) {
            // Display a message when there are no offers available
            AnchorPane noOffersPane = new AnchorPane();
            Label noOffersLabel = new Label("No offers yet");
            noOffersLabel.getStyleClass().add("no-offers-label");
            AnchorPane.setTopAnchor(noOffersLabel, 200.0);
            AnchorPane.setBottomAnchor(noOffersLabel, 200.0);
            AnchorPane.setLeftAnchor(noOffersLabel, 450.0);
            AnchorPane.setRightAnchor(noOffersLabel, 300.0);
            noOffersPane.getChildren().add(noOffersLabel);
            offersContainer.getChildren().add(noOffersPane);
        } else {
            // Populate offers if available
            for (offers offer : userOffers) {
                AnchorPane card = createOfferCard(offer);
                offersContainer.getChildren().add(card);
            }
        }

        offersScrollPane.setContent(offersContainer);
    }







    private AnchorPane createOfferCard(offers offer) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("offer-card");




        ImageView imageView = new ImageView();
        String imageUrl = getClass().getResource("/taswira.png").toExternalForm(); // Path to your image resource
        Image image = new Image(imageUrl);

        imageView.setImage(image);
        imageView.getStyleClass().add("image-view");

        Label namePrefixLabel = new Label("Company: ");
        namePrefixLabel.setLayoutX(300);
        namePrefixLabel.setLayoutY(10);
        namePrefixLabel.setStyle(" -fx-text-fill: #ea8d22;");

        Label nameLabel = new Label(offer.getEntreprise_name());
        nameLabel.setLayoutX(420);
        nameLabel.setLayoutY(10);

        Label DomainPrefixLabel = new Label("Domain: ");
        DomainPrefixLabel.setLayoutX(300);
        DomainPrefixLabel.setLayoutY(50);
        DomainPrefixLabel.setStyle(" -fx-text-fill: #892193FF;");

        Label DomainLabel = new Label(offer.getDomain());
        DomainLabel.setLayoutX(420);
        DomainLabel.setLayoutY(50);

        Label postPrefixLabel = new Label("Post: ");
        postPrefixLabel.setLayoutX(300);
        postPrefixLabel.setLayoutY(90);
        postPrefixLabel.setStyle(" -fx-text-fill: #892193FF;");

        Label postLabel = new Label(offer.getPost());
        postLabel.setLayoutX(420);
        postLabel.setLayoutY(90);

        Label descriptionPrefixLabel = new Label("Description: ");
        descriptionPrefixLabel.setLayoutX(300);
        descriptionPrefixLabel.setLayoutY(120);
        descriptionPrefixLabel.setStyle(" -fx-text-fill: #892193FF;");

        Label descriptionLabel = new Label(offer.getDescription());
        descriptionLabel.setLayoutX(420);
        descriptionLabel.setLayoutY(120);

        Label localisationPrefixLabel = new Label("Location: ");
        localisationPrefixLabel.setLayoutX(300);
        localisationPrefixLabel.setLayoutY(160);
        localisationPrefixLabel.setStyle(" -fx-text-fill: #892193FF;");

        Label localisationLabel = new Label(offer.getLocalisation());
        localisationLabel.setLayoutX(420);
        localisationLabel.setLayoutY(160);

        Label periodPrefixLabel = new Label("Period: ");
        periodPrefixLabel.setLayoutX(300);
        periodPrefixLabel.setLayoutY(200);
        periodPrefixLabel.setStyle(" -fx-text-fill: #892193FF;");

        Label periodValueLabel = new Label(offer.getPeriod());
        periodValueLabel.setLayoutX(420);
        periodValueLabel.setLayoutY(200);

        Label salaryPrefixLabel = new Label("Salary: ");
        salaryPrefixLabel.setLayoutX(300);
        salaryPrefixLabel.setLayoutY(240);
        salaryPrefixLabel.setStyle("-fx-text-fill: #892193FF;");

        String salaryText = String.valueOf(offer.getSalary());
        Label salaryLabel = new Label(salaryText);
        salaryLabel.setLayoutX(420);
        salaryLabel.setLayoutY(240);

        //card.setUserData(offer.getId());

        Button appsButton = new Button("See Applications");
        appsButton.setLayoutX(1000);
        appsButton.setLayoutY(280);
        appsButton.getStyleClass().add("apply-button");
        appsButton.setOnAction(event->  {
            afficherApps(offer);

        });





        Button deleteButton = new Button("Delete");
        deleteButton.setLayoutX(890);
        deleteButton.setLayoutY(280);
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(event->  {

            offerService.supprimer(offer);

            initialize();
        });



        Button editButton = new Button("Edit");
        editButton.setLayoutX(780);
        editButton.setLayoutY(280);
        editButton.getStyleClass().add("edit-button");
        editButton.setOnAction(event -> {
           handleEditButton(offer); // Pass offer data to the handler method
        });

        Image gifImage = new Image(getClass().getResourceAsStream("/images/job-seeking.gif"));
        ImageView gifImageView = new ImageView(gifImage);


        Platform.runLater(() -> {
            gifImageView.setLayoutX(card.getWidth() - gifImage.getWidth());
            gifImageView.setLayoutY(15);
            gifImageView.setLayoutX(1000);
            gifImageView.setFitWidth(200);
            gifImageView.setFitHeight(200);



        });



        card.getChildren().addAll(imageView, namePrefixLabel, nameLabel, DomainPrefixLabel, DomainLabel,
                postPrefixLabel, postLabel, descriptionPrefixLabel, descriptionLabel, localisationPrefixLabel,
                localisationLabel, periodPrefixLabel, periodValueLabel, salaryPrefixLabel, salaryLabel, appsButton,deleteButton,editButton,gifImageView);

        return card;
    }

    private void afficherApps(offers offer) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/OfferApps.fxml"));
            Parent root = loader.load();
            OfferApps controller = loader.getController();
            System.out.println("heyyy= "+ offer);
            controller.setappsOFF(offer);
            offersScrollPane.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void navigateToAddOffer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/addOffer.fxml"));
            Parent root = loader.load();
            offersScrollPane.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void navigateToDisplayAllOffers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            Parent root = loader.load();
            offersScrollPane.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   /* private void handleEditButton(offers offer) {
        // Navigate to the EditOfferController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/editOffer.fxml"));
        Parent root;
        try {
            root = loader.load();

            editOffer controller = loader.getController();
            controller.setEditedOffer(offer); // Pass the offer to the EditOfferController
            controller.setOfferService(offerService); // Initialize OfferService in the controller

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    private void handleEditButton(offers offer) {
        // Navigate to the EditOfferController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/editOffer.fxml"));
        Parent root;
        try {
            root = loader.load();

            // Retrieve the controller instance
            editOffer controller = loader.getController();

            // Pass the offer and offerService to the EditOffer Controller
            controller.setEditedOffer(offer);
            controller.setOfferService(offerService);

            // Get the current scene
            Scene scene = offersScrollPane.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
