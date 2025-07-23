package app.views.layout.dashbaord.nav;


import app.models.User;
import app.models.UserType;

public class NavFactory {
    public static BaseNav createNav(User user) {
        switch (user.getUserType()) {
            case BUYER:
                return new BuyerNav();
            case SELLER:
                return new SellerNav();
            case ADMIN:
                return new AdminNav();
            case DELIVERY:
                return new DeliveryNav();
            default:
                throw new IllegalArgumentException("Unknown user type: " + user.getUserType());
        }
    }
}