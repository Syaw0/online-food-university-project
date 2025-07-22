package app.views.component;

import javafx.scene.control.Label;

public class Typography extends Label {
    public enum Variant {
        H1, H2, H3, BODY1, BODY2, LABEL
    }

    public Typography(String text, Variant variant) {
        super(text);
        this.getStyleClass().add("typography-root");
        switch (variant) {
            case H1:
                this.getStyleClass().add("typography-h1");
                break;
            case H2:
                this.getStyleClass().add("typography-h2");
                break;
            case H3:
                this.getStyleClass().add("typography-h3");
                break;
            case BODY1:
                this.getStyleClass().add("typography-body1");
                break;
            case BODY2:
                this.getStyleClass().add("typography-body2");
                break;
            case LABEL:
                this.getStyleClass().add("typography-label");
                break;
        }
    }
} 