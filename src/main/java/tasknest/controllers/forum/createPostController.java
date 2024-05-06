package tasknest.controllers.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasknest.models.Category;
import tasknest.models.Comments;
import tasknest.models.Posts;
import tasknest.services.CategoryService;
import tasknest.services.PostsService;
import tasknest.tests.MainFx;

import java.io.*;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class createPostController {

    @FXML
    private ComboBox<Posts> postsCombo;
    @FXML
    private TableView<Comments> commentTable;
    @FXML
    private Button ChooseImageButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Category> categoryField;

    @FXML
    private Text charNum;

    @FXML
    private TextField chatField;

    @FXML
    private TextArea contentField;

    @FXML
    private AnchorPane creationAnchorPane;

    @FXML
    public Button editButton;

    @FXML
    private ImageView geminiLogo;

    @FXML
    private TextField imagePathField;

    @FXML
    private ImageView logo;

    @FXML
    public Button postButton;
    public Posts post = new Posts();
    int postId = 0;

    CategoryService categoryService = new CategoryService();
    PostsService postsService = new PostsService();
    @FXML
    private TextField titleField;
    public void initialize(){
        geminiLogo.setImage(new Image("file:C:/Users/Oussema/Documents/esprit/pi_desktop/demo1/images/forum/chatgpt.png"));
        logo.setImage(new Image("file:C:/Users/Oussema/Documents/esprit/pi_desktop/demo1/images/forum/logo.png"));



        if(post != null){
            postId = post.getId();
            titleField.setText(post.getTitle());
            contentField.setText(post.getContent());
            imagePathField.setText(post.getImage_url());
            if (post.getContent()!=null)
                charNum.setText(String.valueOf(post.getContent().length())+"/20");
        }

        categoryField.setStyle("-fx-font: 28px \"Calibri\";-fx-background-color:  white;-fx-border-radius:20;-fx-border-color:   #6cb9b9;");

    }
    public void loadCombo(){
        List<Category> categories = categoryService.afficher();
        categoryField.getItems().addAll(categories);
        titleField.setText(post.getTitle());
    }
    @FXML
    void askAI(MouseEvent event) {

        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "sk-proj-55bmaRonEvqVExlRasFIT3BlbkFJQz2LF3JFsNF2lvbciVSy"; // API key goes here
        String model = "gpt-3.5-turbo"; // current model of chatgpt api

        int maxRetries = 5;
        int currentRetry = 0;
        long initialBackoffMillis = 1000; // 1 second
        long maxBackoffMillis = 60000; // 1 minute
        long backoffMillis = initialBackoffMillis;
        while (currentRetry < maxRetries) {

            try {
                // Create the HTTP POST request
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "Bearer " + apiKey);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("organization","org-IMfuTxJoWyGP5Gg3m9qs90Wq");

                // Build the request body
                String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + chatField.getText() + "\"}]}";
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();

                // Get the response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                in.close();

                // returns the extracted contents of the response.
                System.out.println(extractContentFromResponse(response.toString()));
                String text = extractContentFromResponse(response.toString());
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < text.length(); i += 113) {
                    int end = Math.min(i + 113, text.length());
                    result.append(text.substring(i, end)).append("\n");
                }
                contentField.setText(result.toString());
                charNum.setText(String.valueOf(contentField.getText().length())+"/20");
                break;

            } catch (IOException e) {
                if (e instanceof HttpRetryException && ((HttpRetryException) e).responseCode() == 429) {
                    // Rate limited, retry after backoff
                    try {
                        Thread.sleep(backoffMillis);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    // Increase backoff for next retry, up to a maximum value
                    backoffMillis = Math.min(backoffMillis * 2, maxBackoffMillis);
                    currentRetry++;
                } else {
                    // Handle other IOExceptions
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

    @FXML
    void ChooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }
    @FXML
    void increment(KeyEvent event) {
        // charNum.setText(String.valueOf(contentField.getText().length())+"/20");
        int count = 0;
        for (char c : contentField.getText().toCharArray()) {
            if (!Character.isSpaceChar(c)) count++;
        }
        charNum.setText(String.valueOf(count)+"/20");

    }
    @FXML
    void post(MouseEvent event) throws SQLException {
        if (titleField.getText() == null || contentField.getText() == null || imagePathField.getText() == null || categoryField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");
            alert.setContentText("All fields are required.");
            alert.showAndWait();
            System.out.println(titleField.getText());
        } else if ((!Character.isLetter(titleField.getText().charAt(0))) || (titleField.getText().length() < 5)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");

            alert.setContentText("Title field should start with a letter and longer than 5 carachters.");


            alert.showAndWait();
        } else if (contentField.getText().length() < 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");

            alert.setContentText("Content has to be longer than 20 carachters.");


            alert.showAndWait();
        } else {

            String title = titleField.getText();
            String content = contentField.getText();
            int cat_id = categoryField.getSelectionModel().getSelectedItem().getId();
            String imagePath = imagePathField.getText();
            Posts post = new Posts(title, content, MainFx.getSession().getId(), cat_id, imagePath);
            System.out.println(post);
            postsService.ajouter(post);

            goToPost(post);

        }
    }
    public void goToPost(Posts post) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/post.fxml"));
        AnchorPane nextAnchorPane;

        if(loader.getController()==null)
            System.out.println("single post controller is null ");
        try{
            nextAnchorPane = loader.load();
        } catch (IOException e) {
            System.err.println("Error loading single post scene FXML: " + e.getMessage());
            return;
        }

        postController controller = loader.getController();
        controller.post=post;
        controller.initialize();

        creationAnchorPane.getChildren().removeAll();
        creationAnchorPane.getChildren().setAll(nextAnchorPane);
    }

    @FXML
    void cancel(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/forum/categories.fxml"));
        AnchorPane nextAnchorPane = null;

        if(loader.getController()==null)
            System.out.println("single post controller is null ");
        try{
            nextAnchorPane = loader.load();
        } catch (IOException e) {
            System.err.println("Error loading single post scene FXML: " + e.getMessage());
        }

        creationAnchorPane.getChildren().removeAll();
        creationAnchorPane.getChildren().setAll(nextAnchorPane);
    }
    @FXML
    void edit(MouseEvent event) throws SQLException {
        if (titleField.getText() == null || contentField.getText() == null || imagePathField.getText() == null || categoryField.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");
            alert.setContentText("All fields are required.");
            alert.showAndWait();
            System.out.println(titleField.getText());
        } else if ((!Character.isLetter(titleField.getText().charAt(0))) || (titleField.getText().length() < 5)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");

            alert.setContentText("Title field should start with a letter and longer than 5 carachters.");


            alert.showAndWait();
        } else if (contentField.getText().length() < 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Input error");

            alert.setContentText("Content has to be longer than 20 carachters.");


            alert.showAndWait();
        }
        else {

            String title = titleField.getText();
            String content = contentField.getText();
            int cat_id = categoryField.getSelectionModel().getSelectedItem().getId();
            String imagePath = imagePathField.getText();
            if(postId!=0) {
                Posts post = new Posts(postId, title, content, MainFx.getSession().getId(), cat_id, imagePath);
                System.out.println("post to edit: " + post + "post id: " + post.getId());
                postsService.modifier(post);

                goToPost(post);
            }
        }
    }
}
