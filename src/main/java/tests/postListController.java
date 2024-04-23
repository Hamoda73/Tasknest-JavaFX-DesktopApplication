package tests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.Category;
import models.Posts;
import models.SceneSwitch;
import services.PostsService;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class postListController {
    private int id;
    private PostsService postsService=new PostsService();
    @FXML
    private ListView<Posts> postsList;

    @FXML
    private AnchorPane postListAnchor;

    public postListController() {
    }

    public postListController(int categoryId) {
        this.id=categoryId;
    }

    public void setId(int id) {
        this.id = id;
        System.out.println("updated");
    }
    public void initialize(){

        List<Posts> posts = postsService.afficher(id);
        ObservableList<Posts> observablePosts = FXCollections.observableArrayList(posts);
        postsList.setItems(observablePosts);
    }
    @FXML
    void showSinglePost(MouseEvent event) throws IOException, SQLException {
        new SceneSwitch(postListAnchor, "singlePost.fxml", postsList.getSelectionModel().getSelectedItem());
    }
}
