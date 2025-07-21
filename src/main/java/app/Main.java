package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import app.views.LoginView;


public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // شروع با صفحه لاگین
        Parent root = new LoginView().getView();


        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/assets/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("FoodApp");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
