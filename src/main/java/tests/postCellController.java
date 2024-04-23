package tests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.Posts;

import java.net.URL;
import java.util.ResourceBundle;



public class postCellController implements Initializable {
    @FXML
    private Text postDate;

    @FXML
    private Text postTitle;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;

    @FXML
    private Text viewsNumber;
    private final Posts post;
    public postCellController(Posts post) {
        this.post = post;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
