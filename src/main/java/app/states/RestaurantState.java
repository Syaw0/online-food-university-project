package app.states;

import app.models.Restaurant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RestaurantState {
    private final ObjectProperty<Restaurant> currentRestaurant = new SimpleObjectProperty<>();

    public ObjectProperty<Restaurant> currentRestaurantProperty() {
        return currentRestaurant;
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant.get();
    }

    public void setCurrentRestaurant(Restaurant restaurant) {
        currentRestaurant.set(restaurant);
    }
}
