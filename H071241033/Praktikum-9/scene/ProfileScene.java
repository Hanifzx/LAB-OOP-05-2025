package week9.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import week9.model.Post;
import week9.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileScene {
    private Stage stage;
    private User user;
    private List<Post> posts;
    private FlowPane postsContainer;

    public ProfileScene(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        this.posts = new ArrayList<>();
    }

    public void show() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #fafafa;");

        // Header
        VBox header = createHeader();
        
        // Posts container
        postsContainer = new FlowPane();
        postsContainer.setHgap(5);
        postsContainer.setVgap(5);
        postsContainer.setPadding(new Insets(20));
        postsContainer.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(postsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #fafafa; -fx-background: #fafafa;");

        root.getChildren().addAll(header, scrollPane);

        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: white; -fx-border-color: #dbdbdb; -fx-border-width: 0 0 1 0;");

        // Profile info container
        HBox profileInfo = new HBox(20);
        profileInfo.setAlignment(Pos.CENTER_LEFT);

        // Profile image
        ImageView profileImageView = new ImageView(user.getProfileImage());
        profileImageView.setFitWidth(80);
        profileImageView.setFitHeight(80);
        profileImageView.setPreserveRatio(true);
        profileImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        // User info
        VBox userInfo = new VBox(5);
        Label nickNameLabel = new Label(user.getNickName());
        nickNameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #262626;");
        
        Label fullNameLabel = new Label(user.getFullName());
        fullNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #8e8e8e;");
        
        userInfo.getChildren().addAll(nickNameLabel, fullNameLabel);

        profileInfo.getChildren().addAll(profileImageView, userInfo);

        // Post button
        Button postButton = new Button("New Post");
        postButton.setStyle("-fx-background-color: #0095f6; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20; -fx-font-weight: bold;");
        postButton.setMaxWidth(200);
        postButton.setOnAction(e -> {
            CreatePostScene createPostScene = new CreatePostScene(stage, this);
            createPostScene.show();
        });

        header.getChildren().addAll(profileInfo, postButton);
        return header;
    }

    public void addPost(Post post) {
        posts.add(post);
        updatePostsDisplay();
    }

    private void updatePostsDisplay() {
        postsContainer.getChildren().clear();
        
        for (Post post : posts) {
            ImageView postImageView = new ImageView(post.getPostImage());
            postImageView.setFitWidth(120);
            postImageView.setFitHeight(120);
            postImageView.setPreserveRatio(true);
            postImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0, 0, 1);");
            
            // Add hover effect to show caption
            if (post.getCaption() != null && !post.getCaption().trim().isEmpty()) {
                Tooltip tooltip = new Tooltip(post.getCaption());
                tooltip.setStyle("-fx-background-color: rgba(0,0,0,0.8); -fx-text-fill: white; -fx-background-radius: 5;");
                Tooltip.install(postImageView, tooltip);
            }
            
            postsContainer.getChildren().add(postImageView);
        }
    }
}