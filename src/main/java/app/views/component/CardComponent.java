package app.views.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class CardComponent extends GridPane {
    private final Label titleLabel = new Label();
    private final Label ratingLabel = new Label();
    private final Label descriptionLabel = new Label();
    private final Button actionButton = new Button();
    private final Button actionButton2 = new Button(); // Second action button
    private final ImageView imageView = new ImageView();

    public CardComponent() {
        setHgap(20);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        setEffect(new javafx.scene.effect.DropShadow(5, Color.gray(0.3)));
        setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT); // RTL layout

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setMinSize(150, 150);

        VBox textColumn = new VBox(10);
        textColumn.setAlignment(Pos.TOP_LEFT);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.getChildren().addAll(actionButton, actionButton2);

        actionButton2.setVisible(false);
        actionButton2.setManaged(false);

        titleLabel.getStyleClass().add("card-title");
        ratingLabel.getStyleClass().add("card-rating");
        descriptionLabel.getStyleClass().add("card-description");
        actionButton.getStyleClass().add("action-button");
        actionButton2.getStyleClass().add("action-button2"); // Different style

        textColumn.getChildren().addAll(titleLabel, ratingLabel, descriptionLabel, buttonContainer);

        add(imageContainer, 0, 0); // Image on RIGHT
        add(textColumn, 1, 0);     // Text on LEFT

        // Column constraints - REVERSED ORDER
        ColumnConstraints imageCol = new ColumnConstraints();
        imageCol.setPercentWidth(30);
        imageCol.setHgrow(Priority.NEVER);

        ColumnConstraints textCol = new ColumnConstraints();
        textCol.setPercentWidth(70);
        textCol.setHgrow(Priority.ALWAYS);

        getColumnConstraints().addAll(imageCol, textCol); // Image first, then text
    }

    public void setImage(String imageUrl) {
        try {
            String fullPath = getClass().getResource(imageUrl) != null ?
                    Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm() :
                    "file:" + System.getProperty("user.dir") + imageUrl;

            javafx.scene.image.Image image = new javafx.scene.image.Image(fullPath);
            imageView.setImage(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            // Placeholder for missing image
            imageView.setImage(null);
            StackPane placeholder = new StackPane(new Label("تصویر موجود نیست"));
            placeholder.setStyle("-fx-background-color: #e0e0e0;");
            add(placeholder, 0, 0);
        }
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setRating(String rating) {
        ratingLabel.setText("★ " + rating);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setAction(String text, Runnable handler) {
        actionButton.setText(text);
        actionButton.setOnAction(e -> handler.run());
    }

    public void setAction2(String text, Runnable handler) {
        if (text != null && !text.isEmpty()) {
            actionButton2.setText(text);
            actionButton2.setOnAction(e -> handler.run());
            actionButton2.setVisible(true);
            actionButton2.setManaged(true);
        } else {
            actionButton2.setVisible(false);
            actionButton2.setManaged(false);
        }
    }
}
