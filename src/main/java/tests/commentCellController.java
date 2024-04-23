package tests;

import javafx.fxml.Initializable;

import javax.print.DocFlavor;
import javax.xml.stream.events.Comment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.Comments;

public class commentCellController {

    @FXML
    private Text commentDate;

    @FXML
    private TextArea commentField;

    @FXML
    private Button dislikeButton;

    @FXML
    private Button likeButton;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;



    public void setCommentData(Comments comment) {
        this.commentDate.setText(comment.getDate().toString());
        this.commentField.setText(comment.getContent());
        System.out.println(comment.getContent());
    }
}
