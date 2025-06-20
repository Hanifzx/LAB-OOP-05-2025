package week9.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import week9.model.User;

import java.io.File;

public class RegisterScene {
    private Stage stage;
    private Image selectedProfileImage;
    private ImageView profileImageView;

    public RegisterScene(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #fafafa;");

        // Title
        Label titleLabel = new Label("Create Account");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #262626;");

        // Profile Image
        profileImageView = new ImageView();
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        profileImageView.setPreserveRatio(true);
        
        // Default profile image
        try {
            selectedProfileImage = new Image(getClass().getResourceAsStream("/default-profile.png"));
            if (selectedProfileImage.isError()) {
                // Create a simple colored circle as default
                profileImageView.setStyle("-fx-background-color: #e1306c; -fx-background-radius: 50;");
            } else {
                profileImageView.setImage(selectedProfileImage);
            }
        } catch (Exception e) {
            profileImageView.setStyle("-fx-background-color: #e1306c; -fx-background-radius: 50;");
        }

        Button selectImageButton = new Button("Select Profile Photo");
        selectImageButton.setStyle("-fx-background-color: #0095f6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8 16;");
        selectImageButton.setOnAction(e -> selectProfileImage());

        // Input fields
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        fullNameField.setMaxWidth(280);
        fullNameField.setStyle("-fx-background-color: white; -fx-border-color: #dbdbdb; -fx-border-radius: 3; -fx-padding: 10;");

        TextField nickNameField = new TextField();
        nickNameField.setPromptText("Nickname");
        nickNameField.setMaxWidth(280);
        nickNameField.setStyle("-fx-background-color: white; -fx-border-color: #dbdbdb; -fx-border-radius: 3; -fx-padding: 10;");

        Button registerButton = new Button("Sign Up");
        registerButton.setStyle("-fx-background-color: #0095f6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20; -fx-font-weight: bold;");
        registerButton.setMaxWidth(280);
        registerButton.setOnAction(e -> {
            if (fullNameField.getText().trim().isEmpty() || nickNameField.getText().trim().isEmpty()) {
                showAlert("Error", "Please fill in all fields");
                return;
            }
            
            User user = new User(nickNameField.getText().trim(), fullNameField.getText().trim(), selectedProfileImage);
            ProfileScene profileScene = new ProfileScene(stage, user);
            profileScene.show();
        });

        root.getChildren().addAll(titleLabel, profileImageView, selectImageButton, fullNameField, nickNameField, registerButton);
        
        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void selectProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedProfileImage = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(selectedProfileImage);
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