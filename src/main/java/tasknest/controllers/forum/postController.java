package tasknest.controllers.forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tasknest.models.Category;
import tasknest.models.Posts;
import tasknest.models.Comments;
import tasknest.models.users;
import tasknest.services.*;
import tasknest.tests.MainFx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import tasknest.services.userrService;

public class postController {

    @FXML
    private Text categoryField;

    @FXML
    private TextField commentField;

    @FXML
    private ListView<Comments> commentsList;

    @FXML
    private Text dateField;

    @FXML
    private ToggleButton dislikeButton;

    @FXML
    private Text dislikeNumber;

    @FXML
    private ToggleButton likeButton;

    @FXML
    private Text likeNumber;

    @FXML
    private ImageView logoImage;

    @FXML
    private AnchorPane postAnchor;

    @FXML
    private TextArea postContent;

    @FXML
    private ImageView dislikeImg;
    @FXML
    private ImageView retrunIcon;

    @FXML
    private ImageView likeImg;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView postImage;

    @FXML
    private Text titleField;

    @FXML
    private Text userNameField;

    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private ImageView userImg;
    public postController postcontroller;
    private ObservableList<Comments> observableComments= FXCollections.observableArrayList();;
    public CommentsService commentsService = new CommentsService();
    public CategoryService categoryService= new CategoryService();
    public LikesService likesService = new LikesService();
    public DislikesService dislikesService = new DislikesService();
    private PostsService postsService=new PostsService();
    private userrService userService=new userrService();

    boolean liked;
    boolean disliked;

    public Posts post;

    public void initialize() throws SQLException {

if(post !=null) {

    postsService.addView(post);

    if(MainFx.getSession().getId()!=post.getUser_id()){
        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    users user = userService.getUserById(post.getUser_id());
    Category category = categoryService.getCategory(post.getCategory_id());
    categoryField.setText("Category: " + category.getName());
    userNameField.setText(user.getFname());
    if(user.getImage()!=null){
        Image img = new Image("file:"+user.getImage());
        userImg.setImage(img);
    }

    //deleteButton.setVisible(false);
    titleField.setText(post.getTitle());
    System.out.println(post.getImage_url());
    Image image = new Image("file:"+post.getImage_url());
    postImage.setImage(image);
    dateField.setText(post.getDate().toString());
    postContent.setText(post.getContent());


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

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/commentCell.fxml"));
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
                    controller.postcontroller=postcontroller;
                    setText(null);

                    setGraphic(commentPane);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    likeNumber.setText(String.valueOf(likesService.getNumber(post.getId())));
    dislikeNumber.setText(String.valueOf(dislikesService.getNumber(post.getId())));


    if(likesService.getLike(MainFx.getSession().getId()).getId()!=0) {
        likeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\like1.png"));
        liked = true;
    }
    else {
        likeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nolike.png"));
        liked = false;
    }


    if(dislikesService.getDislike(MainFx.getSession().getId()).getId()!=0) {
        dislikeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\dislike1.png"));
        disliked = true;
    }
    else{
        dislikeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nodislike.png"));
        disliked = false;
    }
}
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/postList.fxml"));
        AnchorPane nextAnchorPane = loader.load();
        if(loader.getController()==null)
            System.out.println("controller is null");

        postListController controller = loader.getController();
        System.out.println("cat id in cat cell: " + post.getCategory_id());
        controller.categoryId = post.getCategory_id();
        controller.initialize();

        postAnchor.getChildren().removeAll();
        postAnchor.getChildren().setAll(nextAnchorPane);
    }

    @FXML
    void speak(MouseEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        try{
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speakPlainText(postContent.getText(), null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

        }catch(EngineException e){
            e.printStackTrace();
        }catch(AudioException e){
            e.printStackTrace();
        }catch (EngineStateError e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    @FXML
    void like(MouseEvent event) throws SQLException {
        likesService.add(MainFx.getSession().getId(), post.getId());
        likeNumber.setText(String.valueOf(likesService.getNumber(post.getId())));
        dislikeNumber.setText(String.valueOf(dislikesService.getNumber(post.getId())));

        if(liked){
            likeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nolike.png"));
            liked = false;
        }
        else {
            likeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\like1.png"));
            liked = true;
            if(disliked){
                dislikeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nodislike.png"));
                disliked = false;
            }
        }
    }
    @FXML
    void dislike(MouseEvent event) throws SQLException {
        dislikesService.add(MainFx.getSession().getId(), post.getId());
        likeNumber.setText(String.valueOf(likesService.getNumber(post.getId())));
        dislikeNumber.setText(String.valueOf(dislikesService.getNumber(post.getId())));
        if(disliked){
            dislikeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nodislike.png"));
            disliked = false;
        }
        else{
            dislikeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\dislike1.png"));
            if(liked){
                likeImg.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\nolike.png"));
                liked = false;
            }
        }
    }
    @FXML
    void editPost(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/createPost.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        createPostController createPostController = loader.getController();
        createPostController.post=post;
        createPostController.editButton.setVisible(true);
        createPostController.postButton.setVisible(false);
        createPostController.initialize();
        createPostController.loadCombo();

        postAnchor.getChildren().removeAll();
        postAnchor.getChildren().setAll(nextAnchorPane);
    }
    @FXML
    void comment(MouseEvent event) {

        observableComments.clear();

        String commentContent = commentField.getText();
        commentField.setText("");
        Comments comment = new Comments(MainFx.getSession().getId(), post.getId(), commentContent);
        commentsService.ajouter(comment);


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

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/commentCell.fxml"));
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
                    controller.postcontroller=postcontroller;

                    setText(null);

                    setGraphic(commentPane);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });

    }
    public void reloadComments(){
        observableComments.clear();

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

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/commentCell.fxml"));
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
                    controller.postcontroller=postcontroller;

                    setText(null);

                    setGraphic(commentPane);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }
    @FXML
    private void navigateToDisplayAllOffers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/DisplayAllOffers.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postAnchor.getChildren().removeAll();
            postAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void redirectFreelancers(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CV/showAllCVs.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postAnchor.getChildren().removeAll();
            postAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void reclamation(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/complaint.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            postAnchor.getChildren().removeAll();
            postAnchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void delPost(MouseEvent event) throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to delete this post?");
        confirmationAlert.setContentText("This post will be permanently deleted.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            postsService.supprimer(post);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/postList.fxml"));
            AnchorPane nextAnchorPane = loader.load();
            if(loader.getController()==null)
                System.out.println("controller is null");

            postListController controller = loader.getController();
            System.out.println("cat id in cat cell: " + post.getCategory_id());
            controller.categoryId = post.getCategory_id();
            controller.initialize();

            postAnchor.getChildren().removeAll();
            postAnchor.getChildren().setAll(nextAnchorPane);
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

        postAnchor.getChildren().removeAll();
        postAnchor.getChildren().setAll(nextAnchorPane);
    }
}
