package tests;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.User;

public class userCell {
    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;

public void setUserData(User user){
    userName.setText(user.getFname());
}
}
