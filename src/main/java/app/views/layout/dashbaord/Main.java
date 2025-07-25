package app.views.layout.dashbaord;

import app.models.*;
import app.states.StateManager;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import app.views.pages.admin.*;
import app.views.pages.buyer.*;
import app.views.pages.delivery.DeliveryCompleteOrderListPage;
import app.views.pages.delivery.DeliveryDashboardPage;
import app.views.pages.delivery.DeliveryProgressOrderListPage;
import app.views.pages.seller.*;
import app.views.pages.shared.NotificationPage;
import app.views.pages.shared.ProfilePage;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.InputStream;

public class Main {
    private VBox mainContent;
    private ScrollPane scrollContainer;
    private User currentUser;

    private static Main instance;

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }



    public VBox getView(User user) {
        this.currentUser = user;
        mainContent = new VBox();
        mainContent.getStyleClass().add("main-content");

        
        HBox topBar = createTopBar(user);
        mainContent.getChildren().add(topBar);

        
        scrollContainer = new ScrollPane();
        scrollContainer.setFitToWidth(true);
        scrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        
        scrollContainer.setContent(createDashboardContent(user));

        VBox.setVgrow(scrollContainer, Priority.ALWAYS);
        mainContent.getChildren().add(scrollContainer);

        
        StateManager.getInstance().navigationState.currentViewProperty().addListener(
                (obs, oldView, newView) -> updateContent(newView)
        );

        return mainContent;
    }

    
    public void setupWidthBinding(HBox parentLayout) {
        
        mainContent.prefWidthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parentLayout.getWidth() * 0.8,
                        parentLayout.widthProperty()
                )
        );
    }


    private void updateContent(String viewKey) {
        switch (viewKey) {


            case "profile":
                scrollContainer.setContent(new ProfilePage(currentUser));
                break;

            case "notification":
                scrollContainer.setContent(new NotificationPage(currentUser));
                break;



            case "buyer_dashboard":
                scrollContainer.setContent(new BuyerDashboardPage(currentUser));
                break;


            case "buyer_restaurant_list":
                scrollContainer.setContent(new RestaurantListPage());
                break;

            case "buyer_cart":
                scrollContainer.setContent(new CartPage());
                break;

            case "buyer_in_progress_order_list":
                scrollContainer.setContent(new BuyerInProgressOrderListPage());
                break;


            case "buyer_complete_order_list":
                scrollContainer.setContent(new BuyerCompleteOrderListPage());
                break;




            case "seller_dashboard":
                scrollContainer.setContent(new SellerDashboardPage(currentUser));
                break;

            case "seller_restaurant_setting":
                scrollContainer.setContent(new RestaurantSettingPage());
                break;
            case "seller_food_list":
                scrollContainer.setContent(new FoodListPage());
                break;
            case "seller_pending_order_list":
                scrollContainer.setContent(new PendingOrderListPage());
                break;
            case "seller_in_progress_order_list":
                scrollContainer.setContent(new SellerInProgressOrderListPage());
                break;

            case "seller_complete_order_list":
                scrollContainer.setContent(new SellerCompleteOrderListPage());
                break;


            case "admin_dashboard":
                scrollContainer.setContent(new SystemHealthPage());
                break;
            case "admin_user_list":
                scrollContainer.setContent(new AdminUserListPage());
                break;
            case "admin_order_list":
                scrollContainer.setContent(new AdminInProgressOrderListPage());
                break;
            case "admin_sales_report":
                scrollContainer.setContent(new SalesReportPage());
                break;
            case "admin_tickets":
                scrollContainer.setContent(new TicketListPage());
                break;



            case "delivery_dashboard":
                scrollContainer.setContent(new DeliveryDashboardPage(currentUser));
                break;

            case "delivery_in_progress_order_list":
                scrollContainer.setContent(new DeliveryProgressOrderListPage());
                break;
            case "delivery_complete_order_list":
                scrollContainer.setContent(new DeliveryCompleteOrderListPage());
                break;
            default:
                scrollContainer.setContent(createDashboardContent(currentUser));
        }
    }

    public void setContent(VBox content){
        scrollContainer.setContent(content);
    }

    
    private VBox createDashboardContent(User user) {

        return switch (user.getUserType()){
            case ADMIN ->
                new SystemHealthPage();
            case BUYER -> new BuyerDashboardPage(user);
            case  SELLER -> new SellerDashboardPage(user);
            case DELIVERY ->  new DeliveryDashboardPage(user);
            default -> new SystemHealthPage();
        };
    }

    
    private VBox createBuyerOrdersView() {
        VBox content = new VBox(10);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("سفارشات شما", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        
        Label ordersLabel = new Label("لیست سفارشات اخیر شما...");
        content.getChildren().addAll(title, ordersLabel);
        return content;
    }

    private VBox createSellerProductsView() {
        VBox content = new VBox(10);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("محصولات شما", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        
        Label productsLabel = new Label("مدیریت محصولات فروشگاه...");
        content.getChildren().addAll(title, productsLabel);
        return content;
    }

    private VBox createAdminUsersView() {
        VBox content = new VBox(10);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("مدیریت کاربران", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        
        Label usersLabel = new Label("لیست کاربران سیستم...");
        content.getChildren().addAll(title, usersLabel);
        return content;
    }

    public VBox createDeliveryActiveView() {
        VBox content = new VBox(10);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("سفارشات فعال", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        
        Label ordersLabel = new Label("سفارشات در حال تحویل...");
        content.getChildren().addAll(title, ordersLabel);
        return content;
    }

    private HBox createTopBar(User user) {
        HBox topBar = new HBox();
        topBar.getStyleClass().add("dashboard-top-bar");
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(16, 24, 16, 24));

        // Left Section - Breadcrumb/Page Title
        VBox leftSection = createLeftSection();

        // Center Section - Search Bar
        HBox centerSection = createSearchSection();

        // Right Section - User Actions
        HBox rightSection = createRightSection(user);

        // Spacers for proper alignment
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(
                leftSection,
                leftSpacer,
                centerSection,
                rightSpacer,
                rightSection
        );

        return topBar;
    }

    private VBox createLeftSection() {
        VBox leftSection = new VBox();
        leftSection.getStyleClass().add("topbar-left-section");
        leftSection.setSpacing(4);

        // Page title - dynamically updated based on current view
        Label pageTitle = new Label("داشبورد");
        pageTitle.getStyleClass().add("topbar-page-title");

        // Breadcrumb
        Label breadcrumb = new Label("خانه / داشبورد");
        breadcrumb.getStyleClass().add("topbar-breadcrumb");

        // Listen to navigation changes to update title and breadcrumb
        StateManager.getInstance().navigationState.currentViewProperty().addListener(
                (obs, oldView, newView) -> updatePageInfo(pageTitle, breadcrumb, newView)
        );

        leftSection.getChildren().addAll(pageTitle, breadcrumb);
        return leftSection;
    }

    private HBox createSearchSection() {
        HBox searchSection = new HBox();
        searchSection.getStyleClass().add("topbar-search-section");
        searchSection.setAlignment(Pos.CENTER);
        searchSection.setMaxWidth(400);
        searchSection.setPrefWidth(350);

        // Search container
        HBox searchContainer = new HBox();
        searchContainer.getStyleClass().add("search-container");
        searchContainer.setAlignment(Pos.CENTER_LEFT);

        // Search icon
        Label searchIcon = new Label("🔍");
        searchIcon.getStyleClass().add("search-icon");

        // Search field
        TextField searchField = new TextField();
        searchField.getStyleClass().add("search-field");
        searchField.setPromptText("جستجو در سیستم...");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchContainer.getChildren().addAll(searchIcon, searchField);
        searchSection.getChildren().add(searchContainer);

        return searchSection;
    }

    private HBox createRightSection(User user) {
        HBox rightSection = new HBox();
        rightSection.getStyleClass().add("topbar-right-section");
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.setSpacing(16);

        // Current time/date
        Label currentTime = createTimeLabel();

        // Online status indicator
        HBox onlineStatus = createOnlineStatusIndicator(user);

        // Notification bell
        Button notificationBtn = createNotificationButton();

        // Settings button
        Button settingsBtn = createSettingsButton();

        // User profile section
        HBox userProfile = createUserProfileSection(user);

        // Logout button
        Button logoutBtn = createLogoutButton();

        rightSection.getChildren().addAll(
                currentTime,
                onlineStatus,
                notificationBtn,
                settingsBtn,
                userProfile,
                logoutBtn
        );

        return rightSection;
    }

    private Label createTimeLabel() {
        Label timeLabel = new Label();
        timeLabel.getStyleClass().add("topbar-time");

        // Update time every minute
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), e -> {
            timeLabel.setText(java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Set initial time
        timeLabel.setText(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));

        return timeLabel;
    }

    private HBox createOnlineStatusIndicator(User user) {
        HBox statusBox = new HBox();
        statusBox.getStyleClass().add("status-indicator");
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setSpacing(6);

        Circle statusDot = new Circle(4);
        statusDot.getStyleClass().add("status-dot");

        Label statusText = new Label();
        statusText.getStyleClass().add("status-text");

        // Update status based on user type and availability
        if (user.getUserType() == UserType.DELIVERY) {
            boolean isOnline = user.getAvailability() != null ? user.getAvailability() : false;
            statusDot.getStyleClass().add(isOnline ? "status-online" : "status-offline");
            statusText.setText(isOnline ? "آنلاین" : "آفلاین");
        } else {
            statusDot.getStyleClass().add("status-online");
            statusText.setText("فعال");
        }

        statusBox.getChildren().addAll(statusDot, statusText);
        return statusBox;
    }

    private Button createNotificationButton() {
        Button notificationBtn = new Button();
        notificationBtn.getStyleClass().add("notification-btn");

        // Create notification icon with badge
        StackPane notificationStack = new StackPane();

        Label bellIcon = new Label("🔔");
        bellIcon.getStyleClass().add("notification-bell");

        // Notification badge
        Label badge = new Label("3");
        badge.getStyleClass().add("notification-badge");
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        StackPane.setMargin(badge, new Insets(-5, -5, 0, 0));

        notificationStack.getChildren().addAll(bellIcon, badge);
        notificationBtn.setGraphic(notificationStack);

        // Click handler
        notificationBtn.setOnAction(e -> {
            StateManager.getInstance().navigationState.setCurrentView("notification");
        });

        return notificationBtn;
    }

    private Button createSettingsButton() {
        Button settingsBtn = new Button("⚙️");
        settingsBtn.getStyleClass().add("settings-btn");
        settingsBtn.setOnAction(e -> {
            // Handle settings click - could open a popup or navigate to settings
            System.out.println("Settings clicked");
        });
        return settingsBtn;
    }

    private HBox createUserProfileSection(User user) {
        HBox profileSection = new HBox();
        profileSection.getStyleClass().add("user-profile-section");
        profileSection.setAlignment(Pos.CENTER);
        profileSection.setSpacing(12);


        ImageView avatarI = new ImageView(loadDefaultImage(user));
        avatarI.setFitWidth(36);
        avatarI.setFitHeight(36);
        avatarI.setPreserveRatio(false);
        avatarI.setClip(new Circle(18, 18, 18));
        // User avatar
        Circle avatar = new Circle(18);
        avatar.getStyleClass().add("user-avatar");

        // Try to load user profile image or use default
        try {
            System.out.println(user.getProfile() +" USER ");
            if (user.getProfile() != null && !user.getProfile().isEmpty()) {
                ImageView avatarImage = new ImageView(new Image(user.getProfile()));
                avatarImage.setFitWidth(36);
                avatarImage.setFitHeight(36);
                avatarImage.setPreserveRatio(false);
                avatarImage.setClip(new Circle(18, 18, 18));
            }
        } catch (Exception e) {
            // Use default avatar color based on user type
            String avatarColor = switch (user.getUserType()) {
                case ADMIN -> "#e74c3c";
                case SELLER -> "#f39c12";
                case BUYER -> "#3498db";
                case DELIVERY -> "#27ae60";
                default -> "#95a5a6";
            };
            avatar.setStyle("-fx-fill: " + avatarColor + ";");
        }

        // User info
        VBox userInfo = new VBox();
        userInfo.getStyleClass().add("user-info");
        userInfo.setSpacing(2);

        Label userName = new Label(cleanPersianText(user.getFullName()));
        userName.getStyleClass().add("topbar-user-name");
        userName.textProperty().bind(user.fullNameProperty());

        Label userRole = new Label(cleanPersianText(user.roleToPersian()));
        userRole.getStyleClass().add("topbar-user-role");

        userInfo.getChildren().addAll(userName, userRole);

        // Make profile section clickable
        profileSection.setOnMouseClicked(e -> {

            StateManager.getInstance().navigationState.setCurrentView("profile");
            this.setContent(new ProfilePage(user));
        });
        profileSection.getStyleClass().add("clickable");

        profileSection.getChildren().addAll(avatarI, userInfo);
        return profileSection;
    }

    private Image loadDefaultImage(User user) {
        try {
            InputStream is = getClass().getResourceAsStream(user.getProfile());
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception e) {
            System.err.println("Error loading default image: " + e.getMessage());
        }


        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
    }

    private Button createLogoutButton() {
        Button logoutBtn = new Button(" خروج ⊗");
        logoutBtn.getStyleClass().add("logout-btn");
        logoutBtn.setOnAction(e -> {
            StateManager.getInstance().userState.logout();
            StateManager.getInstance().getNavigationController().logout();
        });

        // Add tooltip
        Tooltip.install(logoutBtn, new Tooltip("خروج از حساب کاربری"));

        return logoutBtn;
    }

    private void updatePageInfo(Label pageTitle, Label breadcrumb, String viewKey) {
        String title = "داشبورد";
        String breadcrumbText = "خانه";

        switch (viewKey) {
            case "buyer_dashboard" -> {
                title = "داشبورد خریدار";
                breadcrumbText = "خانه / داشبورد";
            }
            case "buyer_restaurant_list" -> {
                title = "لیست رستوران‌ها";
                breadcrumbText = "خانه / رستوران‌ها";
            }
            case "buyer_cart" -> {
                title = "سبد خرید";
                breadcrumbText = "خانه / سبد خرید";
            }
            case "seller_dashboard" -> {
                title = "داشبورد فروشنده";
                breadcrumbText = "خانه / داشبورد";
            }
            case "seller_food_list" -> {
                title = "مدیریت غذاها";
                breadcrumbText = "خانه / غذاها";
            }
            case "delivery_dashboard" -> {
                title = "داشبورد پیک";
                breadcrumbText = "خانه / داشبورد";
            }
            case "admin_dashboard" -> {
                title = "داشبورد ادمین";
                breadcrumbText = "خانه / داشبورد";
            }
            case "profile" -> {
                title = "پروفایل کاربری";
                breadcrumbText = "خانه / پروفایل";
            }
            case "notification" -> {
                title = "اعلانات";
                breadcrumbText = "خانه / اعلانات";
            }
        }

        pageTitle.setText(cleanPersianText(title));
        breadcrumb.setText(cleanPersianText(breadcrumbText));
    }

    // Helper method for cleaning Persian text (add this to Main class)
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
}
