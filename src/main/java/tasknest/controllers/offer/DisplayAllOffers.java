package tasknest.controllers.offer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
//import org.w3c.dom.events.MouseEvent;
import tasknest.controllers.applications.Apply;
import tasknest.models.offers;
import tasknest.services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DisplayAllOffers implements Initializable {
    private ObservableList<offers> allOffersList = FXCollections.observableArrayList();
    @FXML
    ScrollPane offersScrollPane;
    @FXML
    private TextField searchField;
    private OfferService offerService;

    @FXML
    private Pagination pagination;

    private static final int ITEMS_PER_PAGE = 3;

    @FXML
    private VBox cardsContainer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        offerService = new OfferService();

        allOffersList.addAll(offerService.getAllOffers());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterOffers(newValue);});

        populateOffers();
        setupPagination();
    }
    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) allOffersList.size() / ITEMS_PER_PAGE);
        pagination = new Pagination(pageCount, 0);
        pagination.setPageFactory(this::createPage);
        offersScrollPane.setContent(pagination);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allOffersList.size());
        List<offers> currentPageOffers = allOffersList.subList(fromIndex, toIndex);

        VBox pageContent = new VBox(10);
        for (offers offer : currentPageOffers) {
            AnchorPane card = createOfferCard(offer);
            pageContent.getChildren().add(card);
        }
        return pageContent;
    }



  /*  private void filterOffers(String query) {
        Predicate<offers> containsQuery = offer ->
                offer.getEntreprise_name().toLowerCase().contains(query.toLowerCase()) ||
                        offer.getDomain().toLowerCase().contains(query.toLowerCase()) ||
                        offer.getPost().toLowerCase().contains(query.toLowerCase()) ||
                        offer.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        offer.getLocalisation().toLowerCase().contains(query.toLowerCase()) ||
                        offer.getPeriod().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(offer.getSalary()).toLowerCase().contains(query.toLowerCase());

        FilteredList<offers> filteredList = allOffersList.filtered(containsQuery);

        VBox offersContainer = new VBox(10);

        for (offers offer : filteredList) {
            AnchorPane card = createOfferCard(offer);
            offersContainer.getChildren().add(card);
        }

        offersScrollPane.setContent(offersContainer);
    }*/




  private void filterOffers(String query) {
    Predicate<offers> containsQuery = offer ->
            offer.getEntreprise_name().toLowerCase().contains(query.toLowerCase()) ||
            offer.getDomain().toLowerCase().contains(query.toLowerCase()) ||
            offer.getPost().toLowerCase().contains(query.toLowerCase()) ||
            offer.getDescription().toLowerCase().contains(query.toLowerCase()) ||
            offer.getLocalisation().toLowerCase().contains(query.toLowerCase()) ||
            offer.getPeriod().toLowerCase().contains(query.toLowerCase()) ||
            String.valueOf(offer.getSalary()).toLowerCase().contains(query.toLowerCase());

    FilteredList<offers> filteredList = allOffersList.filtered(containsQuery);

    VBox offersContainer = new VBox(10);

    for (offers offer : filteredList) {
        AnchorPane card = createOfferCard(offer);
        offersContainer.getChildren().add(card);
    }

    offersScrollPane.setContent(offersContainer);
}




    private void populateOffers() {
        List<offers> allOffers = offerService.getAllOffers();

        VBox offersContainer = new VBox(10);

        for (offers offer : allOffers) {
            AnchorPane card = createOfferCard(offer);
            offersContainer.getChildren().add(card);
        }

        offersScrollPane.setContent(offersContainer);
    }


    private AnchorPane createOfferCard(offers offer) {

        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("offer-card");

        ImageView imageView = new ImageView();
        String imageUrl = getClass().getResource("/images/taswira.png").toExternalForm();
        Image image = new Image(imageUrl);

        imageView.setImage(image);
        imageView.getStyleClass().add("image-view");

        /*Label nameLabel = new Label("Company  : " + offer.getEntreprise_name());
        nameLabel.setLayoutX(300);
        nameLabel.setLayoutY(10);*/

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


        //newwwwwwwwwww
        // Inside your createOfferCard method:

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
      //  descriptionLabel.setMaxWidth(300); // Set the maximum width for wrapping
        //descriptionLabel.setWrapText(true);




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
// Add both labels to your layout

        Label salaryPrefixLabel = new Label("Salary: ");
        salaryPrefixLabel.setLayoutX(300);
        salaryPrefixLabel.setLayoutY(240);
        salaryPrefixLabel.setStyle("-fx-text-fill: #892193FF;");

// Convert float salary to string
        String salaryText = String.valueOf(offer.getSalary());
        Label salaryLabel = new Label(salaryText);
        salaryLabel.setLayoutX(420);
        salaryLabel.setLayoutY(240);




        Button applyButton = new Button("Apply");
        applyButton.setLayoutX(1000);
        applyButton.setLayoutY(280);
        applyButton.getStyleClass().add("apply-button");


        applyButton.setOnMouseClicked(event -> {
            redirectToApply(event, offer.getId());
        });



      /*  Image gifImage = new Image(getClass().getResourceAsStream("/images/job.gif"));
        ImageView gifImageView = new ImageView(gifImage);


        Platform.runLater(() -> {
            gifImageView.setLayoutX(card.getWidth() - gifImage.getWidth());
            gifImageView.setLayoutY(15);
            gifImageView.setLayoutX(1000);
            gifImageView.setFitWidth(200);
            gifImageView.setFitHeight(200);



        });
*/


       /* applyButton.setOnAction(event -> {
            try {
                Window window = ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/Apply.fxml"));
                Parent root = loader.load();

                Apply applyController = loader.getController();
                //applyController.setUserAndOfferIds(offer.getUserId(), offer.getId());

                // Get the scene from the button's stage
                Scene scene = applyButton.getScene();

                // Replace the scene content with the new FXML content
                scene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        */

        card.getChildren().addAll(imageView,namePrefixLabel ,nameLabel, DomainPrefixLabel,DomainLabel,postPrefixLabel, postLabel,descriptionPrefixLabel, descriptionLabel, localisationPrefixLabel,localisationLabel,periodPrefixLabel, periodValueLabel,salaryPrefixLabel, salaryLabel, applyButton/*,gifImageView*/);

        return card;
    }

    /*private int getSelectedOfferId() {
        // Retrieve the selected offer ID from the clicked card's UserData
        AnchorPane selectedCard = (AnchorPane) offersScrollPane.getContent().lookup(".selected");
        if (selectedCard != null) {
            // Return the offer ID stored in the selected card's UserData
            return (int) selectedCard.getUserData();
        }
        return -1; // Return -1 if no offer is selected
    }*/


    private void redirectToApply(MouseEvent event, int offerId) {
        try {

            Window window = ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Applications/Apply.fxml"));
            Parent root = loader.load();

            // pass the user and offer idss to the Apply controller
            Apply applyController = loader.getController();
            applyController.setUserAndOfferIds(offerId);

            // Show the Apply view
            Stage stage = (Stage) window;
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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
    private void navigateToDisplayuseroff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/Displayoffuser.fxml"));
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
}
