package app.views.component;

import javafx.scene.control.Button;

public class ButtonComponent extends Button {
    public enum Variation {
        CONTAINED, OUTLINED, TEXT
    }

    public ButtonComponent(String text, Variation variation) {
        super(text);
        applyVariation(variation);
    }

    public void setButtonColors(String backgroundColor, String textColor, String borderColor) {
        String style = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-border-color: %s;",
                backgroundColor, textColor, borderColor
        );
        this.setStyle(style);
    }

    private void applyVariation(Variation variation) {
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
