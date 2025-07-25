package app.views.layout.dashbaord.nav;

import app.models.User;
import javafx.scene.layout.VBox;

public class BuyerNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("داشبورد", "buyer-dashboard"),
                createNavButton("پروفایل کاربری", "profile"),

                createNavButton("لیست رستوران ها", "buyer_restaurant_list"),
                createNavButton("سبد خرید", "buyer_cart"),
                createNavButton("لیست سفارشات در حال انجام", "buyer_in_progress_order_list"),
                createNavButton("لیست سفارشات تکمیل شده", "buyer_complete_order_list"),
                createNavButton("کیف پول", "buyer_wallet"),
                createNavButton("اعلان ها", "notification")


        );
        return navItems;
    }
}
