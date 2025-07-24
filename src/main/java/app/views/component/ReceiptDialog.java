package app.views.component;

import app.models.Food;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Map;

public class ReceiptDialog extends Dialog<Void> {
    private final ToggleGroup paymentGroup = new ToggleGroup();
    private double walletBalance = 100000; 

    public ReceiptDialog(Map<Food, Integer> cartItems, int subtotal, int tax, int delivery, int total,Boolean isViewMode) {
        setTitle("رسید پرداخت");
        setHeaderText(isViewMode? "اطلاعات سفارش شما" :"تأیید نهایی خرید");
        getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);


        VBox receiptContainer = new VBox(20);
        receiptContainer.setPadding(new Insets(20));
        receiptContainer.setStyle("-fx-background-color: white;");

        
        Label title = new Label("رسید سفارش");
        title.setFont(Font.font(18));
        title.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");
        receiptContainer.getChildren().add(title);

        
        GridPane summaryGrid = new GridPane();
        summaryGrid.setHgap(10);
        summaryGrid.setVgap(5);
        summaryGrid.setPadding(new Insets(10));

        summaryGrid.add(new Label("جمع اقلام:"), 0, 0);
        summaryGrid.add(new Label(subtotal + " تومان"), 1, 0);
        summaryGrid.add(new Label("مالیات (۱۰٪):"), 0, 1);
        summaryGrid.add(new Label(tax + " تومان"), 1, 1);
        summaryGrid.add(new Label("هزینه ارسال:"), 0, 2);
        summaryGrid.add(new Label(delivery + " تومان"), 1, 2);
        summaryGrid.add(new Label("مبلغ قابل پرداخت:"), 0, 3);
        Label totalLabel = new Label(total + " تومان");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        summaryGrid.add(totalLabel, 1, 3);

        receiptContainer.getChildren().add(summaryGrid);

        
        HBox divider = new HBox();
        divider.setMinHeight(1);
        divider.setStyle("-fx-background-color: #e0e0e0;");
        receiptContainer.getChildren().add(divider);

        
        VBox itemsBox = new VBox(10);
        Label itemsTitle = new Label("آیتم‌های سفارش");
        itemsTitle.setStyle("-fx-font-weight: bold;");
        itemsBox.getChildren().add(itemsTitle);

        for (Map.Entry<Food, Integer> entry : cartItems.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = Integer.parseInt(food.getPrice()) * quantity;

            HBox itemRow = new HBox(10);
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(food.getName() + " × " + quantity);
            nameLabel.setStyle("-fx-font-size: 14;");

            Label priceLabel = new Label(itemTotal + " تومان");
            priceLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #757575;");

            itemRow.getChildren().addAll(priceLabel, nameLabel);
            itemsBox.getChildren().add(itemRow);
        }
        receiptContainer.getChildren().add(itemsBox);

        
        if(!isViewMode){
            VBox paymentBox = new VBox(10);
        Label paymentTitle = new Label("روش پرداخت");
        paymentTitle.setStyle("-fx-font-weight: bold;");
        paymentBox.getChildren().add(paymentTitle);


        HBox walletBox = new HBox(10);
        RadioButton walletRadio = new RadioButton("پرداخت از کیف پول");
        walletRadio.setToggleGroup(paymentGroup);
        walletBox.getChildren().add(walletRadio);

        Label walletBalanceLabel = new Label("(موجودی: " + walletBalance + " تومان)");
        walletBalanceLabel.setStyle("-fx-text-fill: #757575;");
        walletBox.getChildren().add(walletBalanceLabel);
        paymentBox.getChildren().add(walletBox);


        RadioButton onlineRadio = new RadioButton("پرداخت آنلاین");
        onlineRadio.setToggleGroup(paymentGroup);
        onlineRadio.setSelected(true);
        paymentBox.getChildren().add(onlineRadio);

        receiptContainer.getChildren().add(paymentBox);


        Button payButton = new Button("پرداخت و ثبت سفارش");
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        payButton.setOnAction(e -> {

            showSuccess();
            close();
        });
        HBox buttonBox = new HBox(payButton);
        buttonBox.setAlignment(Pos.CENTER);
        receiptContainer.getChildren().add(buttonBox);
        }


        getDialogPane().setContent(receiptContainer);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText("سفارش شما با موفقیت ثبت شد!");
        alert.showAndWait();
    }
}
