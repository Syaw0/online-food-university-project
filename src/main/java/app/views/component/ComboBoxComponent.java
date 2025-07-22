package app.views.component;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

/**
 * Material-style ComboBox component.
 */
public class ComboBoxComponent extends VBox {
    private final Label label;
    private final ComboBox<String> comboBox;

    public ComboBoxComponent(String labelText, String... items) {
        super(4);
        setAlignment(Pos.CENTER_LEFT);

        label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #616161; -fx-padding: 0 0 4 0;");

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-border-radius: 4px;" +
            "-fx-background-radius: 4px;" +
            "-fx-border-color: #bdbdbd;" +
            "-fx-border-width: 1;" +
            "-fx-font-size: 15px;" +
            "-fx-text-fill: #212121;" +
            "-fx-padding: 0;"
        );

        getChildren().addAll(label, comboBox);
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public void setValue(String value) {
        comboBox.setValue(value);
    }

    public String getValue() {
        return comboBox.getValue();
    }

    public void setOnAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        comboBox.setOnAction(handler);
    }
}