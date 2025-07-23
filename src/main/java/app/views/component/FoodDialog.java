package app.views.component;

import app.models.Food;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;

public class FoodDialog extends Dialog<Food> {
    private final TextField nameField = new TextField();
    private final TextArea descriptionField = new TextArea();
    private final TextField priceField = new TextField();
    private final TextField stockField = new TextField();
    private final ComboBox<String> categoryCombo = new ComboBox<>();
    private final ImageView foodImageView = new ImageView();
    private final Button uploadButton = new Button("آپلود تصویر");

    public FoodDialog(Food food, String restaurantId) {
        setTitle(food == null ? "افزودن غذا" : "ویرایش غذا");

        // Initialize controls
        foodImageView.setFitHeight(150);
        foodImageView.setFitWidth(150);
        foodImageView.setPreserveRatio(true);
        categoryCombo.getItems().addAll("غذای اصلی", "پیش غذا", "دسر", "نوشیدنی");

        // Set initial values
        if (food != null) {
            nameField.setText(food.getName());
            descriptionField.setText(food.getDescription());
            priceField.setText(food.getPrice());
            stockField.setText(food.getStock());
            categoryCombo.getSelectionModel().select(food.getCategoryId());
        }

        // Create form grid
        GridPane grid = new GridPane();
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("نام:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("توضیحات:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("قیمت:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("موجودی:"), 0, 3);
        grid.add(stockField, 1, 3);
        grid.add(new Label("دسته‌بندی:"), 0, 4);
        grid.add(categoryCombo, 1, 4);
        grid.add(new Label("تصویر:"), 0, 5);
        VBox imageBox = new VBox(10, foodImageView, uploadButton);
        grid.add(imageBox, 1, 5);

        // Upload button action
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(((Stage) getDialogPane().getScene().getWindow()));
            if (file != null) {
                foodImageView.setImage(new Image(file.toURI().toString()));
            }
        });

        // Button setup
        ButtonType saveButtonType = new ButtonType("ذخیره", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Validation
        Node saveButton = getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Validation logic
        nameField.textProperty().addListener((obs, oldVal, newVal) -> validateFields(saveButton));
        priceField.textProperty().addListener((obs, oldVal, newVal) -> validateFields(saveButton));
        stockField.textProperty().addListener((obs, oldVal, newVal) -> validateFields(saveButton));
        categoryCombo.valueProperty().addListener((obs, oldVal, newVal) -> validateFields(saveButton));

        getDialogPane().setContent(grid);

        // Convert result to Food object
        setResultConverter(buttonType -> {
            if (buttonType == saveButtonType) {
                return new Food(
                        food != null ? food.getId() : null,
                        restaurantId,
                        nameField.getText(),
                        descriptionField.getText(),
                        priceField.getText(),
                        stockField.getText(),
                        categoryCombo.getValue(),
                        foodImageView.getImage() != null ? foodImageView.getImage().getUrl() : "",
                        food != null ? food.getTotalRate() : "0"
                );
            }
            return null;
        });
    }

    private void validateFields(Node saveButton) {
        boolean isValid = !nameField.getText().isEmpty() &&
                !priceField.getText().isEmpty() &&
                !stockField.getText().isEmpty() &&
                categoryCombo.getValue() != null;
        saveButton.setDisable(!isValid);
    }
}
