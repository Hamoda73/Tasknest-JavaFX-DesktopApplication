package tasknest.controllers.forum;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import tasknest.models.Comments;
import tasknest.models.users;
import tasknest.tests.MainFx;
import tasknest.services.userrService;
import tasknest.services.CommentsService;

import java.util.Optional;


public class commentCellController {
    @FXML
    private Text commentDate;
    @FXML
    private ImageView delIcon;
    @FXML
    private TextArea commentField;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;

    private userrService userService= new userrService();
    private CommentsService commentsService= new CommentsService();
    Comments comment;
    public postController postcontroller;

    public void setCommentData(Comments comment) {

        this.comment = comment;
        users user = userService.getUserById(comment.getUser_id());
            this.commentDate.setText(comment.getDate().toString());
            this.commentField.setText(comment.getContent());
           this.userName.setText(user.getFname());
            Image img = new Image("file:"+user.getImage());
            this.userImage.setImage(img);
           // System.out.println(user.getImage());
            System.out.println(comment.getContent());
            if(comment.getUser_id()!=MainFx.getSession().getId())
                delIcon.setVisible(false);

    }
    @FXML
    void delC(MouseEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Needed");
        confirmationAlert.setHeaderText("Are you sure you want to delete this comment?");
        confirmationAlert.setContentText("This comment will be permanently deleted.");


        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            commentsService.delete(comment);
            postcontroller.reloadComments();
        }
        }
    }

