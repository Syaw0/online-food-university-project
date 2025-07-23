package app.views.layout.dashbaord;


import app.models.UserType;

import java.util.Arrays;
import java.util.List;

public class NavItemProvider {
    public static List<NavItem> getNavItems(UserType userType) {
        switch (userType) {
            case BUYER:
                return Arrays.asList(
                        new NavItem("داشبورد", "buyer_dashboard"),
                        new NavItem("سفارشات", "buyer_orders"),
                        new NavItem("پروفایل", "buyer_profile"),
                        new NavItem("تنظیمات", "buyer_settings")
                );
            case SELLER:
                return Arrays.asList(
                        new NavItem("داشبورد", "seller_dashboard"),
                        new NavItem("محصولات", "seller_products"),
                        new NavItem("سفارشات", "seller_orders"),
                        new NavItem("گزارشات", "seller_reports"),
                        new NavItem("تنظیمات", "seller_settings")
                );
            case ADMIN:
                return Arrays.asList(
                        new NavItem("داشبورد", "admin_dashboard"),
                        new NavItem("کاربران", "admin_users"),
                        new NavItem("گزارشات", "admin_reports"),
                        new NavItem("تنظیمات سیستم", "admin_system_settings")
                );
            case DELIVERY:
                return Arrays.asList(
                        new NavItem("داشبورد", "delivery_dashboard"),
                        new NavItem("سفارشات فعال", "delivery_active_orders"),
                        new NavItem("تاریخچه", "delivery_history"),
                        new NavItem("تنظیمات", "delivery_settings")
                );
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }
}