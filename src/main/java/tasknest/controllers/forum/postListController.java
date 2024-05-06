package tasknest.controllers.forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import tasknest.models.Category;
import tasknest.models.Posts;
import tasknest.services.PostsService;
import tasknest.services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class postListController {

    @FXML
    private ImageView catImage;
    @FXML
    private ImageView addIcon;

    @FXML
    private Text categoryNameField;

    @FXML
    private ImageView loogoImge;

    @FXML
    private AnchorPane postListAnchor;

    @FXML
    private ListView<Posts> postsList;

    @FXML
    private ImageView returnIcon;

    public int categoryId;
    private PostsService postsService=new PostsService();
    private CategoryService categoryService=new CategoryService();

    public void initialize(){
        if(categoryId!=0){
            Category category = categoryService.getCategory(categoryId);
            categoryNameField.setText(category.getName());
            catImage.setImage(new Image("file:"+category.getImage_url()));

            Image img = new Image("file:"+category.getImage_url());
            this.catImage.setImage(img);
            Circle clip = new Circle(35, 35, 35);
            clip.setStroke(Color.BLACK);
            this.catImage.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = this.catImage.snapshot(parameters, null);
            this.catImage.setEffect(new DropShadow(20, Color.BLACK));
            this.catImage.setClip(null);
            this.catImage.setImage(image);

        System.out.println(categoryId);
        List<Posts> posts = postsService.afficher(categoryId);
        System.out.println(posts);
        ObservableList<Posts> observablePosts = FXCollections.observableArrayList();
        observablePosts.addAll(posts);
        postsList.setItems(observablePosts);


        postsList.setCellFactory(cell -> new ListCell<Posts>() {
            @Override
            protected void updateItem(Posts post, boolean empty) {
                super.updateItem(post, empty);
                if (post != null)  {
                    // Load comment scene FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/postCell.fxml"));
                    if(loader.getController() == null) {
                        System.out.println("controller is  null");
                    }
                    AnchorPane postCellAnchor;
                    try {
                        postCellAnchor = loader.load();
                    } catch (IOException e) {
                        System.err.println("Error loading category scene FXML: " + e.getMessage());
                        return;
                    }
                    postCellController controller = loader.getController();

                    if(controller == null)
                        System.out.println("controller is null");

                    controller.post = post;
                    controller.postListAnchor=postListAnchor;

                    try {
                        controller.setPostData();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    setText(null);

                    setGraphic(postCellAnchor);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }
}
    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/categories.fxml"));
        AnchorPane nextAnchorPane = loader.load();
        if(loader.getController()==null)
            System.out.println("controller is null");

        postListAnchor.getChildren().removeAll();
        postListAnchor.getChildren().setAll(nextAnchorPane);
    }
    @FXML
    private void navigateToDisplayAllOffers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postListAnchor.getChildren().removeAll();
            postListAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void redirectFreelancers(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CV/showAllCVs.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postListAnchor.getChildren().removeAll();
            postListAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void reclamation(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/complaint.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postListAnchor.getChildren().removeAll();
            postListAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        postListAnchor.getChildren().removeAll();
        postListAnchor.getChildren().setAll(nextAnchorPane);
    }
}
