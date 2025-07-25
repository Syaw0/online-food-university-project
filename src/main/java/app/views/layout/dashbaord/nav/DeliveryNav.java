package app.views.layout.dashbaord.nav;

import app.models.User;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DeliveryNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("لیست سفارشات در حال جریان", "delivery_in_progress_order_list"),
                createNavButton("لیست سفارشات تکمیل شده", "delivery_complete_order_list"),
                createNavButton("پروفایل کاربری", "profile"),
                createNavButton("اعلان ها", "notification")

        );
        return navItems;
    }
}