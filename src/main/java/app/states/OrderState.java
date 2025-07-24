package app.states;

import app.models.Order;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class OrderState {
    private final ObjectProperty<Order> currentOrder = new SimpleObjectProperty<>();

    public ObjectProperty<Order> currentOrderProperty() {
        return currentOrder;
    }

    public Order getCurrentOrder() {
        return currentOrder.get();
    }

    public void setCurrentOrder(Order order) {
        currentOrder.set(order);
    }
}
