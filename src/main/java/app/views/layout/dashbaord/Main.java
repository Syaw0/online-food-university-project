package app.views.layout.dashbaord;

import app.models.*;
import app.states.StateManager;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import app.views.pages.buyer.BuyerCompleteOrderListPage;
import app.views.pages.buyer.BuyerInProgressOrderListPage;
import app.views.pages.buyer.CartPage;
import app.views.pages.buyer.RestaurantListPage;
import app.views.pages.seller.*;
import app.views.pages.shared.ProfilePage;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

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

        // Top bar (sticky)
        HBox topBar = createTopBar(user);
        mainContent.getChildren().add(topBar);

        // Scrollable content
        scrollContainer = new ScrollPane();
        scrollContainer.setFitToWidth(true);
        scrollContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Create initial dashboard content
        scrollContainer.setContent(createDashboardContent(user));

        VBox.setVgrow(scrollContainer, Priority.ALWAYS);
        mainContent.getChildren().add(scrollContainer);

        // Listen for navigation changes
        StateManager.getInstance().navigationState.currentViewProperty().addListener(
                (obs, oldView, newView) -> updateContent(newView)
        );

        return mainContent;
    }

    // Moved width binding to DashboardLayout where we have parent context
    public void setupWidthBinding(HBox parentLayout) {
        // Bind content width to 80% of parent HBox when parent is available
        mainContent.prefWidthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parentLayout.getWidth() * 0.8,
                        parentLayout.widthProperty()
                )
        );
    }


    private void updateContent(String viewKey) {
        switch (viewKey) {

//            ======= SHARED =======
            case "profile":
                scrollContainer.setContent(new ProfilePage(currentUser));
                break;

//            ======= BUYER =======

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


//            ======= SELLER =======
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

//            ======= ADMIN =======
            case "admin_users":
                scrollContainer.setContent(createAdminUsersView());
                break;

//            ======= DELIVERY =======
            case "delivery_dashboard":
                scrollContainer.setContent(createDashboardContent(currentUser));
                break;
            case "delivery_active":
                scrollContainer.setContent(createDeliveryActiveView());
                break;
            default:
                scrollContainer.setContent(createDashboardContent(currentUser));
        }
    }

    public void setContent(VBox content){
        scrollContainer.setContent(content);
    }

    // IMPLEMENTED: Create dashboard content
    private VBox createDashboardContent(User user) {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("داشبورد کاربری", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        VBox userInfoPanel = new VBox(10);
        userInfoPanel.getStyleClass().add("info-panel");
        userInfoPanel.setPadding(new Insets(15));

        userInfoPanel.getChildren().addAll(
                new Label("نام کامل: " + user.getFullName()),
                new Label("ایمیل: " + user.getEmail()),
                new Label("نوع حساب: " + user.getUserType().toString())
        );

        VBox roleContent = new VBox(10);
        roleContent.getStyleClass().add("role-content");

        switch (user.getUserType()) {
            case BUYER:
                roleContent.getChildren().add(new Typography("سفارشات اخیر", Typography.Variant.H3));
                break;
            case SELLER:
                roleContent.getChildren().add(new Typography("آمار فروش امروز", Typography.Variant.H3));
                break;
            case ADMIN:
                roleContent.getChildren().add(new Typography("مدیریت سیستم", Typography.Variant.H3));
                break;
            case DELIVERY:
                roleContent.getChildren().add(new Typography("سفارشات فعال", Typography.Variant.H3));
                break;
        }

        content.getChildren().addAll(title, userInfoPanel, roleContent);
        return content;
    }

    // View creation methods for different sections
    private VBox createBuyerOrdersView() {
        VBox content = new VBox(10);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(20));

        Label title = new Typography("سفارشات شما", Typography.Variant.H2);
        title.setAlignment(Pos.CENTER_RIGHT);

        // Add buyer orders content here
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

        // Add seller products content here
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

        // Add admin users management content here
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

        // Add delivery orders content here
        Label ordersLabel = new Label("سفارشات در حال تحویل...");
        content.getChildren().addAll(title, ordersLabel);
        return content;
    }

    private HBox createTopBar(User user) {
        HBox topBar = new HBox(15);
        topBar.getStyleClass().add("dashboard-top-bar");
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);

        Button logoutBtn = new ButtonComponent("خروج از حساب کاربری", ButtonComponent.Variation.TEXT);
//        logoutBtn.getStyleClass().add("logout-button");
        logoutBtn.setOnAction(e -> {
            StateManager.getInstance().userState.logout();
            StateManager.getInstance().getNavigationController().logout();
        });

        Label userGreeting = new Typography("سلام، " + user.getFullName() + "!", Typography.Variant.LABEL);
        userGreeting.textProperty().bind(user.fullNameProperty());
//        userGreeting.getStyleClass().add("user-greeting");

        Label userType = new Label(user.getUserType().toString());
        userType.getStyleClass().add("user-type-badge");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(
                userGreeting,
                userType,
                spacer,
                logoutBtn
        );

        return topBar;
    }
}
