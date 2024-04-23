package tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Category;
import models.SceneSwitch;
import services.CategoryService;

import java.io.IOException;
import java.util.List;

public class CategoriesController {
    private Stage stage;
    CategoryService categoryService = new CategoryService();
    @FXML
    private Button toCreation;

    @FXML
    private AnchorPane categoryAnchorPane;

    @FXML
    private ListView<Category> listOfCategories;

    public CategoriesController() throws IOException {
    }

    public void initialize() {
        List<Category> categories = categoryService.afficher();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        listOfCategories.setItems(observableCategories);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    Parent root2 = FXMLLoader.load(getClass().getResource("createPost.fxml"));
    private Scene creationScene = new Scene(root2);

    @FXML
    void show(MouseEvent event) {
        List<Category> categories = categoryService.afficher();
        ObservableList<Category> observableCategories = FXCollections.observableArrayList(categories);
        listOfCategories.setItems(observableCategories);
    }



    @FXML
    void goToCreation(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource("createPost.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        CreatePost createPostController = loader.getController();



        createPostController.editButton.setVisible(false);
        createPostController.postButton.setVisible(true);
        createPostController.initialize();

        categoryAnchorPane.getChildren().removeAll();
        categoryAnchorPane.getChildren().setAll(nextAnchorPane);
    }


    @FXML
    void showPosts(MouseEvent event) throws IOException{
        new SceneSwitch(categoryAnchorPane, "postsList.fxml",listOfCategories.getSelectionModel().getSelectedItem().getId());
    }

}

