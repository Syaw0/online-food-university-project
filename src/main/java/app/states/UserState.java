package app.states;

import app.models.Restaurant;
import app.models.User;
import app.models.UserType;
import app.mock.RestaurantRepo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.BooleanProperty;

import java.util.List;

public class UserState {
    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private final BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
    private final ObjectProperty<UserType> role = new SimpleObjectProperty<>(UserType.GUEST);
    private final RestaurantRepo restaurantRepo = new RestaurantRepo(); 

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public BooleanProperty isLoggedInProperty() {
        return isLoggedIn;
    }

    public ObjectProperty<UserType> roleProperty() {
        return role;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public void setCurrentUser(User user) {
        currentUser.set(user);
        isLoggedIn.set(user != null);
        role.set(user != null ? user.getUserType() : UserType.GUEST);

        
        if (user != null && user.getUserType() == UserType.SELLER) {
            loadSellerRestaurant(user);
        }
    }

    private void loadSellerRestaurant(User seller) {
        
        List<Restaurant> sellerRestaurants = restaurantRepo.findByOwnerId(seller.getId());

        if (sellerRestaurants.isEmpty()) {
            
            Restaurant newRestaurant = new Restaurant(
                    null,
                    seller.getId(),
                    "رستوران جدید",
                    "توضیحات رستوران",
                    "",
                    "",
                    "9:00-23:00",
                    "",
                    "0"
            );
            restaurantRepo.addRestaurant(newRestaurant);
            StateManager.getInstance().restaurantState.setCurrentRestaurant(newRestaurant);
        } else {
            
            StateManager.getInstance().restaurantState.setCurrentRestaurant(sellerRestaurants.get(0));
        }
    }

    public void logout() {
        currentUser.set(null);
        isLoggedIn.set(false);
        role.set(UserType.GUEST);
        StateManager.getInstance().restaurantState.setCurrentRestaurant(null);
    }

    public void login(User user) {
        this.setCurrentUser(user);
    }

    public UserType getRole() {
        return role.get();
    }
}
