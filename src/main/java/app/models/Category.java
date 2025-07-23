package app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.UUID;

public class Category {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();

    public Category(String id, String name) {
        
        if (id == null || id.trim().isEmpty()) {
            this.id.set(UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }
        this.name.set(name);
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }

    
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }

    
    public void setId(String id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }

    @Override
    public String toString() {
        return "\nid: " + getId() +
                "\nname: " + getName();
    }
}
