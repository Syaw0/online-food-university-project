package app.views;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import app.controllers.AuthController;

public class LoginView {
    public VBox getView() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button loginBtn = new Button("ورود");

        layout.getChildren().addAll(new Label("ورود"), username, password, loginBtn);

        loginBtn.setOnAction(e -> {
            AuthController.login(username.getText(), password.getText());
        });

        layout.setId("login-view"); // برای CSS
        return layout;
    }
}
