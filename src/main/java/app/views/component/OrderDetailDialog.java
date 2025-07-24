package app.views.component;

import app.models.Order;
import app.models.Order.FoodItem;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.StageStyle;

import java.util.List;

public class OrderDetailDialog extends Dialog<Void> {
    public OrderDetailDialog(Order order) {
        getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setTitle("جزئیات سفارش");
        setHeaderText("مشاهده اطلاعات کامل سفارش");


        getDialogPane().getStyleClass().add("order-detail-dialog");

        
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.getStyleClass().add("dialog-content");

        
        GridPane orderInfo = new GridPane();
        orderInfo.getStyleClass().add("order-info");
        orderInfo.setHgap(15);
        orderInfo.setVgap(10);

        orderInfo.add(createLabel("شماره سفارش:"), 0, 0);
        orderInfo.add(createValue(order.getId()), 1, 0);
        orderInfo.add(createLabel("مشتری:"), 0, 1);
        orderInfo.add(createValue(order.getCustomerName()), 1, 1);
        orderInfo.add(createLabel("تلفن:"), 0, 2);
        orderInfo.add(createValue(order.getCustomerPhone()), 1, 2);
        orderInfo.add(createLabel("تاریخ ایجاد:"), 0, 3);
        orderInfo.add(createValue(order.getCreationDate()), 1, 3);
        orderInfo.add(createLabel("وضعیت:"), 0, 4);
        orderInfo.add(createStatusLabel(order.getStatus().toString()), 1, 4);

        
        Label itemsHeader = new Label("آیتم‌های سفارش");
        itemsHeader.getStyleClass().add("items-header");

        VBox itemsContainer = new VBox(15);
        itemsContainer.getStyleClass().add("items-container");

        for (FoodItem item : order.getFoodItems()) {
            itemsContainer.getChildren().add(createFoodItemCard(item));
        }

        ScrollPane itemsScroll = new ScrollPane(itemsContainer);
        itemsScroll.setFitToWidth(true);
        itemsScroll.getStyleClass().add("items-scroll");

        content.getChildren().addAll(orderInfo, itemsHeader, itemsScroll);

        getDialogPane().setContent(content);

        
        ButtonType closeButton = new ButtonType("بستن", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(closeButton);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-label");
        return label;
    }

    private Label createValue(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-value");
        return label;
    }

    private Label createStatusLabel(String status) {
        Label label = new Label(toPersianStatus(status));
        label.getStyleClass().add("status-label");
        
        if (status.equals("PENDING")) {
            label.getStyleClass().add("status-pending");
        } else if (status.equals("ACCEPTED_BY_SELLER")) {
            label.getStyleClass().add("status-accepted");
        } else if (status.equals("REJECTED_BY_SELLER")) {
            label.getStyleClass().add("status-rejected");
        }
        return label;
    }

    private String toPersianStatus(String status) {
        return switch (status) {
            case "PENDING" -> "در انتظار تایید";
            case "ACCEPTED_BY_SELLER" -> "تایید شده توسط فروشنده";
            case "PREPARING" -> "در حال آماده سازی";
            case "GIVE_TO_DELIVERY" -> "تحویل به پیک";
            case "RECEIVED_BY_DELIVERY" -> "دریافت توسط پیک";
            case "DELIVERED_TO_CUSTOMER" -> "تحویل به مشتری";
            case "REJECTED_BY_SELLER" -> "رد شده توسط فروشنده";
            default -> status;
        };
    }

    private HBox createFoodItemCard(FoodItem item) {
        HBox card = new HBox(15);
        card.getStyleClass().add("food-item-card");

        
        ImageView image = new ImageView();
        try {
            if (item.getFood().getImage() != null && !item.getFood().getImage().isEmpty()) {
                image.setImage(new Image(item.getFood().getImage()));
            } else {
                image.setImage(new Image("file:src/main/resources/assets/images/default-food.png"));
            }
        } catch (Exception e) {
            image.setImage(new Image("file:src/main/resources/assets/images/default-food.png"));
        }
        image.setFitWidth(80);
        image.setFitHeight(80);
        image.setPreserveRatio(true);
        image.getStyleClass().add("food-image");

        
        VBox details = new VBox(5);
        details.getStyleClass().add("food-details");

        Label name = new Label(item.getFood().getName());
        name.getStyleClass().add("food-name");

        Label description = new Label(item.getFood().getDescription());
        description.getStyleClass().add("food-description");
        description.setWrapText(true);
        description.setMaxWidth(300);

        HBox meta = new HBox(20);
        Label price = new Label("قیمت: " + item.getFood().getPrice() + " تومان");
        price.getStyleClass().add("food-price");

        Label quantity = new Label("تعداد: " + item.getQuantity());
        quantity.getStyleClass().add("food-quantity");

        meta.getChildren().addAll(price, quantity);
        details.getChildren().addAll(name, description, meta);

        card.getChildren().addAll(image, details);
        return card;
    }
}
