package app.mock;

import app.models.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodRepo {
    private final List<Food> foods;

    public FoodRepo() {
        foods = new ArrayList<>();
        initializeMockData();
    }
    private void initializeMockData() {
        foods.add(new Food("1", "1", "کباب بختیاری",
                "کباب بختیاری با برنج، گوجه و سبزیجات تازه همراه با دوغ محلی",
                "85000", "50", "غذای اصلی", "/assets/images/foods/kebab.png", "4.7"));

        foods.add(new Food("2", "1", "قورمه سبزی",
                "قورمه سبزی اصیل با گوشت گوسفندی، لوبیا قرمز و لیمو عمانی",
                "75000", "35", "غذای اصلی", "/assets/images/foods/ghorme.png", "4.5"));

        foods.add(new Food("3", "2", "استیک راسته",
                "استیک راسته گوساله ۳۰۰ گرمی با سس قارچ و سیب زمینی دودی",
                "125000", "20", "غذای اصلی", "/assets/images/foods/steak.png", "4.8"));

        foods.add(new Food("4", "2", "سالاد سزار",
                "سالاد سزار با سس ویژه و نان سیر خانگی",
                "45000", "40", "پیش غذا", "/assets/images/foods/salad.png", "4.3"));

        foods.add(new Food("5", "3", "برگر ویژه",
                "برگر با گوشت ۱۸۰ گرمی، پنیر چدار، قارچ و سیب زمینی",
                "65000", "60", "فست فود", "/assets/images/foods/burger.png", "4.6"));

        // Add 5 more foods to make 10 total
        foods.add(new Food("6", "3", "پیتزا مخصوص",
                "پیتزا با پنیر موزارلا، ژامبون، قارچ و زیتون",
                "70000", "30", "فست فود", "/assets/images/foods/pizza.png", "4.4"));

        foods.add(new Food("7", "1", "جوجه کباب",
                "جوجه کباب ترش با برنج سبزی پلو و دنبلان",
                "80000", "40", "غذای اصلی", "/assets/images/foods/chicken.png", "4.6"));

        foods.add(new Food("8", "2", "سوشی سالمون",
                "سوشی سالمون تازه با سس مخصوص",
                "90000", "25", "پیش غذا", "/assets/images/foods/sushi.png", "4.9"));

        foods.add(new Food("9", "3", "پاستا آلفردو",
                "پاستا با سس آلفردو و مرغ گریل شده",
                "60000", "35", "فست فود", "/assets/images/foods/pasta.png", "4.5"));

        foods.add(new Food("10", "1", "دلمه برگ مو",
                "دلمه برگ مو با گوشت چرخ کرده و برنج",
                "55000", "45", "غذای اصلی", "/assets/images/foods/dolme.png", "4.7"));
    }

    public Food findById(String id) {
        return foods.stream()
                .filter(food -> food.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Food> findByRestaurantId(String restaurantId) {
        return foods.stream()
                .filter(food -> food.getRestaurantId().equals(restaurantId))
                .toList();
    }

    public List<Food> findByCategoryId(String categoryId) {
        return foods.stream()
                .filter(food -> food.getCategoryId().equals(categoryId))
                .toList();
    }

    public List<Food> getAllFoods() {
        return new ArrayList<>(foods);
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void updateFood(Food updatedFood) {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getId().equals(updatedFood.getId())) {
                foods.set(i, updatedFood);
                return;
            }
        }
    }

    public void removeFood(String id) {
        foods.removeIf(food -> food.getId().equals(id));
    }
}
