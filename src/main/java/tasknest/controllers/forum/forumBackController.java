package tasknest.controllers.forum;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasknest.models.Category;
import tasknest.models.Comments;
import tasknest.models.Posts;
import tasknest.services.CategoryService;
import tasknest.services.PostsService;
import tasknest.services.CommentsService;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class forumBackController {

    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField imageUrl;
    @FXML
    private TextArea categoryDesc;
    @FXML
    private TextField categoryName;
    @FXML
    private Label checkoffers;

    @FXML
    private Label forum;

    @FXML
    private Label freelancer;

    @FXML
    private Label home;

    @FXML
    private Label reclamation;

    @FXML
    private PieChart pieChart;
    @FXML
    private TableView<Comments> commentTable;

    @FXML
    private ComboBox<Posts> postsCombo;
    @FXML
    private TableView<Posts> postsTable;
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private ImageView categoryImage;
    CategoryService categoryService = new CategoryService();
    PostsService postsService = new PostsService();
    CommentsService commentsService = new CommentsService();
    ObservableList<Category> observableCategories = FXCollections.observableArrayList();
    ObservableList<Posts> observablePosts = FXCollections.observableArrayList();
    ObservableList<Comments> observableComments = FXCollections.observableArrayList();

    public void initialize(){

        //--Category table---------------------
        List<Category> categories = categoryService.afficher();
        observableCategories.addAll(categories);

        TableColumn<Category, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Category, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Category, String> imageColumn = new TableColumn<>("Image Url");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image_url"));

        categoryTable.getColumns().addAll(nameColumn, descriptionColumn,imageColumn);
        categoryTable.setItems(observableCategories);

        //--Post Table---------------------------------------------------------------
        List<Posts> posts = postsService.afficher();
        observablePosts.addAll(posts);

        TableColumn<Posts, String> titlePost = new TableColumn<>("Title");
        titlePost.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Posts, String> contentColumn = new TableColumn<>("Content");
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        TableColumn<Posts, LocalDateTime> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Posts, Integer> viewsColumn = new TableColumn<>("Number of views");
        viewsColumn.setCellValueFactory(new PropertyValueFactory<>("views"));

        TableColumn<Posts, String> imagePColumn = new TableColumn<>("Image Url");
        imagePColumn.setCellValueFactory(new PropertyValueFactory<>("image_url"));

        postsTable.getColumns().addAll(titlePost,contentColumn,dateColumn,viewsColumn,imagePColumn);
        postsTable.setItems(observablePosts);

        //----------------Comments-----------------------------------

        postsCombo.getItems().addAll(posts);




        //----------------Pie chart---------------------
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int nbPosts;
        for(Category category : categories){
            nbPosts = postsService.afficher(category.getId()).size();
            pieChartData.add(new PieChart.Data(category.getName(),nbPosts));
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Products sold");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLegendVisible(true);
        pieChart.setStartAngle(180);
    }

    @FXML
    void addCat(MouseEvent event) {
        Category category = new Category(categoryName.getText(),categoryDesc.getText(),imageUrl.getText());
        categoryService.addCategory(category);
        categoryTable.getItems().add(category);
        categoryName.clear();
        categoryDesc.clear();
        imageUrl.clear();
        categoryImage.setImage(new Image("file:C:\\Users\\Oussema\\Downloads\\Tasknest-JavaFX-DesktopApplication-main\\src\\main\\resources\\forum\\images\\white.png"));
    }

    @FXML
    void ChooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imageUrl.setText(selectedFile.getAbsolutePath());
            categoryImage.setImage(new Image("file:" + selectedFile.getAbsolutePath()));
        }
    }
    public void alloffersback(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offer/displayofferback.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void allcvsback(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CV/showAllCVsBACK.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reclamnav(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listusers(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/diaplayUsers.fxml"));
            AnchorPane nextAnchorPane = loader.load();

            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(nextAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delCat(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to delete this category?");
        confirmationAlert.setContentText("All the posts associated will also be deleted.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            categoryService.supprimer(categoryTable.getSelectionModel().getSelectedItem());
            observableCategories.clear();
            List<Category> categories = categoryService.afficher();
            observableCategories.addAll(categories);
        }
    }
    @FXML
    void load(MouseEvent event) {
        observableComments.clear();

        List<Comments> comments = commentsService.afficher(postsCombo.getSelectionModel().getSelectedItem().getId());
        observableComments.addAll(comments);

        TableColumn<Comments, String> commentContent = new TableColumn<>("Content");
        commentContent.setCellValueFactory(new PropertyValueFactory<>("content"));

        TableColumn<Comments, LocalDateTime> dateComment = new TableColumn<>("Date");
        dateComment.setCellValueFactory(new PropertyValueFactory<>("date"));



        commentTable.getColumns().addAll(commentContent,dateComment);
        commentTable.setItems(observableComments);
    }

    @FXML
    void deleteComment(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to delete this comment?");
        confirmationAlert.setContentText("This comment will be permanently deleted.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            commentsService.delete(commentTable.getSelectionModel().getSelectedItem());
            observableComments.clear();
            List<Comments> comments = commentsService.afficher(postsCombo.getSelectionModel().getSelectedItem().getId());
            observableComments.addAll(comments);
        }
    }
    @FXML
    void deletePost(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to delete this post?");
        confirmationAlert.setContentText("This post will be permanently deleted.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            postsService.supprimer(postsTable.getSelectionModel().getSelectedItem());
            observablePosts.clear();
            List<Posts> posts = postsService.afficher();
            observablePosts.addAll(posts);
        }
    }
    @FXML
    void editCat(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/editCategory.fxml"));
        AnchorPane nextAnchorPane = loader.load();

        editCategoryController controller = loader.getController();


        controller.category=categoryTable.getSelectionModel().getSelectedItem();
        controller.setData();

        anchor.getChildren().removeAll();
        anchor.getChildren().setAll(nextAnchorPane);
    }

}
