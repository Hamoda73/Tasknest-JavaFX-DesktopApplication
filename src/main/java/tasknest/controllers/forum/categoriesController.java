package tasknest.controllers.forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import tasknest.controllers.forum.createPostController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tasknest.models.Category;
import tasknest.services.CategoryService;
import tasknest.tests.MainFx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class categoriesController  {

    @FXML
    private ImageView addIcon;

    @FXML
    private AnchorPane categoryAnchorPane;

    @FXML
    private Text createButton;

    @FXML
    private ListView<Category> listOfCategories;

    @FXML
    private ImageView logoImage;

    CategoryService categoryService = new CategoryService();

    public void initialize(){
        System.out.println("user id: " + MainFx.getSession().getId());
        //logoImage.setImage(new Image("file:C:\\Users\\OussemaDownloads/Tasknest-JavaFX-DesktopApplication-main/src/main/resources/images/logo.png"));

        List<Category> categories = categoryService.afficher();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList();
        observableCategories.addAll(categories);
        listOfCategories.setItems(observableCategories);


        listOfCategories.setCellFactory(cell -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (category != null) {
                    // Load comment scene FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/categoryCell.fxml"));
                    if (loader.getController() == null) {
                        System.out.println("in list of categories controller is  null");
                    }
                    AnchorPane categoryAnchor;
                    try {
                        categoryAnchor = loader.load();
                    } catch (IOException e) {
                        System.err.println("Error loading category scene FXML: " + e.getMessage());
                        return;
                    }
                    categoryCellController controller = loader.getController();
                    if (controller == null)
                        System.out.println("controller is null");
                    controller.setCategoryData(category);
                    controller.categoryAnchorPane=categoryAnchorPane;
                    controller.category=category;
                    setText(null);

                    setGraphic(categoryAnchor);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    void createPost(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/createPost.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        createPostController controller = loader.getController();

        controller.editButton.setVisible(false);
        controller.postButton.setVisible(true);
        controller.initialize();
        controller.loadCombo();

        categoryAnchorPane.getChildren().removeAll();
        categoryAnchorPane.getChildren().setAll(nextAnchorPane);
    }
    @FXML
    private void navigateToDisplayAllOffers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            categoryAnchorPane.getChildren().removeAll();
            categoryAnchorPane.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void redirectFreelancers(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CV/showAllCVs.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            categoryAnchorPane.getChildren().removeAll();
            categoryAnchorPane.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void reclamation(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/complaint.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            categoryAnchorPane.getChildren().removeAll();
            categoryAnchorPane.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
