package app.views.layout.dashbaord.nav;


import app.models.User;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SellerNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("Dashboard", "buyer-dashboard"),
                createNavButton("Products", "buyer-products"),
                createNavButton("Orders", "buyer-orders"),
                createNavButton("Cart", "buyer-cart"),
                createNavButton("Favorites", "buyer-favorites"),
                createNavButton("Profile", "profile")
        );
        return navItems;
    }
}