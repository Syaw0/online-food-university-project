package app.models;

public class DeliveryPerson {
    private final String name;
    private final String phone;
    private boolean available;
    private int currentDeliveries;

    public DeliveryPerson(String name, String phone, boolean available, int currentDeliveries) {
        this.name = name;
        this.phone = phone;
        this.available = available;
        this.currentDeliveries = currentDeliveries;
    }

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public boolean isAvailable() { return available; }
    public int getCurrentDeliveries() { return currentDeliveries; }

    @Override
    public String toString() {
        return name + " - " + (available ? "موجود" : "مشغول");
    }
}
