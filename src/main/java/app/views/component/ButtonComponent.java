package app.views.component;

import javafx.scene.control.Button;

public class ButtonComponent extends Button {
    public enum Variation {
        CONTAINED, OUTLINED, TEXT
    }

    public ButtonComponent(String text, Variation variation) {
        super(text);
        switch (variation) {
            case CONTAINED:
                this.getStyleClass().add("button-contained");
                break;
            case OUTLINED:
                this.getStyleClass().add("button-outlined");
                break;
            case TEXT:
                this.getStyleClass().add("button-text");
                break;
        }
    }
} 