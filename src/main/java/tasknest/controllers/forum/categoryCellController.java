package tasknest.controllers.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tasknest.models.Category;
import tasknest.services.CategoryService;
import tasknest.services.PostsService;

import java.io.IOException;

public class categoryCellController {
    public Category category;
    public Stage stage;

    @FXML
    private Text categoryDescription;

    @FXML
    private ImageView categoryImage;

    @FXML
    private Text postNumber;

    @FXML
    private Text categoryName;

    @FXML
    public AnchorPane categoryAnchorPane;

    @FXML
    private Circle clip;

    public CategoryService categoryService;
    private PostsService postsService=new PostsService();




  public void setCategoryData(Category category) {
        if (category.getId() != 0) {
            int nbPosts = postsService.afficher(category.getId()).size();

            this.postNumber.setText(nbPosts + " Posts");
            this.categoryName.setText(category.getName());
            this.categoryDescription.setText(category.getDescription());
            if(category.getImage_url()!=null){
                Image img = new Image("file:"+category.getImage_url());
                this.categoryImage.setImage(img);


                Circle clip = new Circle(35, 35, 35);


                clip.setStroke(Color.BLACK);
                this.categoryImage.setClip(clip);

                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                WritableImage image = this.categoryImage.snapshot(parameters, null);
                this.categoryImage.setEffect(new DropShadow(20, Color.BLACK));
                this.categoryImage.setClip(null);


                this.categoryImage.setImage(image);
            }


        }

    }
    @FXML
    void showPosts(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/postList.fxml"));
        AnchorPane nextAnchorPane = loader.load();
        if(loader.getController()==null)
            System.out.println("controller is null");

        postListController controller = loader.getController();
        System.out.println("cat id in cat cell: " + category.getId());
        controller.categoryId = category.getId();
        controller.initialize();

        categoryAnchorPane.getChildren().removeAll();
        categoryAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
