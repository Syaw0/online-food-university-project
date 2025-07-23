package app.states;

import app.models.Food;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FoodState {
    private final ObjectProperty<Food> currentFood = new SimpleObjectProperty<>();

    public ObjectProperty<Food> currentFoodProperty() {
        return currentFood;
    }

    public Food getCurrentFood() {
        return currentFood.get();
    }

    public void setCurrentFood(Food food) {
        currentFood.set(food);
    }
}
