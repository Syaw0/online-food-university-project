package app.mock;

import app.models.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepo {
    private final List<Category> categories;

    public CategoryRepo() {
        categories = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        categories.add(new Category("1", "غذای اصلی"));
        categories.add(new Category("2", "پیش غذا"));
        categories.add(new Category("3", "فست فود"));
        categories.add(new Category("4", "دسر"));
        categories.add(new Category("5", "نوشیدنی"));
    }

    public Category findById(String id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Category findByName(String name) {
        return categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void updateCategory(Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(updatedCategory.getId())) {
                categories.set(i, updatedCategory);
                return;
            }
        }
    }

    public void removeCategory(String id) {
        categories.removeIf(category -> category.getId().equals(id));
    }
}
