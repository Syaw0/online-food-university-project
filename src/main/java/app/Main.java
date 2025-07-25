package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import app.states.StateManager;
import app.utils.NavigationController;

import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/assets/fonts/Vazirmatn-VariableFont_wght.ttf")).toExternalForm(), 14);

        NavigationController navigation = new NavigationController(stage);
        StateManager stateManager = StateManager.getInstance();
        stateManager.setNavigationController(navigation);

        navigation.showAuthLayout();
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
