package week9.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import week9.model.Post;

import java.io.File;

public class CreatePostScene {
    private Stage stage;
    private ProfileScene profileScene;
    private Image selectedImage;
    private ImageView imagePreview;

    public CreatePostScene(Stage stage, ProfileScene profileScene) {
        this.stage = stage;
        this.profileScene = profileScene;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #fafafa;");

        // Title
        Label titleLabel = new Label("Create New Post");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #262626;");

        // Image preview
        imagePreview = new ImageView();
        imagePreview.setFitWidth(250);
        imagePreview.setFitHeight(250);
        imagePreview.setPreserveRatio(true);
        imagePreview.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        Button uploadButton = new Button("Upload Image");
        uploadButton.setStyle("-fx-background-color: #0095f6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        uploadButton.setOnAction(e -> selectImage());

        // Caption input
        TextArea captionArea = new TextArea();
        captionArea.setPromptText("Write a caption... (optional)");
        captionArea.setMaxWidth(280);
        captionArea.setMaxHeight(80);
        captionArea.setStyle("-fx-background-color: white; -fx-border-color: #dbdbdb; -fx-border-radius: 3;");

        // Buttons
        Button postButton = new Button("Post");
        postButton.setStyle("-fx-background-color: #0095f6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20; -fx-font-weight: bold;");
        postButton.setMaxWidth(280);
        postButton.setOnAction(e -> {
            if (selectedImage == null) {
                showAlert("Error", "Please select an image first");
                return;
            }
            
            String caption = captionArea.getText().trim();
            Post post = new Post(caption.isEmpty() ? null : caption, selectedImage);
            profileScene.addPost(post);
            profileScene.show();
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #dbdbdb; -fx-text-fill: #262626; -fx-background-radius: 5; -fx-padding: 10 20;");
        backButton.setMaxWidth(280);
        backButton.setOnAction(e -> profileScene.show());

        root.getChildren().addAll(titleLabel, imagePreview, uploadButton, captionArea, postButton, backButton);

        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Post Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            imagePreview.setImage(selectedImage);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}