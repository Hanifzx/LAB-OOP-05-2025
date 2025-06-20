package week9;

import javafx.application.Application;
import javafx.stage.Stage;
import week9.scene.RegisterScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Social Media");
        primaryStage.setWidth(400);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        
        RegisterScene registerScene = new RegisterScene(primaryStage);
        registerScene.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}