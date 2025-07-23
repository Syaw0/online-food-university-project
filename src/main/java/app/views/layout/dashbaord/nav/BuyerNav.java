package app.views.layout.dashbaord.nav;

import app.models.User;
import javafx.scene.layout.VBox;

public class BuyerNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("داشبورد", "buyer-dashboard"),
                createNavButton("پروفایل کاربری", "profile")
        );
        return navItems;
    }
}
