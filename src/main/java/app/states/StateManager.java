package app.states;

import app.models.Comment;
import app.models.User;
import app.utils.NavigationController;

public class StateManager {
    private static StateManager instance;
    private NavigationController navigation;

    public final  RestaurantState restaurantState = new RestaurantState();
    public final NavigationState navigationState = new NavigationState();
    public final UserState userState = new UserState();
    public final FoodState foodState = new FoodState();
    public final CommentState commentState = new CommentState();
    private StateManager() {}  

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    
    public void setNavigationController(NavigationController controller) {
        this.navigation = controller;
    }
    
    public NavigationController getNavigationController() {
        return navigation;
    }

}
