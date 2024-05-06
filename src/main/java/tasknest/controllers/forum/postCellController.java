package tasknest.controllers.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tasknest.models.Posts;
import tasknest.models.users;
import tasknest.services.DislikesService;
import tasknest.services.LikesService;
import tasknest.services.PostsService;
import tasknest.tests.MainFx;

import java.io.IOException;
import java.sql.SQLException;
import tasknest.services.userrService;

public class postCellController {
    public Posts post;

    public AnchorPane postListAnchor;

    @FXML
    private ImageView dislikeIcon;

    @FXML
    private ImageView likeIcon;

    @FXML
    private AnchorPane postCellAnchor;

    @FXML
    private Text postDate;

    @FXML
    private Text postTitle;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;

    @FXML
    private Text dislikeNumber;
    @FXML
    private Text likeNumber;

    @FXML
    private Text viewsNumber;
    public LikesService likesService = new LikesService();
    public DislikesService dislikesService = new DislikesService();
    private userrService userService = new userrService();

    @FXML
    void showPost(MouseEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/post.fxml"));
        AnchorPane nextAnchorPane;

        if(loader.getController()==null)
            System.out.println("single post controller is null ");
        try{
            nextAnchorPane = loader.load();
        } catch (IOException e) {
            System.err.println("Error loading single post scene FXML: " + e.getMessage());
            return;
        }

        postController controller = loader.getController();
        controller.post=post;
        controller.postcontroller = controller;
        controller.initialize();

        postListAnchor.getChildren().removeAll();
        postListAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void setPostData() throws SQLException {
        if (post.getId() != 0) {
            users user = userService.getUserById(post.getUser_id());

            postTitle.setText(post.getTitle());
            postDate.setText(post.getDate().toString());
            userName.setText(user.getFname());
            userImage.setImage(new Image("file:"+user.getImage()));
            viewsNumber.setText(post.getViews()+" Views");
            likeNumber.setText(String.valueOf(likesService.getNumber(post.getId())));
            dislikeNumber.setText(String.valueOf(dislikesService.getNumber(post.getId())));
        }

    }
}
