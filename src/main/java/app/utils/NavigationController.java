package app.utils;

import app.views.layout.dashbaord.DashboardLayout;
import javafx.scene.Scene;
import javafx.stage.Stage;

import app.views.layout.*;

import java.util.Objects;

public class NavigationController {
    private final Stage stage;
    
    public NavigationController(Stage stage) {
        this.stage = stage;
    }
    
    public void showAuthLayout() {
        AuthLayout authLayout = new AuthLayout();
        Scene scene = new Scene(authLayout.getView(), 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/assets/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("FoodApp - Auth");
    }
    
    public void showDashboardLayout() {
        DashboardLayout dashboardLayout = new DashboardLayout();
        Scene scene = new Scene(dashboardLayout.getView(), 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/assets/style.css")).toExternalForm());
        stage.setTitle("FoodApp - Dashboard");
        stage.setScene(scene);
    }
    
    public void loginSuccess() {
        System.out.println("LOGIN-- move to dashboard>>>");
        showDashboardLayout();
    }

    public void logout(){
        showAuthLayout();
    }
    
}
