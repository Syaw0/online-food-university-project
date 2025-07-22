package app.views.component;

import javafx.scene.control.PasswordField;

public class PasswordFieldComponent extends PasswordField {
    public PasswordFieldComponent() {
        super();
        this.getStyleClass().add("password-field");
    }
    public PasswordFieldComponent(String text) {
        super();
        this.setText(text);
        this.getStyleClass().add("password-field");
    }
} 