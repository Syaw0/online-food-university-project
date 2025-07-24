package app.models;

import javafx.beans.property.*;

public class User {
    private final StringProperty id = new SimpleStringProperty();                  
    private final StringProperty fullName = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final ObjectProperty<UserType> userType = new SimpleObjectProperty<>();
    private final StringProperty profile = new SimpleStringProperty();             
    private final StringProperty bankAccountNumber = new SimpleStringProperty();
    private Boolean  availability = false;

    public User(String id, String fullName, String phone, String password,
                String address, String email, UserType userType,
                String profile, String bankAccountNumber, Boolean availability) {

        if (id == null || id.trim().isEmpty()) {
            this.id.set(java.util.UUID.randomUUID().toString());
        } else {
            this.id.set(id);
        }

        this.fullName.set(fullName);
        this.phone.set(phone);
        this.password.set(password);
        this.email.set(email);
        this.userType.set(userType);
        this.profile.set(profile);
        this.bankAccountNumber.set(bankAccountNumber);

        
        if (userType == UserType.ADMIN || userType == UserType.DELIVERY) {
            this.address.set("");
        } else {
            this.address.set(address);
        }
    }

    
    public User(String fullName, String phone, String password,
                String address, String email, UserType userType,String profile, String bankAccountNumber,Boolean availability) {
        this("", fullName, phone, password, address, email, userType, profile   , bankAccountNumber,availability);
    }

    
    public StringProperty idProperty() { return id; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty addressProperty() { return address; }
    public StringProperty emailProperty() { return email; }
    public ObjectProperty<UserType> userTypeProperty() { return userType; }
    public StringProperty profileProperty() { return profile; }                   
    public StringProperty bankAccountNumberProperty() { return bankAccountNumber; } 

    
    public String getId() { return id.get(); }
    public String getFullName() { return fullName.get(); }
    public String getPhone() { return phone.get(); }
    public String getPassword() { return password.get(); }
    public String getAddress() { return address.get(); }
    public String getEmail() { return email.get(); }
    public UserType getUserType() { return userType.get(); }
    public String getProfile() { return profile.get(); }                          
    public String getBankAccountNumber() { return bankAccountNumber.get(); }      
    public Boolean getAvailability() { return availability; }
    
    public void setId(String id) { this.id.set(id); }                             
    public void setFullName(String name) { fullName.set(name); }
    public void setUserType(UserType userType) { this.userType.set(userType); }
    public void setProfile(String profile) { this.profile.set(profile); }         
    public void setBankAccountNumber(String bankAccountNumber) {                  
        this.bankAccountNumber.set(bankAccountNumber);
    }
    public void setPhone(String phone) { this.phone.set(phone); }
    public void setPassword(String password) { this.password.set(password); }
    public void setAddress(String address) { this.address.set(address); }
    public void setEmail(String email) { this.email.set(email); }
    public  void setAvailability(Boolean availability){this.availability = availability;}

    public String roleToPersian(){
        return switch (this.getUserType()) {
            case BUYER -> "خریدار";
            case ADMIN -> "ادمین";
            case DELIVERY -> "پیک";
            case SELLER -> "رستوران دار";
            case GUEST -> "مهمان";
        };
    }

    @Override
    public String toString() {
        return "\nid: " + getId() +                                               
                "\nname:" + getFullName() +
                "\nphone: " +getPhone() +
                "\npassword: " + getPassword() +
                "\naddress: " + getAddress() +
                "\nemail: " + getEmail() +
                "\nuser type: " + getUserType() +
                "\nprofile: " + getProfile() +                                   
                "\nbank account: " + getBankAccountNumber();                     
    }
}
