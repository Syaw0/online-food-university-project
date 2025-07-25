package app.views.pages.delivery;

import app.models.*;
import app.mock.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DeliveryDashboardPage extends VBox {
    private final User currentUser;
    private final OrderRepo orderRepo;
    private final RestaurantRepo restaurantRepo;
    private final Random random = new Random();

    // Safe Persian text constants
    private static final String WELCOME_TEXT = "خوش آمديد";
    private static final String DELIVERY_STATUS_TEXT = "وضعيت تحويل";
    private static final String ACTIVE_ORDERS_TEXT = "سفارشات فعال";
    private static final String PERFORMANCE_TEXT = "آمار عملکرد";
    private static final String EARNINGS_TEXT = "درآمد روزانه";
    private static final String RATINGS_TEXT = "امتيازات و نظرات";
    private static final String AREAS_TEXT = "مناطق تحويل";
    private static final String ACTIONS_TEXT = "عمليات سريع";

    // Delivery status
    private boolean isOnline = true;

    public DeliveryDashboardPage(User user) {
        this.currentUser = user != null ? user : createDefaultUser();
        this.orderRepo = new OrderRepo();
        this.restaurantRepo = new RestaurantRepo();

        this.getStyleClass().add("delivery-dashboard-root");

        initializeUI();
    }

    private User createDefaultUser() {
        return new User("default", "پيک موتوري", "00000000000", "123",
                "", "delivery@example.com", UserType.DELIVERY,
                "", "", true);
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
            content.getChildren().add(createStatusToggleSection());
            content.getChildren().add(createActiveOrdersSection());
            content.getChildren().add(createPerformanceSection());
            content.getChildren().add(createEarningsSection());
            content.getChildren().add(createRatingsSection());
            content.getChildren().add(createDeliveryAreasSection());
            content.getChildren().add(createQuickActionsSection());

        } catch (Exception e) {
            System.err.println("Error creating delivery dashboard: " + e.getMessage());
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
        section.getStyleClass().add("delivery-welcome-section");
        section.setSpacing(16);
        section.setPadding(new Insets(24));

        Label welcomeTitle = createSafeLabel(WELCOME_TEXT + " " + cleanPersianText(currentUser.getFullName()), "welcome-title");
        Label subtitle = createSafeLabel("پنل پيک موتوري", "welcome-subtitle");
        Label dateLabel = createSafeLabel("امروز: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), "date-label");

        // Status indicator
        HBox statusBox = new HBox();
        statusBox.setSpacing(12);
        statusBox.setAlignment(Pos.CENTER_LEFT);

        Circle statusIndicator = new Circle(8);
        statusIndicator.setFill(isOnline ? Color.web("#4CAF50") : Color.web("#F44336"));

        Label statusLabel = createSafeLabel(isOnline ? "آنلاين" : "آفلاين", "status-label");
        statusLabel.setStyle("-fx-text-fill: " + (isOnline ? "#4CAF50" : "#F44336") + ";");

        statusBox.getChildren().addAll(statusIndicator, statusLabel);

        // Daily stats
        HBox dailyStats = createDailyStatsRow();

        section.getChildren().addAll(welcomeTitle, subtitle, dateLabel, statusBox, dailyStats);
        return section;
    }

    private HBox createDailyStatsRow() {
        HBox statsRow = new HBox();
        statsRow.setSpacing(20);
        statsRow.getStyleClass().add("daily-stats");

        // Generate fake daily stats
        statsRow.getChildren().addAll(
                createStatCard("12", "تحويل امروز", "#2196F3"),
                createStatCard("45", "کيلومتر", "#FF9800"),
                createStatCard("340,000", "درآمد امروز", "#4CAF50"),
                createStatCard("4.8", "امتياز", "#9C27B0")
        );

        return statsRow;
    }

    private VBox createStatCard(String value, String label, String color) {
        VBox card = new VBox();
        card.getStyleClass().add("delivery-stat-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(16));

        Rectangle colorBar = new Rectangle(40, 4);
        colorBar.setFill(Color.web(color));

        Label valueLabel = createSafeLabel(cleanPersianText(value), "stat-value");
        Label labelText = createSafeLabel(cleanPersianText(label), "stat-label");

        card.getChildren().addAll(colorBar, valueLabel, labelText);
        return card;
    }

    private VBox createStatusToggleSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(DELIVERY_STATUS_TEXT, "section-title");

        HBox toggleContainer = new HBox();
        toggleContainer.getStyleClass().add("status-toggle-container");
        toggleContainer.setSpacing(20);
        toggleContainer.setAlignment(Pos.CENTER);
        toggleContainer.setPadding(new Insets(20));

        // Online/Offline buttons
        Button onlineBtn = new Button("آنلاين");
        onlineBtn.getStyleClass().addAll("status-btn", isOnline ? "status-active" : "status-inactive");
        onlineBtn.setPrefWidth(120);
        onlineBtn.setPrefHeight(50);

        Button offlineBtn = new Button("آفلاين");
        offlineBtn.getStyleClass().addAll("status-btn", !isOnline ? "status-active" : "status-inactive");
        offlineBtn.setPrefWidth(120);
        offlineBtn.setPrefHeight(50);

        // Toggle functionality
        onlineBtn.setOnAction(e -> {
            isOnline = true;
            onlineBtn.getStyleClass().remove("status-inactive");
            onlineBtn.getStyleClass().add("status-active");
            offlineBtn.getStyleClass().remove("status-active");
            offlineBtn.getStyleClass().add("status-inactive");
        });

        offlineBtn.setOnAction(e -> {
            isOnline = false;
            offlineBtn.getStyleClass().remove("status-inactive");
            offlineBtn.getStyleClass().add("status-active");
            onlineBtn.getStyleClass().remove("status-active");
            onlineBtn.getStyleClass().add("status-inactive");
        });

        toggleContainer.getChildren().addAll(onlineBtn, offlineBtn);
        section.getChildren().addAll(header, toggleContainer);

        return section;
    }

    private VBox createActiveOrdersSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(ACTIVE_ORDERS_TEXT, "section-title");

        // Tab-like view for different order types
        HBox tabsContainer = new HBox();
        tabsContainer.setSpacing(16);

        List<Order> readyOrders = orderRepo.getInProgressOrdersForDeliveryGuy();
        List<Order> inDeliveryOrders = orderRepo.getInProgressOrders().stream()
                .filter(o -> o.getStatus() == Order.Status.RECEIVED_BY_DELIVERY)
                .limit(3)
                .collect(Collectors.toList());

        tabsContainer.getChildren().addAll(
                createOrderTypeCard("آماده تحويل", String.valueOf(readyOrders.size()), "#FF9800"),
                createOrderTypeCard("در حال تحويل", String.valueOf(inDeliveryOrders.size()), "#2196F3"),
                createOrderTypeCard("تحويل شده", "8", "#4CAF50")
        );

        // Orders list
        VBox ordersList = createOrdersList();

        section.getChildren().addAll(header, tabsContainer, ordersList);
        return section;
    }

    private VBox createOrderTypeCard(String type, String count, String color) {
        VBox card = new VBox();
        card.getStyleClass().add("order-type-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(16));
        card.setPrefWidth(140);

        Rectangle indicator = new Rectangle(50, 4);
        indicator.setFill(Color.web(color));

        Label countLabel = createSafeLabel(cleanPersianText(count), "order-count");
        Label typeLabel = createSafeLabel(cleanPersianText(type), "order-type");

        card.getChildren().addAll(indicator, countLabel, typeLabel);
        return card;
    }

    private VBox createOrdersList() {
        VBox container = new VBox();
        container.setSpacing(12);

        Label title = createSafeLabel("آخرين سفارشات", "subsection-title");
        container.getChildren().add(title);

        List<Order> recentOrders = orderRepo.getAllOrders().stream().limit(4).collect(Collectors.toList());

        for (Order order : recentOrders) {
            if (order != null) {
                container.getChildren().add(createDeliveryOrderItem(order));
            }
        }

        return container;
    }

    private HBox createDeliveryOrderItem(Order order) {
        HBox item = new HBox();
        item.getStyleClass().add("delivery-order-item");
        item.setSpacing(16);
        item.setPadding(new Insets(16));
        item.setAlignment(Pos.CENTER_LEFT);

        VBox orderInfo = new VBox();
        orderInfo.setSpacing(6);

        Label orderIdLabel = createSafeLabel("سفارش #" + cleanPersianText(order.getId()), "order-id-label");
        Label customerLabel = createSafeLabel("مشتري: " + cleanPersianText(order.getCustomerName()), "customer-label");
        Label addressLabel = createSafeLabel("آدرس: " + generateFakeAddress(), "address-label");

        orderInfo.getChildren().addAll(orderIdLabel, customerLabel, addressLabel);

        // Distance and time info
        VBox deliveryInfo = new VBox();
        deliveryInfo.setSpacing(4);
        deliveryInfo.setAlignment(Pos.CENTER_RIGHT);

        Label distanceLabel = createSafeLabel(random.nextInt(5) + 1 + " کيلومتر", "distance-label");
        Label timeLabel = createSafeLabel(random.nextInt(20) + 10 + " دقيقه", "time-label");

        deliveryInfo.getChildren().addAll(distanceLabel, timeLabel);

        // Action button
        Button actionBtn = new Button("تحويل");
        actionBtn.getStyleClass().add("delivery-action-btn");
        actionBtn.setPrefWidth(80);

        item.getChildren().addAll(orderInfo, deliveryInfo, actionBtn);
        HBox.setHgrow(orderInfo, Priority.ALWAYS);

        return item;
    }

    private String generateFakeAddress() {
        String[] streets = {"خيابان آزادي", "خيابان ولي عصر", "بلوار امام خميني", "خيابان مطهري", "خيابان فردوسي"};
        return streets[random.nextInt(streets.length)] + "، پلاک " + (random.nextInt(200) + 1);
    }

    private VBox createPerformanceSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(PERFORMANCE_TEXT, "section-title");

        // Performance chart
        VBox chartContainer = new VBox();
        chartContainer.getStyleClass().add("performance-chart-container");
        chartContainer.setPadding(new Insets(20));

        Label chartTitle = createSafeLabel("عملکرد هفتگي", "chart-title");

        // Simple line chart simulation
        HBox performanceChart = createPerformanceChart();

        chartContainer.getChildren().addAll(chartTitle, performanceChart);
        section.getChildren().addAll(header, chartContainer);

        return section;
    }

    private HBox createPerformanceChart() {
        HBox chartContainer = new HBox();
        chartContainer.setSpacing(15);
        chartContainer.setAlignment(Pos.BOTTOM_CENTER);
        chartContainer.setPadding(new Insets(20));

        String[] days = {"شنبه", "يک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه"};
        int[] deliveries = {8, 12, 6, 15, 10, 18, 14}; // Number of deliveries per day

        for (int i = 0; i < days.length; i++) {
            VBox dayContainer = new VBox();
            dayContainer.setAlignment(Pos.BOTTOM_CENTER);
            dayContainer.setSpacing(5);

            // Bar representing deliveries
            Rectangle bar = new Rectangle(25, deliveries[i] * 8); // Scale for display
            bar.setFill(Color.web("#4CAF50"));
            bar.getStyleClass().add("performance-bar");

            Label dayLabel = createSafeLabel(days[i], "chart-day-label");
            Label valueLabel = createSafeLabel(String.valueOf(deliveries[i]), "chart-value-label");

            dayContainer.getChildren().addAll(valueLabel, bar, dayLabel);
            chartContainer.getChildren().add(dayContainer);
        }

        return chartContainer;
    }

    private VBox createEarningsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(EARNINGS_TEXT, "section-title");

        GridPane earningsGrid = new GridPane();
        earningsGrid.setHgap(20);
        earningsGrid.setVgap(16);
        earningsGrid.setPadding(new Insets(16));

        // Earnings data
        String[][] earnings = {
                {"درآمد امروز", "340,000 تومان"},
                {"درآمد هفته", "2,100,000 تومان"},
                {"درآمد ماه", "8,500,000 تومان"},
                {"متوسط تحويل", "28,000 تومان"},
                {"انعام امروز", "85,000 تومان"},
                {"کل تحويلات", "156 سفارش"}
        };

        for (int i = 0; i < earnings.length; i++) {
            VBox earningCard = createEarningCard(earnings[i][0], earnings[i][1]);
            earningsGrid.add(earningCard, i % 3, i / 3);
        }

        section.getChildren().addAll(header, earningsGrid);
        return section;
    }

    private VBox createEarningCard(String label, String value) {
        VBox card = new VBox();
        card.getStyleClass().add("earning-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(20));

        Label valueLabel = createSafeLabel(cleanPersianText(value), "earning-value");
        Label labelText = createSafeLabel(cleanPersianText(label), "earning-label");

        card.getChildren().addAll(valueLabel, labelText);
        return card;
    }

    private VBox createRatingsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(RATINGS_TEXT, "section-title");

        // Rating summary
        HBox ratingSummary = new HBox();
        ratingSummary.setSpacing(20);
        ratingSummary.setAlignment(Pos.CENTER);

        // Overall rating
        VBox overallRating = new VBox();
        overallRating.getStyleClass().add("overall-rating");
        overallRating.setAlignment(Pos.CENTER);
        overallRating.setSpacing(8);
        overallRating.setPadding(new Insets(20));

        Label ratingValue = createSafeLabel("4.8", "rating-value");
        Label ratingLabel = createSafeLabel("امتياز کلي", "rating-label");
        Label totalReviews = createSafeLabel("از 127 نظر", "total-reviews");

        overallRating.getChildren().addAll(ratingValue, ratingLabel, totalReviews);

        // Rating breakdown
        VBox ratingBreakdown = createRatingBreakdown();

        ratingSummary.getChildren().addAll(overallRating, ratingBreakdown);

        // Recent reviews
        VBox recentReviews = createRecentReviews();

        section.getChildren().addAll(header, ratingSummary, recentReviews);
        return section;
    }

    private VBox createRatingBreakdown() {
        VBox breakdown = new VBox();
        breakdown.setSpacing(8);
        breakdown.setPadding(new Insets(20));

        String[] ratings = {"5 ستاره", "4 ستاره", "3 ستاره", "2 ستاره", "1 ستاره"};
        int[] percentages = {60, 25, 10, 3, 2};
        String[] colors = {"#4CAF50", "#8BC34A", "#FFC107", "#FF9800", "#F44336"};

        for (int i = 0; i < ratings.length; i++) {
            HBox ratingRow = new HBox();
            ratingRow.setSpacing(12);
            ratingRow.setAlignment(Pos.CENTER_LEFT);

            Label ratingLabel = createSafeLabel(ratings[i], "rating-breakdown-label");
            ratingLabel.setPrefWidth(60);

            Rectangle bar = new Rectangle(percentages[i] * 2, 8); // Scale for display
            bar.setFill(Color.web(colors[i]));

            Label percentageLabel = createSafeLabel(percentages[i] + "%", "percentage-label");

            ratingRow.getChildren().addAll(ratingLabel, bar, percentageLabel);
            breakdown.getChildren().add(ratingRow);
        }

        return breakdown;
    }

    private VBox createRecentReviews() {
        VBox container = new VBox();
        container.setSpacing(12);

        Label title = createSafeLabel("آخرين نظرات", "subsection-title");
        container.getChildren().add(title);

        String[] reviewTexts = {
                "پيک بسيار سريع و مودب بود",
                "تحويل به موقع و غذا گرم رسيد",
                "خيلي حرفه اي عمل کرد"
        };

        String[] reviewers = {"احمد محمدي", "فاطمه زارعي", "علي رضايي"};

        for (int i = 0; i < reviewTexts.length; i++) {
            container.getChildren().add(createReviewItem(reviewers[i], reviewTexts[i], 5));
        }

        return container;
    }

    private HBox createReviewItem(String reviewer, String reviewText, int stars) {
        HBox item = new HBox();
        item.getStyleClass().add("review-item");
        item.setSpacing(12);
        item.setPadding(new Insets(12));

        VBox reviewInfo = new VBox();
        reviewInfo.setSpacing(4);

        Label reviewerLabel = createSafeLabel(cleanPersianText(reviewer), "reviewer-name");
        Label reviewLabel = createSafeLabel(cleanPersianText(reviewText), "review-text");

        reviewInfo.getChildren().addAll(reviewerLabel, reviewLabel);

        Label starsLabel = createSafeLabel("★".repeat(stars), "stars-label");
        starsLabel.setStyle("-fx-text-fill: #FFD700;");

        item.getChildren().addAll(reviewInfo, starsLabel);
        HBox.setHgrow(reviewInfo, Priority.ALWAYS);

        return item;
    }

    private VBox createDeliveryAreasSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(AREAS_TEXT, "section-title");

        // Areas grid
        GridPane areasGrid = new GridPane();
        areasGrid.setHgap(16);
        areasGrid.setVgap(12);
        areasGrid.setPadding(new Insets(16));

        String[] areas = {
                "منطقه 1 - تهران", "منطقه 3 - تهران", "منطقه 5 - تهران",
                "منطقه 7 - تهران", "منطقه 2 - تهران", "منطقه 4 - تهران"
        };

        for (int i = 0; i < areas.length; i++) {
            VBox areaCard = createAreaCard(areas[i], random.nextInt(20) + 5);
            areasGrid.add(areaCard, i % 3, i / 3);
        }

        section.getChildren().addAll(header, areasGrid);
        return section;
    }

    private VBox createAreaCard(String areaName, int deliveryCount) {
        VBox card = new VBox();
        card.getStyleClass().add("area-card");
        card.setAlignment(Pos.CENTER);
        card.setSpacing(8);
        card.setPadding(new Insets(16));

        Label nameLabel = createSafeLabel(cleanPersianText(areaName), "area-name");
        Label countLabel = createSafeLabel(deliveryCount + " تحويل", "area-count");

        card.getChildren().addAll(nameLabel, countLabel);
        return card;
    }

    private VBox createQuickActionsSection() {
        VBox section = new VBox();
        section.setSpacing(16);

        Label header = createSafeLabel(ACTIONS_TEXT, "section-title");

        HBox actionsRow = new HBox();
        actionsRow.setSpacing(16);

        String[] actions = {
                "مسيريابي", "تماس با پشتيباني", "گزارش مشکل",
                "تاريخچه تحويلات", "تنظيمات پروفايل", "راهنماي کاربر"
        };

        for (String action : actions) {
            Button actionBtn = new Button(cleanPersianText(action));
            actionBtn.getStyleClass().add("delivery-action-button");
            actionBtn.setPrefWidth(150);
            actionBtn.setPrefHeight(70);
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
