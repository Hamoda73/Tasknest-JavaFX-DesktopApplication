package tasknest.controllers.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasknest.models.Category;
import tasknest.models.Posts;
import tasknest.services.CategoryService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class editCategoryController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextArea desc;

    @FXML
    private TextField image;

    @FXML
    private TextField name;

    @FXML
    private ImageView catImg;
    public Category category;
    CategoryService categoryservice = new CategoryService();
    public void setData() {
        name.setText(category.getName());
        desc.setText(category.getDescription());
        image.setText(category.getImage_url());
        catImg.setImage(new Image("file:"+category.getImage_url()));
    }

    @FXML
    void cancel(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/forumBack.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        anchor.getChildren().removeAll();
        anchor.getChildren().setAll(nextAnchorPane);
    }

    @FXML
    void edit(MouseEvent event) throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to edit this category?");
        confirmationAlert.setContentText("This category will be permanently changed.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println(category.getId());
            categoryservice.modifier(category);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/forumBack.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(nextAnchorPane);
        }
    }

    @FXML
    void img(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            image.setText(selectedFile.getAbsolutePath());
            catImg.setImage(new Image("file:"+selectedFile.getAbsolutePath()));
        }
    }

}