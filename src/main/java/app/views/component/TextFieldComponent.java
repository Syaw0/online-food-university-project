package app.views.component;

import javafx.scene.control.TextField;

public class TextFieldComponent extends TextField {
    public TextFieldComponent() {
        super();
        this.getStyleClass().add("text-field");
    }
    public TextFieldComponent(String text) {
        super(text);
        this.getStyleClass().add("text-field");
    }
} 