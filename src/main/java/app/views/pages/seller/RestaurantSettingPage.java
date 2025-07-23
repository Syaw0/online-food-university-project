package app.views.pages.seller;

import app.models.Restaurant;
import app.models.User;
import app.states.StateManager;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;

public class RestaurantSettingPage extends VBox {
    private final TextField nameField = new TextField();
    private final TextArea descriptionField = new TextArea();
    private final TextField addressField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField workTimeField = new TextField();
    private final ImageView logoImageView = new ImageView();
    private final Restaurant currentRestaurant;

    public RestaurantSettingPage() {
        Restaurant current = StateManager.getInstance().restaurantState.getCurrentRestaurant();

        // Create empty restaurant if null (shouldn't happen for sellers)
        if (current == null) {
            User currentUser = StateManager.getInstance().userState.getCurrentUser();
            current = new Restaurant(
                    null,
                    currentUser != null ? currentUser.getId() : "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "0"
            );
            StateManager.getInstance().restaurantState.setCurrentRestaurant(current);
        }

        this.currentRestaurant = current;
        initializeUI();
        loadRestaurantData();
    }

    private void initializeUI() {
        // Main container setup
        setPadding(new Insets(30));
        setSpacing(25);
        getStyleClass().add("restaurant-setting-page");

        // Title
        Label title = new Typography("تنظیمات رستوران", Typography.Variant.H1);
        title.getStyleClass().add("page-title");

        // Logo section
        VBox logoSection = createLogoSection();

        // Form section
        GridPane formGrid = createFormGrid();

        // Update button
        ButtonComponent updateButton = new ButtonComponent("ذخیره تغییرات", ButtonComponent.Variation.CONTAINED);
        updateButton.getStyleClass().add("update-button");
        updateButton.setOnAction(e -> validateAndUpdate());
        HBox buttonContainer = new HBox(updateButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        // Assemble layout
        getChildren().addAll(title, logoSection, formGrid, buttonContainer);
    }

    private VBox createLogoSection() {
        // Safe default image loading
        Image defaultImage = loadDefaultLogo();
        logoImageView.setImage(defaultImage);
        logoImageView.setFitHeight(200);
        logoImageView.setFitWidth(200);
        logoImageView.setPreserveRatio(true);
        logoImageView.getStyleClass().add("restaurant-logo");

        // Image upload controls
        ButtonComponent uploadButton = new ButtonComponent("تغییر لوگو", ButtonComponent.Variation.TEXT);
        ButtonComponent removeButton = new ButtonComponent("حذف لوگو", ButtonComponent.Variation.TEXT);
        removeButton.getStyleClass().add("remove-logo-button");
        HBox buttonBox = new HBox(15, uploadButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Event handlers
        uploadButton.setOnAction(e -> handleLogoUpload());
        removeButton.setOnAction(e -> {
            logoImageView.setImage(defaultImage);
            currentRestaurant.setLogo("");
        });

        VBox logoBox = new VBox(15, logoImageView, buttonBox);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.getStyleClass().add("logo-section");

        return logoBox;
    }

    private Image loadDefaultLogo() {
        try {
            InputStream is = getClass().getResourceAsStream("/images/default-restaurant.png");
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception e) {
            System.err.println("Error loading default logo: " + e.getMessage());
        }

        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.getStyleClass().add("restaurant-form");

        // Add column constraints for proper expansion
        ColumnConstraints labelColumn = new ColumnConstraints();
        labelColumn.setHgrow(Priority.NEVER);  // Labels don't expand

        ColumnConstraints fieldColumn = new ColumnConstraints();
        fieldColumn.setHgrow(Priority.ALWAYS);  // Input fields expand
        fieldColumn.setFillWidth(true);          // Critical for TextArea

        grid.getColumnConstraints().addAll(labelColumn, fieldColumn);

        // Form labels and fields
        String[] labels = {
                "نام رستوران:",
                "توضیحات:",
                "آدرس:",
                "تلفن:",
                "ساعت کاری:"
        };

        // Add form rows
        int rowIndex = 0;

        // Name field
        Label nameLabel = new Label(labels[0]);
        nameLabel.getStyleClass().add("form-label");
        grid.add(nameLabel, 0, rowIndex);
        grid.add(nameField, 1, rowIndex);
        nameField.getStyleClass().add("form-field");
        nameField.setPromptText("*");
        rowIndex++;

        // Description field - Fixed
        VBox textAreaContainer = new VBox(descriptionField);
        textAreaContainer.setAlignment(Pos.CENTER_LEFT);
        textAreaContainer.setFillWidth(true);
        textAreaContainer.setMaxWidth(Double.MAX_VALUE);

        // Configure text area
        descriptionField.setWrapText(true);
        descriptionField.setPrefRowCount(3);
        descriptionField.setMinHeight(100);
        descriptionField.setMaxWidth(Double.MAX_VALUE);

        // Add container instead of text area directly
        grid.add(textAreaContainer, 1, rowIndex);
        rowIndex++;

        // Address field
        Label addressLabel = new Label(labels[2]);
        addressLabel.getStyleClass().add("form-label");
        grid.add(addressLabel, 0, rowIndex);
        grid.add(addressField, 1, rowIndex);
        addressField.getStyleClass().add("form-field");
        addressField.setPromptText("*");
        rowIndex++;

        // Phone field
        Label phoneLabel = new Label(labels[3]);
        phoneLabel.getStyleClass().add("form-label");
        grid.add(phoneLabel, 0, rowIndex);
        grid.add(phoneField, 1, rowIndex);
        phoneField.getStyleClass().add("form-field");
        phoneField.setPromptText("*");
        rowIndex++;

        // Work time field
        Label workTimeLabel = new Label(labels[4]);
        workTimeLabel.getStyleClass().add("form-label");
        grid.add(workTimeLabel, 0, rowIndex);
        grid.add(workTimeField, 1, rowIndex);
        workTimeField.getStyleClass().add("form-field");
        workTimeField.setPromptText("*");

        return grid;
    }

    private void loadRestaurantData() {
        if (currentRestaurant != null) {
            nameField.setText(currentRestaurant.getName());
            descriptionField.setText(currentRestaurant.getDescription());
            addressField.setText(currentRestaurant.getAddress());
            phoneField.setText(currentRestaurant.getPhone());
            workTimeField.setText(currentRestaurant.getWorkTime());

            // Load logo if exists
            if (currentRestaurant.getLogo() != null && !currentRestaurant.getLogo().isEmpty()) {
                try {
                    logoImageView.setImage(new Image(currentRestaurant.getLogo()));
                } catch (Exception e) {
                    System.err.println("Error loading restaurant logo: " + e.getMessage());
                }
            }
        }
    }

    private void validateAndUpdate() {
        // Validation checks
        if (nameField.getText().isBlank()) {
            showAlert("خطا در فرم", "نام رستوران الزامی است", Alert.AlertType.ERROR);
            return;
        }
        if (addressField.getText().isBlank()) {
            showAlert("خطا در فرم", "آدرس الزامی است", Alert.AlertType.ERROR);
            return;
        }
        if (phoneField.getText().isBlank()) {
            showAlert("خطا در فرم", "شماره تلفن الزامی است", Alert.AlertType.ERROR);
            return;
        }
        if (workTimeField.getText().isBlank()) {
            showAlert("خطا در فرم", "ساعت کاری الزامی است", Alert.AlertType.ERROR);
            return;
        }

        // Update restaurant object
        currentRestaurant.setName(nameField.getText().trim());
        currentRestaurant.setDescription(descriptionField.getText().trim());
        currentRestaurant.setAddress(addressField.getText().trim());
        currentRestaurant.setPhone(phoneField.getText().trim());
        currentRestaurant.setWorkTime(workTimeField.getText().trim());


        callUpdateMethod();
    }

    private void handleLogoUpload() {
        // Logo upload simulation
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("آپلود لوگو");
        info.setHeaderText("لوگو تغییر کرد");
        info.setContentText("امکان آپلود فایل در نسخه بعدی اضافه خواهد شد");
        info.showAndWait();
    }

    private void callUpdateMethod() {
        System.out.println("RESTAURANT UPDATE METHOD CALLED");
        System.out.println("Updated Restaurant: " + currentRestaurant);
        showAlert("موفقیت", "اطلاعات رستوران با موفقیت به‌روزرسانی شد", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
