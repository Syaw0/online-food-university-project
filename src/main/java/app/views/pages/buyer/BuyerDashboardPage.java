package app.views.pages.buyer;

import app.models.*;
import app.mock.*;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BuyerDashboardPage extends VBox {
    private final User currentUser;
    private final RestaurantRepo restaurantRepo;
    private final FoodRepo foodRepo;
    private final OrderRepo orderRepo;
    private final Random random = new Random();

    
    private static final String WELCOME_TEXT = "خوش آمديد";
    private static final String ORDERS_TEXT = "سفارش‌هاي فعال";
    private static final String RESTAURANTS_TEXT = "رستوران‌هاي برتر";
    private static final String FOODS_TEXT = "غذاهاي محبوب";
    private static final String PROMOTIONS_TEXT = "پيشنهادات ويژه";
    private static final String STATS_TEXT = "آمار شخصي";

    public BuyerDashboardPage(User user) {
        this.currentUser = user != null ? user : createDefaultUser();
        this.restaurantRepo = new RestaurantRepo();
        this.foodRepo = new FoodRepo();
        this.orderRepo = new OrderRepo();

        

        this.getStyleClass().add("buyer-dashboard-root");

        initializeUI();
    }

    private User createDefaultUser() {
        return new User("default", "کاربر", "00000000000", "123",
                "", "guest@example.com", UserType.BUYER,
                "", "", false);
    }

    private void initializeUI() {
        ScrollPane mainScroll = new ScrollPane();
        mainScroll.getStyleClass().add("dashboard-scroll-pane");
        mainScroll.setFitToWidth(true);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox content = new VBox();
        content.setSpacing(32);
        content.setPadding(new Insets(0, 16, 32, 16));

        try {
            
            content.getChildren().add(createSimpleWelcomeSection());
            content.getChildren().add(createSimpleOrdersSection());
            content.getChildren().add(createSimpleActionsSection());
            content.getChildren().add(createSimpleRestaurantsSection());
            content.getChildren().add(createSimpleFoodsSection());
            content.getChildren().add(createSimpleStatsSection());

        } catch (Exception e) {
            System.err.println("Error creating dashboard: " + e.getMessage());
            Label errorLabel = createSafeLabel("خطا در بارگذاري");
            content.getChildren().add(errorLabel);
        }

        mainScroll.setContent(content);
        this.getChildren().add(mainScroll);
        VBox.setVgrow(mainScroll, Priority.ALWAYS);
    }

    
    private Label createSafeLabel(String text) {
        return createSafeLabel(text, null);
    }

    private Label createSafeLabel(String text, String styleClass) {
        String safeText = cleanPersianText(text);
        Label label = new Label(safeText);

        
        try {
            label.setFont(Font.font("Arial", 14));
        } catch (Exception e) {
            
        }

        if (styleClass != null) {
            label.getStyleClass().add(styleClass);
        }

        return label;
    }

    
    private String cleanPersianText(String input) {
        if (input == null) return "";

        
        String cleaned = input.replaceAll("[\\p{Cntrl}]", "");

        
        cleaned = cleaned.replace("۰", "0").replace("۱", "1").replace("۲", "2")
                .replace("۳", "3").replace("۴", "4").replace("۵", "5")
                .replace("۶", "6").replace("۷", "7").replace("۸", "8")
                .replace("۹", "9");

        
        cleaned = cleaned.replace("‌", " "); 
        cleaned = cleaned.replace("‍", ""); 

        
        if (cleaned.length() > 50) {
            cleaned = cleaned.substring(0, 47) + "...";
        }

        return cleaned.trim();
    }

    private VBox createSimpleWelcomeSection() {
        VBox section = new VBox();
        section.getStyleClass().add("welcome-section");
        section.setSpacing(16);
        section.setPadding(new Insets(24));

        Label welcomeTitle = createSafeLabel(WELCOME_TEXT + " " + cleanPersianText(currentUser.getFullName()), "welcome-title");
        Label subtitle = createSafeLabel("بهترين رستوران ها", "welcome-subtitle");

        
        HBox statsBox = new HBox();
        statsBox.setSpacing(20);

        statsBox.getChildren().addAll(
                createSimpleStatCard("12", "سفارش"),
                createSimpleStatCard("8", "رستوران"),
                createSimpleStatCard("4.8", "امتياز"),
                createSimpleStatCard("23", "نظر")
        );

        section.getChildren().addAll(welcomeTitle, subtitle, statsBox);
        return section;
    }

    private VBox createSimpleStatCard(String number, String label) {
        VBox card = new VBox();
        card.getStyleClass().add("stat-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);

        Label numberLabel = createSafeLabel(cleanPersianText(number), "stat-number");
        Label labelText = createSafeLabel(cleanPersianText(label), "stat-label");

        card.getChildren().addAll(numberLabel, labelText);
        return card;
    }

    private VBox createSimpleOrdersSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(ORDERS_TEXT, "section-title");

        try {
            List<Order> orders = orderRepo.getInProgressOrders().stream().limit(2).toList();

            if (orders.isEmpty()) {
                Label emptyLabel = createSafeLabel("هيچ سفارش فعالي موجود نيست");
                emptyLabel.setStyle("-fx-text-fill: #666; -fx-padding: 20;");
                section.getChildren().addAll(header, emptyLabel);
            } else {
                VBox ordersContainer = new VBox();
                ordersContainer.setSpacing(12);

                for (Order order : orders) {
                    if (order != null) {
                        ordersContainer.getChildren().add(createSimpleOrderCard(order));
                    }
                }
                section.getChildren().addAll(header, ordersContainer);
            }
        } catch (Exception e) {
            Label errorLabel = createSafeLabel("خطا در بارگذاري سفارشات");
            section.getChildren().addAll(header, errorLabel);
        }

        return section;
    }

    private HBox createSimpleOrderCard(Order order) {
        HBox card = new HBox();
        card.getStyleClass().addAll("card", "order-card");
        card.setSpacing(16);
        card.setPadding(new Insets(16));

        VBox orderInfo = new VBox();
        orderInfo.setSpacing(8);

        String orderId = order.getId() != null ? order.getId() : "نامشخص";
        Label orderIdLabel = createSafeLabel("سفارش: " + cleanPersianText(orderId));

        String status = order.statusToPersian() != null ? order.statusToPersian() : "نامشخص";
        Label statusLabel = createSafeLabel("وضعيت: " + cleanPersianText(status));

        String customer = order.getCustomerName() != null ? order.getCustomerName() : "نامشخص";
        Label customerLabel = createSafeLabel("مشتري: " + cleanPersianText(customer));

        orderInfo.getChildren().addAll(orderIdLabel, statusLabel, customerLabel);
        card.getChildren().add(orderInfo);

        return card;
    }

    private VBox createSimpleActionsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel("دسترسي سريع", "section-title");

        HBox actionsBox = new HBox();
        actionsBox.setSpacing(16);

        String[] actions = {"جستجو", "علاقه مندي ها", "تاريخچه", "پيشنهادات", "پروفايل"};

        for (String action : actions) {
            Button actionBtn = new Button(cleanPersianText(action));
            actionBtn.getStyleClass().addAll("card", "quick-action-card");
            actionBtn.setPrefWidth(120);
            actionBtn.setPrefHeight(60);
            actionsBox.getChildren().add(actionBtn);
        }

        ScrollPane actionsScroll = new ScrollPane(actionsBox);
        actionsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        actionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        actionsScroll.setFitToHeight(true);

        section.getChildren().addAll(header, actionsScroll);
        return section;
    }

    private VBox createSimpleRestaurantsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(RESTAURANTS_TEXT, "section-title");

        try {
            List<Restaurant> restaurants = restaurantRepo.getAllRestaurants().stream()
                    .filter(r -> r != null && r.getName() != null)
                    .limit(3)
                    .toList();

            HBox restaurantsBox = new HBox();
            restaurantsBox.setSpacing(16);

            for (Restaurant restaurant : restaurants) {
                restaurantsBox.getChildren().add(createSimpleRestaurantCard(restaurant));
            }

            ScrollPane restaurantsScroll = new ScrollPane(restaurantsBox);
            restaurantsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            restaurantsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            restaurantsScroll.setFitToHeight(true);

            section.getChildren().addAll(header, restaurantsScroll);
        } catch (Exception e) {
            Label errorLabel = createSafeLabel("خطا در بارگذاري رستوران ها");
            section.getChildren().addAll(header, errorLabel);
        }

        return section;
    }

    private VBox createSimpleRestaurantCard(Restaurant restaurant) {
        VBox card = new VBox();
        card.getStyleClass().addAll("card", "restaurant-card");
        card.setSpacing(12);
        card.setPadding(new Insets(16));
        card.setPrefWidth(200);

        
        Label imagePlaceholder = new Label("[عکس رستوران]");
        imagePlaceholder.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 40; -fx-alignment: center;");
        ImageView image = this.createRestaurantImageView(restaurant);

        String name = restaurant.getName() != null ? restaurant.getName() : "رستوران";
        Label nameLabel = createSafeLabel(cleanPersianText(name), "restaurant-name");

        String rating = restaurant.getTotalRate() != null ? restaurant.getTotalRate() : "0";
        Label ratingLabel = createSafeLabel("امتياز: " + cleanPersianText(rating), "rating-text");

        card.getChildren().addAll(image, nameLabel, ratingLabel);
        return card;
    }

    private ImageView createRestaurantImageView(Restaurant restaurant) {
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(restaurant.getLogo()));
        } catch (Exception e) {
            imageView.setImage(new Image("/assets/images/restaurant/default.png"));
        }
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private ImageView createFoodImageView(Food food) {
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(food.getImage()));
        } catch (Exception e) {
            imageView.setImage(new Image("/assets/images/foods/default.png"));
        }
        imageView.setFitHeight(160);
        imageView.setFitWidth(160);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private VBox createSimpleFoodsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(FOODS_TEXT, "section-title");

        try {
            List<Food> foods = foodRepo.getAllFoods().stream()
                    .filter(f -> f != null && f.getName() != null)
                    .limit(4)
                    .toList();

            HBox foodsBox = new HBox();
            foodsBox.setSpacing(16);

            for (Food food : foods) {
                foodsBox.getChildren().add(createSimpleFoodCard(food));
            }

            ScrollPane foodsScroll = new ScrollPane(foodsBox);
            foodsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            foodsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            foodsScroll.setFitToHeight(true);

            section.getChildren().addAll(header, foodsScroll);
        } catch (Exception e) {
            Label errorLabel = createSafeLabel("خطا در بارگذاري غذاها");
            section.getChildren().addAll(header, errorLabel);
        }

        return section;
    }

    private VBox createSimpleFoodCard(Food food) {
        VBox card = new VBox();
        card.getStyleClass().addAll("card", "food-card");
        card.setSpacing(8);
        card.setPadding(new Insets(12));
        card.setPrefWidth(160);

        
        Label imagePlaceholder = new Label("[عکس غذا]");
        imagePlaceholder.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 30; -fx-alignment: center;");

        ImageView image = this.createFoodImageView(food);

        String name = food.getName() != null ? food.getName() : "غذا";
        Label nameLabel = createSafeLabel(cleanPersianText(name), "food-name");

        String price = formatSimplePrice(food.getPrice());
        Label priceLabel = createSafeLabel(price, "food-price");

        card.getChildren().addAll(image, nameLabel, priceLabel);
        return card;
    }

    private String formatSimplePrice(String priceStr) {
        try {
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                int price = Integer.parseInt(priceStr.trim());
                return price + " تومان";
            }
        } catch (NumberFormatException e) {
            
        }
        return "قيمت نامشخص";
    }

    private VBox createSimpleStatsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(STATS_TEXT, "section-title");

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(16);
        statsGrid.setVgap(16);

        String[][] stats = {
                {"تعداد سفارشات", "42"},
                {"ميانگين هزينه", "85000"},
                {"رستوران مورد علاقه", "رستوران شايان"},
                {"غذاي محبوب", "کباب"}
        };

        for (int i = 0; i < stats.length; i++) {
            VBox statCard = new VBox();
            statCard.getStyleClass().addAll("card", "stat-card");
            statCard.setAlignment(Pos.CENTER);
            statCard.setSpacing(8);
            statCard.setPadding(new Insets(16));

            Label value = createSafeLabel(cleanPersianText(stats[i][1]), "stat-number");
            Label label = createSafeLabel(cleanPersianText(stats[i][0]), "stat-label");

            statCard.getChildren().addAll(value, label);
            statsGrid.add(statCard, i % 2, i / 2);
        }

        section.getChildren().addAll(header, statsGrid);
        return section;
    }
}
