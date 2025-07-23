package app.states;

import app.models.UserType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.BooleanProperty;

import app.models.User;
import app.models.UserType;

public class UserState {
    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private final BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);
    private final ObjectProperty<UserType> role = new SimpleObjectProperty<>(UserType.GUEST);

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public BooleanProperty isLoggedInProperty() {
        return isLoggedIn;
    }

    public ObjectProperty<UserType> roleProperty() {
        return role;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public void setCurrentUser(User user) {
        currentUser.set(user);
        isLoggedIn.set(user != null);
        role.set(user != null ? user.getUserType() : UserType.GUEST);
    }

    public void logout() {
        currentUser.set(null);
        isLoggedIn.set(false);
        role.set(UserType.GUEST);
    }

    public void login(User user) {
        this.setCurrentUser(user);
    }

    public UserType getRole() {
        return role.get();
    }
}
