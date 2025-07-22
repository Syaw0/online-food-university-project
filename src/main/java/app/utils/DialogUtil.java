package app.utils;

import javafx.scene.control.Alert;

public class DialogUtil { 

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("اطلاع‌رسانی");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccess(String message) {
        // در JavaFX "Success" نداریم، پس از Information استفاده می‌کنیم
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
