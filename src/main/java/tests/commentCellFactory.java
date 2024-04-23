package tests;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import models.Comments;
import javafx.scene.control.ListView;

import java.io.IOException;

public class commentCellFactory extends ListCell<Comments> {
   /* @Override
    protected void updateItem(Comments comment, boolean empty){
        super.updateItem(comment, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("commentCellFactory.fxml"));
             commentCellController controller = new commentCellController(comment);
             loader.setController(controller);
             setText(null);
             try{
                 setGraphic(loader.load());
             } catch (IOException e){
                 e.printStackTrace();
             }
        }
    }*/

}
