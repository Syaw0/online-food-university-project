package app.views.layout;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import app.views.component.LogoComponent;
import app.views.forms.auth.LoginForm;
import app.views.forms.auth.RegisterForm;

public class AuthLayout {

    private final BorderPane rightPane = new BorderPane();
    private final StackPane leftPane = new StackPane();
    private String currentImage = "/assets/images/auth-1.jpg";

    private LoginForm loginForm;
    private RegisterForm registerForm;

    public AuthLayout() {
        loginForm = new LoginForm(this::showRegisterForm);
        registerForm = new RegisterForm(this::showLoginForm);
    }

    public HBox getView() {
        HBox root = new HBox();
        root.getStyleClass().add("auth-layout-container");
        createLeftPane(currentImage);
        leftPane.getStyleClass().add("auth-left-pane");
        rightPane.getStyleClass().add("auth-right-pane");

        setupRightPaneWithForm(loginForm);

        root.getChildren().addAll(leftPane, rightPane);
        return root;
    }

    private void createLeftPane(String imagePath) {
        leftPane.getChildren().clear();
        leftPane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
        leftPane.setPrefWidth(700);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
    }

    private void setupRightPaneWithForm(Pane form) {
        BorderPane rightContent = new BorderPane();
        rightContent.setPrefWidth(400); 

        
        HBox logoBox = new HBox(new LogoComponent());
        logoBox.setAlignment(Pos.TOP_RIGHT);
        logoBox.setPadding(new Insets(32, 32, 0, 0));
        rightContent.setTop(logoBox);

        
        VBox formBox = new VBox(form);
        formBox.setAlignment(Pos.CENTER);
        rightContent.setCenter(formBox);

        rightPane.setCenter(rightContent);
    }

    private void showRegisterForm() {
        setupRightPaneWithForm(registerForm);
        currentImage = "/assets/images/auth-2.jpg";
        createLeftPane(currentImage);
    }

    private void showLoginForm() {
        setupRightPaneWithForm(loginForm);
        currentImage = "/assets/images/auth-1.jpg";
        createLeftPane(currentImage);
    }
}