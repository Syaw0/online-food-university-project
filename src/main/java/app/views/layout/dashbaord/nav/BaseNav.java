package app.views.layout.dashbaord.nav;

import app.models.User;
import app.states.StateManager;
import app.views.component.Typography;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public abstract class BaseNav {
    protected Button createNavButton(String text, String viewKey) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(12, 24, 12, 24));

        btn.setOnMouseEntered(e -> btn.getStyleClass().add("nav-button-hover"));
        btn.setOnMouseExited(e -> btn.getStyleClass().remove("nav-button-hover"));
        btn.setOnAction(e -> StateManager.getInstance().navigationState.setCurrentView(viewKey));

        return btn;
    }

    public VBox getView(User user) {
        VBox navbar = new VBox(10);
        navbar.getStyleClass().add("dashboard-navbar");
        navbar.setPadding(new Insets(25, 15, 25, 15));

        // Logo Section
        Label logo = new Label("Aut App");
        logo.getStyleClass().add("nav-logo");
        logo.setPadding(new Insets(0, 0, 30, 0));

        // Navigation Items Container
        VBox navItems = new VBox(8);
        navItems.getStyleClass().add("nav-items-container");
        navItems.setPadding(new Insets(10, 0, 0, 0));

        // Add specific nav items
        VBox userSpecificNav = getUserSpecificNavItems(user);
        navItems.getChildren().addAll(userSpecificNav);

        // Spacer to push user info to bottom
        Region spacer = new Region();
        spacer.getStyleClass().add("nav-spacer");
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // User Info Section
        VBox userInfo = new VBox(5);
        userInfo.getStyleClass().add("user-info-panel");
        userInfo.setPadding(new Insets(20, 5, 5, 5));

        Label userName = new Typography(user.getFullName(), Typography.Variant.H3);
        userName.textProperty().bind(user.fullNameProperty());
        userName.getStyleClass().add("user-name");
        userName.setPadding(new Insets(0, 0, 3, 0));

        Label userRole = new Typography(user.roleToPersian(), Typography.Variant.LABEL);
        userRole.getStyleClass().add("user-role");


        userInfo.getChildren().addAll(userName, userRole);
        navbar.getChildren().addAll(logo, navItems, spacer, userInfo);

        return navbar;
    }

    protected abstract VBox getUserSpecificNavItems(User user);
}
