package tests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.Category;

import java.net.URL;
import java.util.ResourceBundle;



public class categoryCellController implements Initializable {
    @FXML
    private Text categoryDescription;

    @FXML
    private ImageView categoryImage;

    @FXML
    private Text postNumber;

    @FXML
    private Text categoryName;
    private final Category category;

    public categoryCellController(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
