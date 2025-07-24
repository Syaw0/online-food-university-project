package app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    public enum Status {
        PENDING,
        ACCEPTED_BY_SELLER,
        PREPARING,
        RECEIVED_BY_DELIVERY,
        DELIVERED_TO_CUSTOMER,
        REJECTED_BY_SELLER
    }

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty customerPhone = new SimpleStringProperty();
    private final StringProperty deliveryName = new SimpleStringProperty();
    private Status status;
    private List<FoodItem> foodItems; 
    private final StringProperty creationDate = new SimpleStringProperty();


    public Order(String id, String customerName, String customerPhone,
                 String deliveryName, Status status, List<FoodItem> foodItems) {

        if (id == null || id.trim().isEmpty()) {
            this.id.set(UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }

        this.customerName.set(customerName);
        this.customerPhone.set(customerPhone);
        this.deliveryName.set(deliveryName);
        this.status = status;
        this.foodItems = foodItems;
        this.creationDate.set(LocalDate.now().toString());
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty customerNameProperty() { return customerName; }
    public StringProperty customerPhoneProperty() { return customerPhone; }
    public StringProperty deliveryNameProperty() { return deliveryName; }
    public StringProperty creationDateProperty() { return creationDate; }

    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }

    public String getCustomerName() { return customerName.get(); }
    public void setCustomerName(String name) { this.customerName.set(name); }

    public String getCustomerPhone() { return customerPhone.get(); }
    public void setCustomerPhone(String phone) { this.customerPhone.set(phone); }

    public String getDeliveryName() { return deliveryName.get(); }
    public void setDeliveryName(String name) { this.deliveryName.set(name); }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<FoodItem> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodItem> foodItems) { this.foodItems = foodItems; }

    public String getCreationDate() { return creationDate.get(); }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", customerName=" + getCustomerName() +
                ", customerPhone=" + getCustomerPhone() +
                ", deliveryName=" + getDeliveryName() +
                ", status=" + status +
                ", foodItems=" + foodItems +
                ", creationDate=" + getCreationDate() +
                '}';
    }


    public String statusToPersian(){
        return switch (this.getStatus()) {
            case PENDING -> "در انتظار تایید رستوران";
            case PREPARING -> "آماده سازی سفارش";
            case ACCEPTED_BY_SELLER -> "تایید سفارش توسط رستوران";
            case REJECTED_BY_SELLER -> "رد شده توسط رستوران";
            case RECEIVED_BY_DELIVERY -> "توسط پیک دریافت شد";
            case DELIVERED_TO_CUSTOMER -> "تحویل به مشتری";
        };
    }

    
    public static class FoodItem {
        private Food food;
        private int quantity;

        public FoodItem(Food food, int quantity) {
            this.food = food;
            this.quantity = quantity;
        }

        public Food getFood() { return food; }
        public int getQuantity() { return quantity; }

        @Override
        public String toString() {
            return quantity + "x " + food.getName();
        }
    }
}
