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

        
        this.restaurantId.set(restaurantId != null ? restaurantId : "");
        this.name.set(name != null ? name : "غذای بدون نام");
        this.description.set(description != null ? description : "توضیحی موجود نیست");
        this.price.set(price != null ? price : "0");
        this.stock.set(stock != null ? stock : "0");
        this.categoryId.set(categoryId != null ? categoryId : "متفرقه");
        this.image.set(image != null ? image : "/assets/images/foods/default.png");
        this.totalRate.set(totalRate != null ? totalRate : "0.0");
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

    
    public String getId() {
        String value = id.get();
        return value != null ? value : "";
    }

    public String getRestaurantId() {
        String value = restaurantId.get();
        return value != null ? value : "";
    }

    public String getName() {
        String value = name.get();
        return value != null ? value : "غذای بدون نام";
    }

    public String getDescription() {
        String value = description.get();
        return value != null ? value : "توضیحی موجود نیست";
    }

    public String getPrice() {
        String value = price.get();
        return value != null ? value : "0";
    }

    public String getStock() {
        String value = stock.get();
        return value != null ? value : "0";
    }

    public String getCategoryId() {
        String value = categoryId.get();
        return value != null ? value : "متفرقه";
    }

    public String getImage() {
        String value = image.get();
        return value != null ? value : "/assets/images/foods/default.png";
    }

    public String getTotalRate() {
        String value = totalRate.get();
        return value != null ? value : "0.0";
    }

    public String getCreationDate() {
        String value = creationDate.get();
        return value != null ? value : LocalDate.now().toString();
    }

    
    public void setId(String id) {
        this.id.set(id != null ? id : UUID.randomUUID().toString());
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId.set(restaurantId != null ? restaurantId : "");
    }

    public void setName(String name) {
        this.name.set(name != null ? name : "غذای بدون نام");
    }

    public void setDescription(String description) {
        this.description.set(description != null ? description : "توضیحی موجود نیست");
    }

    public void setPrice(String price) {
        this.price.set(price != null ? price : "0");
    }

    public void setStock(String stock) {
        this.stock.set(stock != null ? stock : "0");
    }

    public void setCategoryId(String categoryId) {
        this.categoryId.set(categoryId != null ? categoryId : "متفرقه");
    }

    public void setImage(String image) {
        this.image.set(image != null ? image : "/assets/images/foods/default.png");
    }

    public void setTotalRate(String totalRate) {
        this.totalRate.set(totalRate != null ? totalRate : "0.0");
    }

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
