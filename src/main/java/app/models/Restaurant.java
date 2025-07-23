package app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.UUID;
import java.time.LocalDate;

public class Restaurant {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty ownerId = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty workTime = new SimpleStringProperty();
    private final StringProperty logo = new SimpleStringProperty();
    private final StringProperty totalRate = new SimpleStringProperty();
    private final StringProperty creationDate = new SimpleStringProperty();

    public Restaurant(String id, String ownerId, String name, String description,
                      String address, String phone, String workTime,
                      String logo, String totalRate) {
        
        if (id == null || id.trim().isEmpty()) {
            this.id.set(UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }

        this.ownerId.set(ownerId);
        this.name.set(name);
        this.description.set(description);
        this.address.set(address);
        this.phone.set(phone);
        this.workTime.set(workTime);
        this.logo.set(logo);
        this.totalRate.set(totalRate);
        this.creationDate.set(LocalDate.now().toString());
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty ownerIdProperty() { return ownerId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty workTimeProperty() { return workTime; }
    public StringProperty logoProperty() { return logo; }
    public StringProperty totalRateProperty() { return totalRate; }
    public StringProperty creationDateProperty() { return creationDate; }

    
    public String getId() { return id.get(); }
    public String getOwnerId() { return ownerId.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public String getAddress() { return address.get(); }
    public String getPhone() { return phone.get(); }
    public String getWorkTime() { return workTime.get(); }
    public String getLogo() { return logo.get(); }
    public String getTotalRate() { return totalRate.get(); }
    public String getCreationDate() { return creationDate.get(); }

    
    public void setId(String id) { this.id.set(id); }
    public void setOwnerId(String ownerId) { this.ownerId.set(ownerId); }
    public void setName(String name) { this.name.set(name); }
    public void setDescription(String description) { this.description.set(description); }
    public void setAddress(String address) { this.address.set(address); }
    public void setPhone(String phone) { this.phone.set(phone); }
    public void setWorkTime(String workTime) { this.workTime.set(workTime); }
    public void setLogo(String logo) { this.logo.set(logo); }
    public void setTotalRate(String totalRate) { this.totalRate.set(totalRate); }

    @Override
    public String toString() {
        return "\nid: " + getId() +
                "\nownerId:" + getOwnerId() +
                "\nname: " + getName() +
                "\ndescription: " + getDescription() +
                "\naddress: " + getAddress() +
                "\nphone: " + getPhone() +
                "\nworkTime: " + getWorkTime() +
                "\nlogo: " + getLogo() +
                "\ntotalRate: " + getTotalRate() +
                "\ncreationDate: " + getCreationDate();
    }
}
