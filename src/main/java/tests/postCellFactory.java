package tests;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import models.Posts;

import java.io.IOException;

public class postCellFactory extends ListCell<Posts> {
    @Override
    protected void updateItem(Posts post, boolean empty) {
        super.updateItem(post, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("postCell.fxml"));
            postCellController controller = new postCellController(post);
            loader.setController(controller);
            setText(null);
            try{
                setGraphic(loader.load());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
