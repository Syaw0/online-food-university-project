package app.views.pages.shared;

import app.models.User;
import app.models.UserType;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.InputStream;

public class ProfilePage extends VBox {
    private final TextField nameField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField emailField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField bankAccountField = new TextField();
    private final TextField addressField = new TextField();
    private final ImageView profileImageView = new ImageView();
    private final User currentUser;

    public ProfilePage(User user) {
        this.currentUser = user;
        initializeUI();
        loadUserData();
    }

    private void initializeUI() {
        // Main container setup
        setPadding(new Insets(20));
        setSpacing(20);
        setStyle("-fx-background-color: #f5f7fa;");

        // Title
        Label title = new Typography("پروفایل کاربر", Typography.Variant.H1);

        // Profile image section
        VBox imageSection = createImageSection();

        // Form section
        GridPane formGrid = createFormGrid();

        // Update button
        Button updateButton = new ButtonComponent("آپدیت پروفایل", ButtonComponent.Variation.CONTAINED);
        updateButton.setOnAction(e -> validateAndUpdate());
        updateButton.setMaxWidth(Double.MAX_VALUE);
        HBox buttonContainer = new HBox(updateButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        // Assemble layout
        getChildren().addAll(title, imageSection, formGrid, buttonContainer);
    }

    private VBox createImageSection() {
        // Safe default image loading
        Image defaultImage = loadDefaultImage();
        profileImageView.setImage(defaultImage);
        profileImageView.setFitHeight(300);
        profileImageView.setFitWidth(300);
        profileImageView.setPreserveRatio(true);

        // Image upload controls
        Button uploadButton = new ButtonComponent("تغییر عکس پروفایل", ButtonComponent.Variation.TEXT);
        Button removeButton = new ButtonComponent("حذف", ButtonComponent.Variation.TEXT);
        removeButton.setStyle("-fx-color: #f5f7fa;");
        HBox buttonBox = new HBox(10, uploadButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Event handlers
        uploadButton.setOnAction(e -> handleImageUpload());
        removeButton.setOnAction(e -> {
            profileImageView.setImage(defaultImage);
            currentUser.setProfile(null);
        });

        VBox imageBox = new VBox(10, profileImageView, buttonBox);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(15));
        imageBox.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        return imageBox;
    }

    private Image loadDefaultImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/images/default-profile.png");
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception e) {
            System.err.println("Error loading default image: " + e.getMessage());
        }

        // Fallback to minimal transparent image
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Form labels and fields
        String[] labels = {"نام ونام خانوادگی :", "شماره تماس :", "ایمیل :", "پسورد :", "شماره حساب بانکی :"};
        TextField[] fields = {nameField, phoneField, emailField, passwordField, bankAccountField};

        // Add form rows
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-weight: bold;");
            grid.add(label, 0, i);
            grid.add(fields[i], 1, i);
        }

        // Add address field conditionally
        int rowIndex = labels.length;
        if (shouldShowAddress()) {
            Label addressLabel = new Label("آدرس :");
            addressLabel.setStyle("-fx-font-weight: bold;");
            grid.add(addressLabel, 0, rowIndex);
            grid.add(addressField, 1, rowIndex);
            addressField.setPromptText("*");
            rowIndex++;
        }

        // Setup required fields
        nameField.setPromptText("*");
        phoneField.setPromptText("*");
        passwordField.setPromptText("*");

        return grid;
    }

    private boolean shouldShowAddress() {
        UserType type = currentUser.getUserType();
        return type != UserType.ADMIN && type != UserType.DELIVERY;
    }

    private void loadUserData() {
        nameField.setText(currentUser.getFullName());
        phoneField.setText(currentUser.getPhone());
        emailField.setText(currentUser.getEmail());
        passwordField.setText(currentUser.getPassword());
        bankAccountField.setText(currentUser.getBankAccountNumber());

        if (shouldShowAddress()) {
            addressField.setText(currentUser.getAddress());
        }

        // Load profile image if exists
        if (currentUser.getProfile() != null && !currentUser.getProfile().isEmpty()) {
            try {
                profileImageView.setImage(new Image(currentUser.getProfile()));
            } catch (Exception e) {
                System.err.println("Error loading profile image: " + e.getMessage());
            }
        }
    }

    private void validateAndUpdate() {
        // Validation checks for required fields
        if (nameField.getText().isBlank()) {
            showAlert("ایراد در تایید فرم","نام و نام خانوادگی نباید خالی باشد!", Alert.AlertType.ERROR);
            return;
        }
        if (phoneField.getText().isBlank()) {
            showAlert("ایراد در تایید فرم","وارد کردن شماره تماس اجباری می باشد.", Alert.AlertType.ERROR);
            return;
        }
        if (passwordField.getText().length() < 6) {
            showAlert("ایراد در تایید فرم","طول پسورد باید حداقل ۶ عدد باشد", Alert.AlertType.ERROR);
            return;
        }

        // Address validation only for eligible users
        if (shouldShowAddress() && addressField.getText().isBlank()) {
            showAlert("ایراد در تایید فرم","آدرس خود را وارد کنید", Alert.AlertType.ERROR);
            return;
        }

        // Update user object
        currentUser.setFullName(nameField.getText().trim());
        currentUser.setPhone(phoneField.getText().trim());
        currentUser.setEmail(emailField.getText().trim());
        currentUser.setPassword(passwordField.getText().trim());
        currentUser.setBankAccountNumber(bankAccountField.getText().trim());

        // Only update address if it's visible
        if (shouldShowAddress()) {
            currentUser.setAddress(addressField.getText().trim());
        }

        // Update profile image (handled separately)
        if (profileImageView.getImage() != null) {
            currentUser.setProfile("user-profile.jpg");
        }

        callUpdateMethod();
    }

    private void handleImageUpload() {
        // Image upload simulation
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("آپلود عکس پروفایل");
        info.setHeaderText("عکس پروفایل آپدیت شد");
        info.setContentText("فیچر آینده...");
        info.showAndWait();
    }

    private void callUpdateMethod() {
        currentUser.setEmail(emailField.getText().trim());
        currentUser.setPassword(passwordField.getText().trim());
        currentUser.setBankAccountNumber(bankAccountField.getText().trim());
        currentUser.setAddress(addressField.getText().trim());
        currentUser.setFullName(nameField.getText().trim());
        currentUser.setPhone(phoneField.getText().trim());

        showAlert("آپدیت پروفایل","پروفایل شما با موفقیت آپدیت شد", Alert.AlertType.INFORMATION);
        System.out.println("UPDATE METHOD CALLED");
        System.out.println("Updated User: " + currentUser);
    }

    private void showAlert(String title,String message,Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
