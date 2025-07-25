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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        
        setPadding(new Insets(20));
        setSpacing(20);
        setStyle("-fx-background-color: #f5f7fa;");

        
        Label title = new Typography("پروفایل کاربر", Typography.Variant.H1);

        
        VBox imageSection = createImageSection();

        
        GridPane formGrid = createFormGrid();

        
        Button updateButton = new ButtonComponent("آپدیت پروفایل", ButtonComponent.Variation.CONTAINED);
        updateButton.setOnAction(e -> validateAndUpdate());
        updateButton.setMaxWidth(Double.MAX_VALUE);
        HBox buttonContainer = new HBox(updateButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        
        getChildren().addAll(title, imageSection, formGrid, buttonContainer);
    }

    private VBox createImageSection() {
        
        Image defaultImage = loadDefaultImage();
        profileImageView.setImage(defaultImage);
        profileImageView.setFitHeight(300);
        profileImageView.setFitWidth(300);
        profileImageView.setPreserveRatio(true);

        
        Button uploadButton = new ButtonComponent("تغییر عکس پروفایل", ButtonComponent.Variation.TEXT);
        Button removeButton = new ButtonComponent("حذف", ButtonComponent.Variation.TEXT);
        removeButton.setStyle("-fx-color: #f5f7fa;");
        HBox buttonBox = new HBox(10, uploadButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);

        
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
            InputStream is = getClass().getResourceAsStream("/assets/images/users/default.png");
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception e) {
            System.err.println("Error loading default image: " + e.getMessage());
        }

        
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        
        String[] labels = {"نام ونام خانوادگی :", "شماره تماس :", "ایمیل :", "پسورد :", "شماره حساب بانکی :"};
        TextField[] fields = {nameField, phoneField, emailField, passwordField, bankAccountField};

        
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-weight: bold;");
            grid.add(label, 0, i);
            grid.add(fields[i], 1, i);
        }

        
        int rowIndex = labels.length;
        if (shouldShowAddress()) {
            Label addressLabel = new Label("آدرس :");
            addressLabel.setStyle("-fx-font-weight: bold;");
            grid.add(addressLabel, 0, rowIndex);
            grid.add(addressField, 1, rowIndex);
            addressField.setPromptText("*");
            rowIndex++;
        }

        
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

        
        if (currentUser.getProfile() != null && !currentUser.getProfile().isEmpty()) {
            try {
                profileImageView.setImage(new Image(currentUser.getProfile()));
            } catch (Exception e) {
                System.err.println("Error loading profile image: " + e.getMessage());
            }
        }

        if (currentUser.getProfile() != null && !currentUser.getProfile().isEmpty()) {
            try {
                
                String profileUri = currentUser.getProfile();
                if (profileUri.startsWith("file:")) {
                    profileImageView.setImage(new Image(profileUri));
                } else {
                    
                    profileImageView.setImage(new Image(profileUri));
                }
            } catch (Exception e) {
                System.err.println("Error loading profile image: " + e.getMessage());
            }
        }
    }

    private void validateAndUpdate() {
        
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

        
        if (shouldShowAddress() && addressField.getText().isBlank()) {
            showAlert("ایراد در تایید فرم","آدرس خود را وارد کنید", Alert.AlertType.ERROR);
            return;
        }

        
        currentUser.setFullName(nameField.getText().trim());
        currentUser.setPhone(phoneField.getText().trim());
        currentUser.setEmail(emailField.getText().trim());
        currentUser.setPassword(passwordField.getText().trim());
        currentUser.setBankAccountNumber(bankAccountField.getText().trim());

        
        if (shouldShowAddress()) {
            currentUser.setAddress(addressField.getText().trim());
        }

        
        if (profileImageView.getImage() != null) {
            currentUser.setProfile("user-profile.jpg");
        }

        callUpdateMethod();
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

    private void handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("انتخاب عکس پروفایل");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                
                Image newImage = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(newImage);

                
                String profilePath = saveImageToStorage(selectedFile);
                currentUser.setProfile(profilePath);

                
                showAlert("آپلود موفق", "عکس پروفایل با موفقیت آپدیت شد", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("خطا در آپلود", "مشکلی در آپلود عکس پیش آمده: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private String saveImageToStorage(File imageFile) {
        try {
            
            Path profileDir = Paths.get("user-profiles");
            if (!Files.exists(profileDir)) {
                Files.createDirectories(profileDir);
            }

            
            String fileName = "profile_" + currentUser.getId() + "_"
                    + System.currentTimeMillis()
                    + getFileExtension(imageFile.getName());

            
            Path destination = profileDir.resolve(fileName);
            Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            return destination.toUri().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error saving image: " + e.getMessage(), e);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }


}
