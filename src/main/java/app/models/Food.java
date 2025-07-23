package app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.UUID;
import java.time.LocalDate;

public class Food {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty restaurantId = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty price = new SimpleStringProperty();
    private final StringProperty stock = new SimpleStringProperty();
    private final StringProperty categoryId = new SimpleStringProperty();
    private final StringProperty image = new SimpleStringProperty();
    private final StringProperty totalRate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();

    public Food(String id, String restaurantId, String name, String description,
                String price, String stock, String categoryId,
                String image, String totalRate) {
        
        if (id == null || id.trim().isEmpty()) {
            this.id.set(UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }

        this.restaurantId.set(restaurantId);
        this.name.set(name);
        this.description.set(description);
        this.price.set(price);
        this.stock.set(stock);
        this.categoryId.set(categoryId);
        this.image.set(image);
        this.totalRate.set(totalRate);
        this.creationDate.set(LocalDate.now().toString());
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty restaurantIdProperty() { return restaurantId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty priceProperty() { return price; }
    public StringProperty stockProperty() { return stock; }
    public StringProperty categoryIdProperty() { return categoryId; }
    public StringProperty imageProperty() { return image; }
    public StringProperty totalRateProperty() { return totalRate; }
    public StringProperty creationDateProperty() { return creationDate; }

    
    public String getId() { return id.get(); }
    public String getRestaurantId() { return restaurantId.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public String getPrice() { return price.get(); }
    public String getStock() { return stock.get(); }
    public String getCategoryId() { return categoryId.get(); }
    public String getImage() { return image.get(); }
    public String getTotalRate() { return totalRate.get(); }
    public String getCreationDate() { return creationDate.get(); }

    
    public void setId(String id) { this.id.set(id); }
    public void setRestaurantId(String restaurantId) { this.restaurantId.set(restaurantId); }
    public void setName(String name) { this.name.set(name); }
    public void setDescription(String description) { this.description.set(description); }
    public void setPrice(String price) { this.price.set(price); }
    public void setStock(String stock) { this.stock.set(stock); }
    public void setCategoryId(String categoryId) { this.categoryId.set(categoryId); }
    public void setImage(String image) { this.image.set(image); }
    public void setTotalRate(String totalRate) { this.totalRate.set(totalRate); }

    @Override
    public String toString() {
        return "\nid: " + getId() +
                "\nrestaurantId:" + getRestaurantId() +
                "\nname: " + getName() +
                "\ndescription: " + getDescription() +
                "\nprice: " + getPrice() +
                "\nstock: " + getStock() +
                "\ncategoryId: " + getCategoryId() +
                "\nimage: " + getImage() +
                "\ntotalRate: " + getTotalRate() +
                "\ncreationDate: " + getCreationDate();
    }
}
