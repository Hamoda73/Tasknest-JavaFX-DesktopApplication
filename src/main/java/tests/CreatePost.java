package tests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import models.Category;
import models.Posts;
import models.SceneSwitch;
import services.CategoryService;
import services.PostsService;
import utils.DataSource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CreatePost {


    @FXML
    private Button ChooseImageButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Category> categoryField;

    @FXML
    private TextArea contentField;

    @FXML
    private AnchorPane creationAnchorPane;

    @FXML
    public Button postButton;

    @FXML
    public Button editButton;

    @FXML
    private Button switch2categories;

    @FXML
    private TextField titleField;

    @FXML
    private TextField imagePathField;

    private Posts post = new Posts();

    CategoryService categoryService = new CategoryService();
    PostsService postsService = new PostsService();


    public void initialize() {
        List<Category> categories = categoryService.afficher();
        categoryField.getItems().addAll(categories);
        if(post != null){
            titleField.setText(post.getTitle());
            contentField.setText(post.getContent());
            imagePathField.setText(post.getImage_url());
        }

    }

    @FXML
    void ChooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }


    @FXML
    void onSwitchClick(ActionEvent event) throws IOException {
        new SceneSwitch(creationAnchorPane, "categories.fxml");
    }


    @FXML
    void cancel(MouseEvent event) {

    }

    @FXML
    void post(MouseEvent event) {

        String title = titleField.getText();
        String content = contentField.getText();
        int cat_id = categoryField.getSelectionModel().getSelectedItem().getId();
        String imagePath = imagePathField.getText();
        Posts post = new Posts(title,content,12,cat_id,imagePath);
        System.out.println(post);
        postsService.ajouter(post);
    }

    void setPost(Posts post) {
        this.post = post;
    }

    @FXML
    void editPost(MouseEvent event) {
        post.setTitle(titleField.getText());
        post.setContent(contentField.getText());
        post.setImage_url(imagePathField.getText());
        post.setCategory_id(categoryField.getSelectionModel().getSelectedItem().getId());
        post.setDate(LocalDateTime.now());
        postsService.modifier(post);
    }
}
