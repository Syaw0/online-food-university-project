package app.views.component;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FilterComponent extends VBox {
    public FilterComponent(String title, Node control) {
        setSpacing(5);
        setAlignment(Pos.CENTER);
        setPrefWidth(300);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        getChildren().addAll(titleLabel, control);
    }
}
