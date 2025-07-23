package app.views.layout.dashbaord.nav;


import app.models.User;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SellerNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("داشبورد", "seller-dashboard"),
                createNavButton("تنظیمات رستوران", "seller_restaurant_setting"),
                createNavButton("لیست غذا ها", "seller_food_list"),
                createNavButton("Cart", "seller-cart"),
                createNavButton("Favorites", "seller-favorites"),
                createNavButton("Profile", "profile")
        );
        return navItems;
    }
}