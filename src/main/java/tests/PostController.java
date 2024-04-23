package tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tests.commentCellController;

import models.*;
import services.*;
import javafx.scene.layout.AnchorPane;
import tests.CreatePost;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class PostController {

    @FXML
    private ToggleButton dislikeButton;

    @FXML
    private ToggleButton likeButton;





    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane postAnchor;

    @FXML
    private Text categoryField;

    @FXML
    private Text dateField;

    @FXML
    private ImageView postImage;

    @FXML
    private Text titleField;

    @FXML
    private ImageView userImage;

    @FXML
    private TextArea postContent;

    @FXML
    private Text userNameField;

    @FXML
    private Text likeNumber;

    @FXML
    private Text dislikeNumber;


    @FXML
    private TextField commentField;
    @FXML
    private ListView<Comments> commentsList;

    private ObservableList<Comments> observableComments;

    private Posts post = new Posts();


    private PostsService postsService = new PostsService();
    private CategoryService categoryService = new CategoryService();
    private UserService userService = new UserService();
    private LikesService likesService = new LikesService();
    private DislikesService dislikesService = new DislikesService();
    private CommentsService commentsService = new CommentsService();
    private

    ImageView noLike = new ImageView(new Image("C:/Users/Oussema/Documents/esprit/pi desktop/demo1/nolike.png"));
    ImageView Like = new ImageView(new Image("C:/Users/Oussema/Documents/esprit/pi desktop/demo1/like.png"));
    ImageView noDislike = new ImageView(new Image("C:/Users/Oussema/Documents/esprit/pi desktop/demo1/nodislike.png"));
    ImageView Dislike = new ImageView(new Image("C:/Users/Oussema/Documents/esprit/pi desktop/demo1/dislike.png"));


    private int user_id = 1;



    public void setPost(Posts post) {
        this.post = post;
    }

    public void initialize() throws SQLException {


        if (post.getDate()!= null) {
            Category category = categoryService.getCategory(post.getCategory_id());
            User user = userService.getUserById(post.getUser_id());
            categoryField.setText("Category: " + category.getName());
            userNameField.setText(user.getFname());
            System.out.println(user.getImage_url());
            Image img = new Image(user.getImage_url());
            userImage.setImage(img);
            //deleteButton.setVisible(false);
            titleField.setText(post.getTitle());
            Image image = new Image(post.getImage_url());
            postImage.setImage(image);
            dateField.setText(post.getDate().toString());
            postContent.setText(post.getContent());

            observableComments = FXCollections.observableArrayList();
            List<Comments> comments = commentsService.afficher(post.getId());
            observableComments.addAll(comments);
            //ObservableList<Comments> observableComments = FXCollections.observableArrayList(comments);
            commentsList.setItems(observableComments);

            commentsList.setCellFactory(cell -> new ListCell<Comments>() {
                @Override
                protected void updateItem(Comments comment, boolean empty) {
                    super.updateItem(comment, empty);
                    if (comment != null) {
                        // Load comment scene FXML

                        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource("commentCell.fxml"));
                        AnchorPane commentPane;
                        try {
                            commentPane = loader.load();
                        } catch (IOException e) {
                            System.err.println("Error loading comment scene FXML: " + e.getMessage());
                            return;
                        }
                        commentCellController controller = loader.getController();
                        if(controller == null)
                            System.out.println("controller is null");

                        controller.setCommentData(comment);
                        setText(null);

                        setGraphic(commentPane);
                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            });

            commentsList.setPrefHeight(173*comments.size());

        }

        if(likesService.getLike(user_id).getId()!=0)
            likeButton.setGraphic(Like);
        else
            likeButton.setGraphic(noLike);



        if(dislikesService.getDislike(user_id).getId()!=0)
            dislikeButton.setGraphic(Dislike);
        else
            dislikeButton.setGraphic(noDislike);

        likeNumber.setText(String.valueOf(likesService.getNumber(post.getId())));
        dislikeNumber.setText(String.valueOf(dislikesService.getNumber(post.getId())));

    }
    @FXML
    void deletePost(MouseEvent event) throws IOException {
        postsService.supprimer(post);
        new SceneSwitch(postAnchor, "postsList.fxml", post.getCategory_id());
    }

    @FXML
    void editPost(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource("createPost.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        CreatePost createPostController = loader.getController();
        createPostController.setPost(post);
        createPostController.editButton.setVisible(true);
        createPostController.postButton.setVisible(false);
        createPostController.initialize();

        postAnchor.getChildren().removeAll();
        postAnchor.getChildren().setAll(nextAnchorPane);
    }
    @FXML
    void like(MouseEvent event) {
        likesService.add(user_id, post.getId());
        if(likeButton.getGraphic()==Like){
            likeButton.setGraphic(noLike);
            likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText())-1));

        }

        else {
            likeButton.setGraphic(Like);
            likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText())+1));
            if(dislikeButton.getGraphic()==Dislike){
                dislikeButton.setGraphic(noDislike);
                dislikeNumber.setText(String.valueOf(Integer.parseInt(dislikeNumber.getText())-1));
            }

        }
    }
    @FXML
    void dislike(MouseEvent event) {
        dislikesService.add(user_id, post.getId());
        if(dislikeButton.getGraphic()==Dislike){
            dislikeButton.setGraphic(noDislike);
            dislikeNumber.setText(String.valueOf(Integer.parseInt(dislikeNumber.getText())-1));
        }

        else{
            dislikeButton.setGraphic(Dislike);
            dislikeNumber.setText(String.valueOf(Integer.parseInt(dislikeNumber.getText())+1));
            if(likeButton.getGraphic()==Like){
                likeButton.setGraphic(noLike);
                likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText())-1));
            }

        }
    }


    @FXML
    void postComment(MouseEvent event) {

        String commentContent = commentField.getText();
        commentField.setText("");
        Comments comment = new Comments(user_id, post.getId(), commentContent);
        System.out.println(post);
        commentsService.ajouter(comment);
        List<Comments> comments = commentsService.afficher(post.getId());
        ObservableList<Comments> observableComments = FXCollections.observableArrayList(comments);
        commentsList.setItems(observableComments);
    }

    @FXML
    void showLikers(MouseEvent event) throws SQLException {


        Scene newScene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("likersList.fxml"));
        try {
            Parent root = loader.load(); // This line loads the FXML and creates the scene graph
            newScene = new Scene(root, 278, 585); // Adjust width and height as needed
        } catch (IOException e) {
            newScene = null;
            e.printStackTrace(); // Handle the exception appropriately (e.g., show error message)
        }
        likersListController likersCont = loader.getController();
        likersCont.users = likesService.getLikers(post.getId());
        System.out.println(likersCont.users);
        likersCont.initialize();



        // Create a new stage
        Stage newStage = new Stage();

// Set the scene for the stage
        newStage.setScene(newScene);

// Optional: Set title for the new stage
        newStage.setTitle("Reactors List");

// Show the new stage
        newStage.show();

    }

}
