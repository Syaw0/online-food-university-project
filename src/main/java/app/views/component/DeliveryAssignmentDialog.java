package app.views.component;

import app.models.DeliveryPerson;
import app.mock.DeliveryRepo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class DeliveryAssignmentDialog extends Dialog<DeliveryPerson> {
    private final ListView<DeliveryPerson> listView = new ListView<>();

    public DeliveryAssignmentDialog() {
        // Dialog setup
        this.setTitle("تخصیص پیک");
        this.setHeaderText("انتخاب پیک برای تحویل سفارش");

        // RTL direction for entire dialog
        this.getDialogPane().setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        // Button types
        ButtonType assignButtonType = new ButtonType("تخصیص", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(assignButtonType, ButtonType.CANCEL);

        // Content setup
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
//        content.setStyle("-fx-font-family: 'Tahoma'; -fx-font-size: 14px;");

        // List view setup
        listView.setPrefSize(350, 250);
        listView.setCellFactory(lv -> new ListCell<DeliveryPerson>() {
            @Override
            protected void updateItem(DeliveryPerson person, boolean empty) {
                super.updateItem(person, empty);
                if (empty || person == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a formatted string with proper RTL alignment
                    String text = String.format("%s\n\t%s\n\t\t%s\n\t\t\t%s",
                            person.getName(),
                            "موبایل: " + person.getPhone(),
                            "وضعیت: " + (person.isAvailable() ? "✅ موجود" : "❌ مشغول"),
                            "تحویل‌های جاری: " + person.getCurrentDeliveries()
                    );

                    setText(text);

                    // Style based on availability
                    if (person.isAvailable()) {
                        setTextFill(Color.GREEN);
                        setStyle("-fx-font-weight: bold");
                    } else {
                        setTextFill(Color.GRAY);
//                        setStyle("-fx-font-style: italic");
                    }

                    setDisable(!person.isAvailable());
                }
            }
        });

        // Load delivery persons
        DeliveryRepo repo = new DeliveryRepo();
        List<DeliveryPerson> persons = repo.getAllDeliveryPersons();
        listView.getItems().addAll(persons);

        Label titleLabel = new Label("لیست پیک‌های موجود:");
        titleLabel.setFont(new Font(14));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        content.getChildren().addAll(titleLabel, listView);
        this.getDialogPane().setContent(content);

        // Result conversion
        this.setResultConverter(dialogButton -> {
            if (dialogButton == assignButtonType) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });
    }
}
