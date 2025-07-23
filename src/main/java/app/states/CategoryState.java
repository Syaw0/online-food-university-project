package app.states;

import app.models.Category;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CategoryState {
    private final ObjectProperty<Category> currentCategory = new SimpleObjectProperty<>();

    public ObjectProperty<Category> currentCategoryProperty() {
        return currentCategory;
    }

    public Category getCurrentCategory() {
        return currentCategory.get();
    }

    public void setCurrentCategory(Category category) {
        currentCategory.set(category);
    }
}
