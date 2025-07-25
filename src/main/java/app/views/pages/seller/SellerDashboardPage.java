package app.views.pages.seller;

import app.models.*;
import app.mock.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SellerDashboardPage extends VBox {
    private final User currentUser;
    private final RestaurantRepo restaurantRepo;
    private final FoodRepo foodRepo;
    private final OrderRepo orderRepo;
    private final Random random = new Random();

    // Safe Persian text constants
    private static final String WELCOME_TEXT = "خوش آمديد";
    private static final String ORDERS_TEXT = "مديريت سفارشات";
    private static final String SALES_TEXT = "آمار فروش";
    private static final String MENU_TEXT = "تحليل منو";
    private static final String CUSTOMERS_TEXT = "نظرات مشتريان";
    private static final String FINANCIAL_TEXT = "گزارش مالي";
    private static final String ACTIONS_TEXT = "عمليات سريع";

    public SellerDashboardPage(User user) {
        this.currentUser = user != null ? user : createDefaultUser();
        this.restaurantRepo = new RestaurantRepo();
        this.foodRepo = new FoodRepo();
        this.orderRepo = new OrderRepo();

        this.getStyleClass().add("seller-dashboard-root");

        initializeUI();
    }

    private User createDefaultUser() {
        return new User("default", "رستوران دار", "00000000000", "123",
                "", "seller@example.com", UserType.SELLER,
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
            // Create sections
            content.getChildren().add(createWelcomeSection());
            content.getChildren().add(createSalesOverviewSection());
            content.getChildren().add(createOrdersManagementSection());
            content.getChildren().add(createMenuAnalyticsSection());
            content.getChildren().add(createCustomerReviewsSection());
            content.getChildren().add(createFinancialSection());
            content.getChildren().add(createQuickActionsSection());

        } catch (Exception e) {
            System.err.println("Error creating seller dashboard: " + e.getMessage());
            Label errorLabel = createSafeLabel("خطا در بارگذاري داشبورد");
            content.getChildren().add(errorLabel);
        }

        mainScroll.setContent(content);
        this.getChildren().add(mainScroll);
        VBox.setVgrow(mainScroll, Priority.ALWAYS);
    }

    // Safe label creation methods
    private Label createSafeLabel(String text) {
        return createSafeLabel(text, null);
    }

    private Label createSafeLabel(String text, String styleClass) {
        String safeText = cleanPersianText(text);
        Label label = new Label(safeText);

        try {
            label.setFont(Font.font("Arial", 14));
        } catch (Exception e) {
            // Use default font if Arial not available
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

    private VBox createWelcomeSection() {
        VBox section = new VBox();
        section.getStyleClass().add("seller-welcome-section");
        section.setSpacing(16);
        section.setPadding(new Insets(24));

        Label welcomeTitle = createSafeLabel(WELCOME_TEXT + " " + cleanPersianText(currentUser.getFullName()), "welcome-title");
        Label subtitle = createSafeLabel("پنل مديريت رستوران", "welcome-subtitle");
        Label dateLabel = createSafeLabel("امروز: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), "date-label");

        // Restaurant info
        Restaurant userRestaurant = getUserRestaurant();
        if (userRestaurant != null) {
            Label restaurantName = createSafeLabel("رستوران: " + cleanPersianText(userRestaurant.getName()), "restaurant-name");
            section.getChildren().addAll(welcomeTitle, subtitle, restaurantName, dateLabel);
        } else {
            section.getChildren().addAll(welcomeTitle, subtitle, dateLabel);
        }

        // Quick stats
        HBox quickStats = createQuickStatsRow();
        section.getChildren().add(quickStats);

        return section;
    }

    private Restaurant getUserRestaurant() {
        List<Restaurant> userRestaurants = restaurantRepo.findByOwnerId(currentUser.getId());
        return userRestaurants.isEmpty() ? null : userRestaurants.get(0);
    }

    private HBox createQuickStatsRow() {
        HBox statsRow = new HBox();
        statsRow.setSpacing(20);
        statsRow.getStyleClass().add("quick-stats");

        // Generate fake stats
        statsRow.getChildren().addAll(
                createStatCard("23", "سفارش امروز", "#4CAF50"),
                createStatCard("1,250,000", "درآمد امروز", "#2196F3"),
                createStatCard("4.7", "امتياز رستوران", "#FF9800"),
                createStatCard("156", "مشتري فعال", "#9C27B0")
        );

        return statsRow;
    }

    private VBox createStatCard(String value, String label, String color) {
        VBox card = new VBox();
        card.getStyleClass().add("seller-stat-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(16));

        // Color indicator
        Rectangle colorBar = new Rectangle(40, 4);
        colorBar.setFill(Color.web(color));

        Label valueLabel = createSafeLabel(cleanPersianText(value), "stat-value");
        Label labelText = createSafeLabel(cleanPersianText(label), "stat-label");

        card.getChildren().addAll(colorBar, valueLabel, labelText);
        return card;
    }

    private VBox createSalesOverviewSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(SALES_TEXT, "section-title");

        // Sales chart simulation
        VBox chartContainer = new VBox();
        chartContainer.getStyleClass().add("chart-container");
        chartContainer.setPadding(new Insets(20));

        Label chartTitle = createSafeLabel("نمودار فروش هفتگي", "chart-title");

        // Simple bar chart simulation
        HBox barsContainer = createSimpleBarChart();

        // Chart legend
        HBox legend = createChartLegend();

        chartContainer.getChildren().addAll(chartTitle, barsContainer, legend);
        section.getChildren().addAll(header, chartContainer);

        return section;
    }

    private HBox createSimpleBarChart() {
        HBox barsContainer = new HBox();
        barsContainer.setSpacing(10);
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);
        barsContainer.setPadding(new Insets(20));

        String[] days = {"شنبه", "يک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه"};
        int[] sales = {850000, 1200000, 750000, 1100000, 950000, 1350000, 1600000};

        for (int i = 0; i < days.length; i++) {
            VBox barContainer = new VBox();
            barContainer.setAlignment(Pos.BOTTOM_CENTER);
            barContainer.setSpacing(5);

            // Bar
            Rectangle bar = new Rectangle(30, sales[i] / 10000.0); // Scale down for display
            bar.setFill(Color.web("#2196F3"));
            bar.getStyleClass().add("chart-bar");

            // Day label
            Label dayLabel = createSafeLabel(days[i], "chart-day-label");

            // Value label
            Label valueLabel = createSafeLabel(String.valueOf(sales[i] / 1000) + "K", "chart-value-label");

            barContainer.getChildren().addAll(valueLabel, bar, dayLabel);
            barsContainer.getChildren().add(barContainer);
        }

        return barsContainer;
    }

    private HBox createChartLegend() {
        HBox legend = new HBox();
        legend.setSpacing(20);
        legend.setAlignment(Pos.CENTER);

        Rectangle legendColor = new Rectangle(12, 12);
        legendColor.setFill(Color.web("#2196F3"));
        Label legendLabel = createSafeLabel("فروش (تومان)", "legend-label");

        legend.getChildren().addAll(legendColor, legendLabel);
        return legend;
    }

    private VBox createOrdersManagementSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(ORDERS_TEXT, "section-title");

        // Orders summary
        HBox ordersSummary = new HBox();
        ordersSummary.setSpacing(16);

        List<Order> pendingOrders = orderRepo.getPendingOrders();
        List<Order> inProgressOrders = orderRepo.getInProgressOrders();

        ordersSummary.getChildren().addAll(
                createOrderStatusCard("در انتظار تايد", String.valueOf(pendingOrders.size()), "#FF5722"),
                createOrderStatusCard("در حال آماده سازي", String.valueOf(inProgressOrders.size()), "#FF9800"),
                createOrderStatusCard("آماده تحويل", "3", "#4CAF50")
        );

        // Recent orders
        VBox recentOrders = createRecentOrdersList();

        section.getChildren().addAll(header, ordersSummary, recentOrders);
        return section;
    }

    private VBox createOrderStatusCard(String status, String count, String color) {
        VBox card = new VBox();
        card.getStyleClass().add("order-status-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(12);
        card.setPadding(new Insets(20));

        Rectangle indicator = new Rectangle(50, 4);
        indicator.setFill(Color.web(color));

        Label countLabel = createSafeLabel(cleanPersianText(count), "order-count");
        Label statusLabel = createSafeLabel(cleanPersianText(status), "order-status");

        card.getChildren().addAll(indicator, countLabel, statusLabel);
        return card;
    }

    private VBox createRecentOrdersList() {
        VBox container = new VBox();
        container.setSpacing(12);

        Label title = createSafeLabel("آخرين سفارشات", "subsection-title");
        container.getChildren().add(title);

        List<Order> recentOrders = orderRepo.getAllOrders().stream().limit(4).collect(Collectors.toList());

        for (Order order : recentOrders) {
            if (order != null) {
                container.getChildren().add(createOrderItem(order));
            }
        }

        return container;
    }

    private HBox createOrderItem(Order order) {
        HBox item = new HBox();
        item.getStyleClass().add("order-item");
        item.setSpacing(16);
        item.setPadding(new Insets(12));
        item.setAlignment(Pos.CENTER_LEFT);

        VBox orderInfo = new VBox();
        orderInfo.setSpacing(4);

        Label orderIdLabel = createSafeLabel("سفارش #" + cleanPersianText(order.getId()), "order-id-label");
        Label customerLabel = createSafeLabel("مشتري: " + cleanPersianText(order.getCustomerName()), "customer-label");
        Label statusLabel = createSafeLabel(cleanPersianText(order.statusToPersian()), "status-label");

        orderInfo.getChildren().addAll(orderIdLabel, customerLabel);

        // Status badge
        Label statusBadge = createSafeLabel(cleanPersianText(order.statusToPersian()), "status-badge");
        statusBadge.getStyleClass().add(getStatusStyleClass(order.getStatus()));

        item.getChildren().addAll(orderInfo, statusBadge);
        HBox.setHgrow(orderInfo, Priority.ALWAYS);

        return item;
    }

    private String getStatusStyleClass(Order.Status status) {
        if (status == null) return "status-pending";
        return switch (status) {
            case PENDING -> "status-pending";
            case PREPARING, ACCEPTED_BY_SELLER -> "status-preparing";
            case DELIVERED_TO_CUSTOMER -> "status-delivered";
            default -> "status-pending";
        };
    }

    private VBox createMenuAnalyticsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(MENU_TEXT, "section-title");

        // Top selling foods
        HBox topFoods = new HBox();
        topFoods.setSpacing(16);

        List<Food> foods = foodRepo.getAllFoods().stream().limit(4).collect(Collectors.toList());

        for (Food food : foods) {
            if (food != null) {
                topFoods.getChildren().add(createFoodAnalyticsCard(food));
            }
        }

        ScrollPane foodsScroll = new ScrollPane(topFoods);
        foodsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        foodsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        foodsScroll.setFitToHeight(true);

        section.getChildren().addAll(header, foodsScroll);
        return section;
    }

    private VBox createFoodAnalyticsCard(Food food) {
        VBox card = new VBox();
        card.getStyleClass().add("food-analytics-card");
        card.setSpacing(12);
        card.setPadding(new Insets(16));
        card.setPrefWidth(200);

        ImageView foodImage = createFoodImageView(food);

        Label nameLabel = createSafeLabel(cleanPersianText(food.getName()), "food-name");
        Label salesLabel = createSafeLabel("فروش: " + random.nextInt(50) + " عدد", "food-sales");
        Label revenueLabel = createSafeLabel("درآمد: " + random.nextInt(500) + "K", "food-revenue");
        Label ratingLabel = createSafeLabel("امتياز: " + cleanPersianText(food.getTotalRate()), "food-rating");

        card.getChildren().addAll(foodImage, nameLabel, salesLabel, revenueLabel, ratingLabel);
        return card;
    }

    private ImageView createFoodImageView(Food food) {
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(food.getImage()));
        } catch (Exception e) {
            imageView.setImage(new Image("/assets/images/foods/default.png"));
        }
        imageView.setFitHeight(120);
        imageView.setFitWidth(160);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private VBox createCustomerReviewsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(CUSTOMERS_TEXT, "section-title");

        // Reviews summary
        HBox reviewsSummary = new HBox();
        reviewsSummary.setSpacing(20);

        reviewsSummary.getChildren().addAll(
                createReviewSummaryCard("5 ستاره", "45%", "#4CAF50"),
                createReviewSummaryCard("4 ستاره", "30%", "#8BC34A"),
                createReviewSummaryCard("3 ستاره", "15%", "#FFC107"),
                createReviewSummaryCard("2 ستاره", "7%", "#FF9800"),
                createReviewSummaryCard("1 ستاره", "3%", "#F44336")
        );

        ScrollPane reviewsScroll = new ScrollPane(reviewsSummary);
        reviewsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        reviewsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        reviewsScroll.setFitToHeight(true);

        section.getChildren().addAll(header, reviewsScroll);
        return section;
    }

    private VBox createReviewSummaryCard(String rating, String percentage, String color) {
        VBox card = new VBox();
        card.getStyleClass().add("review-summary-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(16));
        card.setMinWidth(100);

        Rectangle bar = new Rectangle(60, 8);
        bar.setFill(Color.web(color));

        Label ratingLabel = createSafeLabel(cleanPersianText(rating), "rating-label");
        Label percentageLabel = createSafeLabel(cleanPersianText(percentage), "percentage-label");

        card.getChildren().addAll(ratingLabel, bar, percentageLabel);
        return card;
    }

    private VBox createFinancialSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(FINANCIAL_TEXT, "section-title");

        GridPane financialGrid = new GridPane();
        financialGrid.setHgap(20);
        financialGrid.setVgap(16);
        financialGrid.setPadding(new Insets(16));

        // Financial metrics
        String[][] metrics = {
                {"درآمد امروز", "1,250,000 تومان"},
                {"درآمد هفته", "8,750,000 تومان"},
                {"درآمد ماه", "35,000,000 تومان"},
                {"متوسط سفارش", "125,000 تومان"},
                {"تعداد سفارشات", "280 سفارش"},
                {"مشتريان جديد", "45 نفر"}
        };

        for (int i = 0; i < metrics.length; i++) {
            VBox metricCard = createFinancialMetricCard(metrics[i][0], metrics[i][1]);
            financialGrid.add(metricCard, i % 3, i / 3);
        }

        section.getChildren().addAll(header, financialGrid);
        return section;
    }

    private VBox createFinancialMetricCard(String label, String value) {
        VBox card = new VBox();
        card.getStyleClass().add("financial-metric-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(20));

        Label valueLabel = createSafeLabel(cleanPersianText(value), "metric-value");
        Label labelText = createSafeLabel(cleanPersianText(label), "metric-label");

        card.getChildren().addAll(valueLabel, labelText);
        return card;
    }

    private VBox createQuickActionsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(ACTIONS_TEXT, "section-title");

        HBox actionsRow = new HBox();
        actionsRow.setSpacing(16);

        String[] actions = {
                "مديريت منو", "تنظيمات رستوران", "گزارشات فروش",
                "پيام‌هاي مشتريان", "مديريت موجودي", "تحليل رقبا"
        };

        for (String action : actions) {
            Button actionBtn = new Button(cleanPersianText(action));
            actionBtn.getStyleClass().add("action-button");
            actionBtn.setPrefWidth(160);
            actionBtn.setPrefHeight(80);
            actionsRow.getChildren().add(actionBtn);
        }

        ScrollPane actionsScroll = new ScrollPane(actionsRow);
        actionsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        actionsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        actionsScroll.setFitToHeight(true);

        section.getChildren().addAll(header, actionsScroll);
        return section;
    }
}
