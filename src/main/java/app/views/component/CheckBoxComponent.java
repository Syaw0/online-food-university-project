package app.views.component;

import javafx.scene.control.CheckBox;

public class CheckBoxComponent extends CheckBox {
    public CheckBoxComponent() {
        super();
        this.getStyleClass().add("checkbox");
    }
    public CheckBoxComponent(String text) {
        super(text);
        this.getStyleClass().add("checkbox");
    }
} 