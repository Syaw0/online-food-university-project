package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import app.views.layout.AuthLayout;
 
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResource("/assets/fonts/Vazirmatn-VariableFont_wght.ttf").toExternalForm(), 14);

        Scene scene = new Scene(new AuthLayout().getView(), 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/assets/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("FoodApp - Auth");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
