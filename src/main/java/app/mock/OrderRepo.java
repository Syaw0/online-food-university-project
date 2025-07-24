package app.mock;

import app.models.*;
import app.models.Order.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderRepo {
    private final List<Order> orders;
    private final Random random = new Random();
    private final String[] customerNames = {
            "علی محمدی", "فاطمه زارعی", "رضا نوروزی", "سارا احمدی",
            "محمد حسینی", "نازیلا کمالی", "امیررضا اکبری", "مریم رضایی",
            "حسن قربانی", "لیلا موسوی"
    };
    private final String[] deliveryPersons = {"پیک موتوری حسین خلیلی", "پیک موتوری عباس پورشفق", "پیک موتوری حسن علی قاسمی"}; // ADDED

    private final String[] customerPhones = {
            "09121234567", "09129876543", "09123456789", "09127778899",
            "09106543210", "09111223344", "09135556677", "09148889900",
            "09151112233", "09169998877"
    };

    private final FoodRepo foodRepo = new FoodRepo();

    public OrderRepo() {
        orders = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        // Get all foods to use in orders
        List<Food> allFoods = foodRepo.getAllFoods();

        // Create 10 fake orders
        for (int i = 1; i <= 10; i++) {
            String orderId = "ORD-" + i;
            String customerName = customerNames[i-1];
            String customerPhone = customerPhones[i-1];

            // Create 1-3 random food items per order
            List<Order.FoodItem> foodItems = new ArrayList<>();
            int itemCount = random.nextInt(3) + 1; // 1-3 items
            for (int j = 1; j <= itemCount; j++) {
                // Pick a random food
                Food randomFood = allFoods.get(random.nextInt(allFoods.size()));
                foodItems.add(new Order.FoodItem(
                        randomFood,
                        random.nextInt(3) + 1 // Quantity 1-3
                ));
            }

            // Vary statuses: 60% PENDING, 40% other statuses
            Status status = random.nextDouble() < 0.6 ? Status.PENDING :
                    Status.values()[random.nextInt(Status.values().length - 1) + 1];

            // AUTO-ASSIGN DELIVERY FOR SPECIFIC STATUSES
            String deliveryName = null;
            if (status == Status.RECEIVED_BY_DELIVERY ||
                    status == Status.DELIVERED_TO_CUSTOMER) {
                deliveryName = deliveryPersons[random.nextInt(deliveryPersons.length)];
            }

            orders.add(new Order(
                    orderId,
                    customerName,
                    customerPhone,
                    deliveryName, // Set delivery name
                    status,
                    foodItems
            ));
        }
    }

    public Order findById(String id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public List<Order> getPendingOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == Status.PENDING)
                .toList();
    }

    public List<Order> getInProgressOrders() {
        return orders.stream()
                .filter(order ->
                        order.getStatus() == Status.ACCEPTED_BY_SELLER ||
                                order.getStatus() == Status.PREPARING ||
                                order.getStatus() == Status.RECEIVED_BY_DELIVERY)
                .toList();
    }

    public void updateOrder(Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(updatedOrder.getId())) {
                orders.set(i, updatedOrder);
                return;
            }
        }
    }

    public List<Order> getDeliveredOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == Status.DELIVERED_TO_CUSTOMER)
                .toList();
    }
}
