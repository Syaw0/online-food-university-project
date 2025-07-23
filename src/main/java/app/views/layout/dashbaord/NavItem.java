package app.views.layout.dashbaord;


public class NavItem {
    private final String displayName;
    private final String targetView;

    public NavItem(String displayName, String targetView) {
        this.displayName = displayName;
        this.targetView = targetView;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTargetView() {
        return targetView;
    }
}