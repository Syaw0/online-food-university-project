package app.views.pages.buyer;

import app.mock.FoodRepo;
import app.models.Food;
import app.views.component.ReceiptDialog;
import app.views.component.Typography;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class CartPage extends VBox {
    private final Map<Food, Integer> cartItems = new HashMap<>();
    private final FoodRepo foodRepo = new FoodRepo();
    private final VBox itemsContainer = new VBox(10);
    private final Label totalItemsLabel = new Label();
    private final Label subtotalLabel = new Label();
    private final Label taxLabel = new Label();
    private final Label deliveryLabel = new Label();
    private final Label totalLabel = new Label();

    public CartPage() {
        initializeUI();
        loadMockCart();
    }

    private void initializeUI() {
        setSpacing(30);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.web("#f7f7f7"), CornerRadii.EMPTY, Insets.EMPTY)));


        Typography title = new Typography("سبد خرید", Typography.Variant.H1);
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);


        VBox content = new VBox(30);


        VBox summarySection = createSummarySection();
        content.getChildren().add(summarySection);


        VBox itemsSection = createItemsSection();
        content.getChildren().add(itemsSection);


        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        getChildren().addAll(titleBox, scrollPane);
    }

    private VBox createSummarySection() {
        VBox summarySection = new VBox(15);
        summarySection.setPadding(new Insets(20));
        summarySection.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));


        Typography sectionTitle = new Typography("خلاصه سفارش", Typography.Variant.H2);


        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));


        grid.add(createSummaryLabel("تعداد آیتم‌ها:"), 0, 0);
        grid.add(totalItemsLabel, 1, 0);
        grid.add(createSummaryLabel("مجموع قیمت:"), 0, 1);
        grid.add(subtotalLabel, 1, 1);
        grid.add(createSummaryLabel("مالیات (۱۰٪):"), 0, 2);
        grid.add(taxLabel, 1, 2);
        grid.add(createSummaryLabel("هزینه ارسال:"), 0, 3);
        grid.add(deliveryLabel, 1, 3);


        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));


        grid.add(createSummaryLabel("مبلغ قابل پرداخت:", true), 0, 4);
        grid.add(totalLabel, 1, 4);


        Button submitButton = new Button("ثبت سفارش");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        submitButton.setOnAction(e -> submitOrder());
        HBox buttonBox = new HBox(submitButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        summarySection.getChildren().addAll(sectionTitle, grid, separator, buttonBox);
        return summarySection;
    }

    private VBox createItemsSection() {
        VBox itemsSection = new VBox(15);
        itemsSection.setPadding(new Insets(20));
        itemsSection.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));


        Typography sectionTitle = new Typography("محصولات سبد خرید", Typography.Variant.H2);


        itemsContainer.setPadding(new Insets(10));


        ScrollPane scrollPane = new ScrollPane(itemsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMinHeight(300);

        itemsSection.getChildren().addAll(sectionTitle, scrollPane);
        return itemsSection;
    }

    private Label createSummaryLabel(String text) {
        return createSummaryLabel(text, false);
    }

    private Label createSummaryLabel(String text, boolean bold) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14; " + (bold ? "-fx-font-weight: bold;" : ""));
        return label;
    }

    private void loadMockCart() {

        Food food1 = foodRepo.findById("1");
        Food food3 = foodRepo.findById("3");
        Food food5 = foodRepo.findById("5");

        cartItems.put(food1, 2);
        cartItems.put(food3, 1);
        cartItems.put(food5, 3);

        refreshCartItems();
        updateSummary();
    }

    private void refreshCartItems() {
        itemsContainer.getChildren().clear();

        for (Map.Entry<Food, Integer> entry : cartItems.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();

            HBox itemBox = createCartItem(food, quantity);
            itemsContainer.getChildren().add(itemBox);
        }
    }

    private HBox createCartItem(Food food, int quantity) {
        HBox itemBox = new HBox(15);
        itemBox.setPadding(new Insets(15));
        itemBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-border-radius: 5px;");
        itemBox.setAlignment(Pos.CENTER);


        ImageView imageView = new ImageView();
        try {
            String fullPath = getClass().getResource(food.getImage()) != null ?
                    getClass().getResource(food.getImage()).toExternalForm() :
                    "file:" + System.getProperty("user.dir") + food.getImage();
            imageView.setImage(new Image(fullPath, 60, 60, true, true));
        } catch (Exception e) {
            StackPane placeholder = new StackPane(new Label("تصویر"));
            placeholder.setStyle("-fx-background-color: #e0e0e0; -fx-min-width: 60; -fx-min-height: 60;");
            itemBox.getChildren().add(placeholder);
        }


        VBox infoBox = new VBox(5);
        infoBox.setMinWidth(200);

        Label nameLabel = new Label(food.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label priceLabel = new Label(food.getPrice() + " تومان");
        priceLabel.setStyle("-fx-text-fill: #757575;");

        infoBox.getChildren().addAll(nameLabel, priceLabel);


        HBox controlsBox = new HBox(5);
        controlsBox.setAlignment(Pos.CENTER);

        Button removeOneBtn = new Button("-");
        removeOneBtn.setStyle("-fx-background-color: #f5f5f5; -fx-min-width: 30; -fx-min-height: 30;");
        removeOneBtn.setOnAction(e -> updateQuantity(food, quantity - 1));

        Label quantityLabel = new Label(String.valueOf(quantity));
        quantityLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 40;");

        Button addOneBtn = new Button("+");
        addOneBtn.setStyle("-fx-background-color: #f5f5f5; -fx-min-width: 30; -fx-min-height: 30;");
        addOneBtn.setOnAction(e -> updateQuantity(food, quantity + 1));

        controlsBox.getChildren().addAll(removeOneBtn, quantityLabel, addOneBtn);


        Button removeBtn = new Button("حذف");
        removeBtn.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #c62828; -fx-font-weight: bold;");
        removeBtn.setOnAction(e -> {
            cartItems.remove(food);
            refreshCartItems();
            updateSummary();
        });


        int totalPrice = Integer.parseInt(food.getPrice()) * quantity;
        Label totalPriceLabel = new Label(totalPrice + " تومان");
        totalPriceLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 80;");

        itemBox.getChildren().addAll(
                imageView,
                infoBox,
                controlsBox,
                totalPriceLabel,
                removeBtn
        );

        return itemBox;
    }

    private void updateQuantity(Food food, int newQuantity) {
        if (newQuantity <= 0) {
            cartItems.remove(food);
        } else {
            cartItems.put(food, newQuantity);
        }
        refreshCartItems();
        updateSummary();
    }

    private void updateSummary() {
        int subtotal = 0;
        int itemCount = 0;

        for (Map.Entry<Food, Integer> entry : cartItems.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            subtotal += Integer.parseInt(food.getPrice()) * quantity;
            itemCount += quantity;
        }

        int tax = (int) (subtotal * 0.1);
        int delivery = 15000;
        int total = subtotal + tax + delivery;

        totalItemsLabel.setText(itemCount + " آیتم");
        subtotalLabel.setText(subtotal + " تومان");
        taxLabel.setText(tax + " تومان");
        deliveryLabel.setText(delivery + " تومان");
        totalLabel.setText(total + " تومان");
    }

    private void submitOrder() {
        // Calculate values
        int subtotal = 0;
        for (Map.Entry<Food, Integer> entry : cartItems.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            subtotal += Integer.parseInt(food.getPrice()) * quantity;
        }
        int tax = (int) (subtotal * 0.1);
        int delivery = 15000;
        int total = subtotal + tax + delivery;

        // Show receipt dialog
        ReceiptDialog dialog = new ReceiptDialog(cartItems, subtotal, tax, delivery, total,false);
        dialog.showAndWait();
    }
}
