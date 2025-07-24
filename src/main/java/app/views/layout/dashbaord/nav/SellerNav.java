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
                createNavButton("لیست سفارشات معلق", "seller_pending_order_list"),
                createNavButton("لیست سفارشات در حال انجام", "seller_in_progress_order_list"),
                createNavButton("لیست سفاراشت تکمیل شده", "seller_complete_order_list")
        );
        return navItems;
    }
}