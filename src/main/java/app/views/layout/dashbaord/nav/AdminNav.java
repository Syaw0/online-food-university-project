package app.views.layout.dashbaord.nav;


import app.models.User;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminNav extends BaseNav {
    @Override
    protected VBox getUserSpecificNavItems(User user) {
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createNavButton("داشبورد", "admin_dashboard"),
                createNavButton("لیست کاربران", "admin_user_list"),
                createNavButton("لیست سفارشات در حال انجام", "admin_order_list"),
                createNavButton("گزارش وضعیت فروش", "admin_sales_report"),
                createNavButton("تیکت ها", "admin_tickets"),

                createNavButton("Profile", "profile")
        );
        return navItems;
    }
}