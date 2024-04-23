package models;

import tests.MainProg;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import tests.postListController;
import tests.PostController;
import tests.CreatePost;

public class SceneSwitch {


    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException{
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainProg.class.getResource(fxml)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);

    }
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml, int category_id) throws IOException{
        //System.out.println(category_id);
        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource(fxml));
        AnchorPane nextAnchorPane = loader.load();

        postListController post_list_controller = loader.getController();
        post_list_controller.setId(category_id);
        post_list_controller.initialize();

        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);


    }
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml, Posts post) throws IOException, SQLException {
        //System.out.println(category_id);
        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource(fxml));
        AnchorPane nextAnchorPane = loader.load();

        PostController postController = loader.getController();
        postController.setPost(post);
        postController.initialize();

        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);


    }



}
