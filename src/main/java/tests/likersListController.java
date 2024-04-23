package tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import models.Comments;
import models.User;

import java.io.IOException;
import java.util.List;

public class likersListController {

    @FXML
    private ListView<User> list;

    public List<User> users;
    private ObservableList<User> observableUsers;

    public void initialize() {
        if(users != null) {
            observableUsers = FXCollections.observableArrayList();

            observableUsers.addAll(users);
            list.setItems(observableUsers);

            list.setCellFactory(cell -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (user != null) {
                        // Load comment scene FXML

                        FXMLLoader loader = new FXMLLoader(MainProg.class.getResource("userCell.fxml"));
                        AnchorPane userPane;
                        try {
                            userPane = loader.load();
                        } catch (IOException e) {
                            System.err.println("Error loading comment scene FXML: " + e.getMessage());
                            return;
                        }
                        userCell controller = loader.getController();
                        if (controller == null)
                            System.out.println("controller is null");

                        controller.setUserData(user);
                        setText(null);

                        setGraphic(userPane);
                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            });
        }
    }
    public void showReactors() {

    }
}
