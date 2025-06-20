package week9;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    private TextField nicknameTextField = new TextField();
    private TextField fullnameTextField = new TextField();
    public User user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Profil");
        primaryStage.setWidth(360);
        primaryStage.setHeight(640);
        showInputUserAccount(primaryStage);
        primaryStage.show();
    }

    private void showInputUserAccount(Stage stage) {
        Label titleLabel = new Label("Buat akun");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // StackPane untuk gambar profil
        StackPane profileImageContainer = new StackPane();
        profileImageContainer.setPrefSize(100, 100);

        ImageView profileImage = new ImageView();
        profileImage.setFitHeight(100);
        profileImage.setFitWidth(100);
        profileImage.setPreserveRatio(true);
        profileImage.setSmooth(true); // Untuk rendering yang lebih halus
        
        // agar posisi image profil selalu di tengah circle
        Circle clip = new Circle(profileImage.getFitWidth() / 2, profileImage.getFitHeight() / 2, profileImage.getFitWidth() / 2);
        profileImage.setClip(clip);
        profileImage.setImage(createSolidColorImage(Color.LIGHTGRAY, 100, 100)); // Gambar abu-abu default

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2));
        profileImage.setEffect(dropShadow);

        profileImageContainer.getChildren().add(profileImage); 
        
        Label nicknameLabel = new Label("Nick Name");
        nicknameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        nicknameTextField.setPromptText("Masukkan nick name...");
        nicknameTextField.setMaxWidth(250);
        nicknameTextField.setStyle("-fx-control-inner-background: #fff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 8;");

        Label fullnameLabel = new Label("Full Name");
        fullnameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        fullnameTextField.setPromptText("Masukkan nama lengkap...");
        fullnameTextField.setMaxWidth(250);
        fullnameTextField.setStyle("-fx-control-inner-background: #fff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 8;");

        Button uploadFotoProfilButton = new Button("Pilih Foto Profil");
        uploadFotoProfilButton.setStyle("-fx-background-color: #3897f0; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 15; -fx-border-radius: 5;");
        uploadFotoProfilButton.setOnAction(e -> {
            File file = chooseFile(stage);
            if (file != null) {
                try {
                    Image image = new Image(file.toURI().toURL().toString());
                    profileImage.setImage(image);
                } catch (MalformedURLException ex) {
                    showError("Gagal memuat gambar.");
                }
            }
        });

        Button submitButton = new Button("Daftar");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-border-radius: 5;");
        submitButton.setMaxWidth(250);

        submitButton.setOnAction(event -> {
            if (nicknameTextField.getText().isEmpty() || fullnameTextField.getText().isEmpty()) {
                showError("Nick Name dan Full Name tidak boleh kosong.");
            } else {
                this.user = new User(nicknameTextField.getText(), fullnameTextField.getText(), profileImage);
                showProfileScene(stage);
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #FAFAFA;");

        root.getChildren().addAll(
                titleLabel,
                profileImageContainer,
                uploadFotoProfilButton,
                nicknameLabel,
                nicknameTextField,
                fullnameLabel,
                fullnameTextField,
                submitButton
        );

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Tugas Praktikum-9");
    }

    private void showProfileScene(Stage stage) {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10, 15, 10, 15));
        topBar.setStyle("-fx-background-color: #fff; -fx-border-width: 0 0 1px 0; -fx-border-color: #eee;");
        Label appName = new Label("Profil");
        appName.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        topBar.getChildren().add(appName);

        VBox profileHeader = new VBox(10);
        profileHeader.setPadding(new Insets(20));
        profileHeader.setAlignment(Pos.CENTER_LEFT);
        profileHeader.setStyle("-fx-background-color: #fff;");

        HBox profileInfo = new HBox(15);
        profileInfo.setAlignment(Pos.CENTER_LEFT);

        // Menggunakan StackPane untuk gambar profil di halaman profil
        StackPane profilePicContainer = new StackPane();
        profilePicContainer.setPrefSize(80, 80);

        ImageView profilePic = user.profileImage; // Menggunakan ImageView dari objek user
        profilePic.setFitWidth(80);
        profilePic.setFitHeight(80);
        profilePic.setPreserveRatio(true);
        profilePic.setSmooth(true);
        
        Circle profileClip = new Circle(profilePic.getFitWidth() / 2, profilePic.getFitHeight() / 2, profilePic.getFitWidth() / 2);
        profilePic.setClip(profileClip);

        profilePicContainer.getChildren().add(profilePic); 

        VBox names = new VBox(5);
        Label nickNameLabel = new Label(user.nickName);
        nickNameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #222;");
        Label fullNameLabel = new Label(user.fullName);
        fullNameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #555;");
        names.getChildren().addAll(nickNameLabel, fullNameLabel);

        profileInfo.getChildren().addAll(profilePicContainer, names); 

        Button addPostButton = new Button("Buat Postingan");
        addPostButton.setPrefWidth(Double.MAX_VALUE);
        addPostButton.setStyle("-fx-background-color: #3897f0; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 15; -fx-border-radius: 5;");
        addPostButton.setOnAction(event -> {
            showAddPostScene(stage);
        });

        profileHeader.getChildren().addAll(profileInfo, addPostButton);

        TilePane postTilePane = new TilePane();
        postTilePane.setPadding(new Insets(10));
        postTilePane.setHgap(5);
        postTilePane.setVgap(5);
        postTilePane.setAlignment(Pos.TOP_LEFT);

        updatePostDisplay(postTilePane);

        ScrollPane scrollPane = new ScrollPane(postTilePane);
        scrollPane.setFitToWidth(true);
        // Agar scrollpane mengisi ruang vertikal yang tersedia
        VBox.setVgrow(scrollPane, Priority.ALWAYS); 
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        VBox root = new VBox(0);
        root.getChildren().addAll(topBar, profileHeader, scrollPane);
        root.setStyle("-fx-background-color: #FAFAFA;");

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Profil " + user.nickName);
    }

    private void updatePostDisplay(TilePane postTilePane) {
        postTilePane.getChildren().clear();
        for (Post post : user.posts) {
            StackPane postItem = new StackPane();
            postItem.setPrefSize(110, 110);
            postItem.setStyle("-fx-background-color: #eee; -fx-border-color: #ddd; -fx-border-width: 1;");

            ImageView postImageView = post.postImage;
            postImageView.setFitWidth(100);
            postImageView.setFitHeight(100);
            postImageView.setPreserveRatio(true);

            Label captionOverlay = new Label(post.caption);
            captionOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 10px;");
            captionOverlay.setWrapText(true);
            captionOverlay.setMaxWidth(100);
            captionOverlay.setAlignment(Pos.BOTTOM_LEFT);
            captionOverlay.setVisible(false);

            postItem.getChildren().addAll(postImageView, captionOverlay);

            postItem.setOnMouseEntered(e -> captionOverlay.setVisible(true));
            postItem.setOnMouseExited(e -> captionOverlay.setVisible(false));

            postTilePane.getChildren().add(postItem);
        }
    }

    private void showAddPostScene(Stage stage) {
        Label titleLabel = new Label("Buat Postingan Baru");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ImageView postImageView = new ImageView();
        postImageView.setFitHeight(200);
        postImageView.setFitWidth(200);
        postImageView.setPreserveRatio(true);
        postImageView.setStyle("-fx-border-color: #ddd; -fx-border-width: 1;");

        Label imageStatusLabel = new Label("Pilih gambar untuk postingan Anda.");
        imageStatusLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Button uploadImageButton = new Button("Unggah Gambar");
        uploadImageButton.setStyle("-fx-background-color: #3897f0; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 15; -fx-border-radius: 5;");
        uploadImageButton.setOnAction(e -> {
            File file = chooseFile(stage);
            if (file != null) {
                try {
                    Image image = new Image(file.toURI().toURL().toString());
                    postImageView.setImage(image);
                    imageStatusLabel.setText("Gambar terpilih: " + file.getName());
                } catch (MalformedURLException ex) {
                    showError("Gagal memuat gambar.");
                    imageStatusLabel.setText("Gagal memuat gambar.");
                }
            }
        });

        TextArea captionTextArea = new TextArea();
        captionTextArea.setPromptText("Tulis caption (opsional)...");
        captionTextArea.setPrefRowCount(3);
        captionTextArea.setWrapText(true);
        captionTextArea.setMaxWidth(300);
        captionTextArea.setStyle("-fx-control-inner-background: #fff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 8;");

        Button submitButton = new Button("Post");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-border-radius: 5;");
        submitButton.setMaxWidth(250);
        submitButton.setOnAction(event -> {
            if (postImageView.getImage() == null) {
                showError("Mohon pilih gambar untuk postingan.");
            } else {
                Post post = new Post(captionTextArea.getText(), postImageView);
                user.addPost(post);
                showProfileScene(stage);
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #FAFAFA;");

        root.getChildren().addAll(titleLabel, uploadImageButton, imageStatusLabel, postImageView, captionTextArea, submitButton);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Buat Postingan");
    }

    private File chooseFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Gambar");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Gambar (*.jpg, *.png, *.jpeg, *.gif)", "*.jpg", "*.png", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(primaryStage);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Image createSolidColorImage(Color color, int width, int height) {
        WritableImage img = new WritableImage(width, height);
        PixelWriter pw = img.getPixelWriter();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pw.setColor(x, y, color);
            }
        }
        return img;
    }
}