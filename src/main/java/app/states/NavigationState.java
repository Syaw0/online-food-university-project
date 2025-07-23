package app.states;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NavigationState {
    private final StringProperty currentView = new SimpleStringProperty("dashboard");

    public StringProperty currentViewProperty() {
        return currentView;
    }

    public String getCurrentView() {
        return currentView.get();
    }

    public void setCurrentView(String view) {
        currentView.set(view);
    }
}