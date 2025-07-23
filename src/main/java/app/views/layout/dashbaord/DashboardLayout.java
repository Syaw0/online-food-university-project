package app.views.layout.dashbaord;

import app.models.User;
import app.states.StateManager;
import app.views.layout.dashbaord.nav.NavFactory;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class DashboardLayout {
    public HBox getView() {
        HBox rootLayout = new HBox();
        rootLayout.getStyleClass().add("dashboard-root");
        rootLayout.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        rootLayout.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        User currentUser = StateManager.getInstance().userState.getCurrentUser();

        // Navbar with role-specific items
        Region navbar = NavFactory.createNav(currentUser).getView(currentUser);
        rootLayout.getChildren().add(navbar);

        // Main content
        Main mainContent = new Main();
        Region mainRegion = mainContent.getView(currentUser);
        HBox.setHgrow(mainRegion, Priority.ALWAYS);
        rootLayout.getChildren().add(mainRegion);

        // Set up width binding after components are created
        mainContent.setupWidthBinding(rootLayout);

        // Bind navbar width to 20% of root layout
        navbar.prefWidthProperty().bind(
                rootLayout.widthProperty().multiply(0.2)
        );

        return rootLayout;
    }
}