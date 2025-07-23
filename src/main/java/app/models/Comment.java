package app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.UUID;
import java.time.LocalDate;

public class Comment {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty foodId = new SimpleStringProperty();
    private final StringProperty userId = new SimpleStringProperty();
    private final StringProperty picture = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty stars = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();

    public Comment(String id, String foodId, String userId, String picture,
                   String title, String description, String stars) {
        
        if (id == null || id.trim().isEmpty()) {
            this.id.set(UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }

        this.foodId.set(foodId);
        this.userId.set(userId);
        this.picture.set(picture);
        this.title.set(title);
        this.description.set(description);
        this.stars.set(stars);
        this.creationDate.set(LocalDate.now().toString());
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty foodIdProperty() { return foodId; }
    public StringProperty userIdProperty() { return userId; }
    public StringProperty pictureProperty() { return picture; }
    public StringProperty titleProperty() { return title; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty starsProperty() { return stars; }
    public StringProperty creationDateProperty() { return creationDate; }

    
    public String getId() { return id.get(); }
    public String getFoodId() { return foodId.get(); }
    public String getUserId() { return userId.get(); }
    public String getPicture() { return picture.get(); }
    public String getTitle() { return title.get(); }
    public String getDescription() { return description.get(); }
    public String getStars() { return stars.get(); }
    public String getCreationDate() { return creationDate.get(); }

    
    public void setId(String id) { this.id.set(id); }
    public void setFoodId(String foodId) { this.foodId.set(foodId); }
    public void setUserId(String userId) { this.userId.set(userId); }
    public void setPicture(String picture) { this.picture.set(picture); }
    public void setTitle(String title) { this.title.set(title); }
    public void setDescription(String description) { this.description.set(description); }
    public void setStars(String stars) { this.stars.set(stars); }

    @Override
    public String toString() {
        return "\nid: " + getId() +
                "\nfoodId:" + getFoodId() +
                "\nuserId: " + getUserId() +
                "\ntitle: " + getTitle() +
                "\ndescription: " + getDescription() +
                "\nstars: " + getStars() +
                "\ncreationDate: " + getCreationDate();
    }
}
