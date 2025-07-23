package app.mock;

import app.models.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepo {
    private final List<Restaurant> restaurants;

    public RestaurantRepo() {
        restaurants = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        
        restaurants.add(new Restaurant(
                "1",
                "3", 
                "رستوران شایان",
                "رستوران شایان با فضایی گرم و سنتی پذیرای شماست. غذای ایرانی و فرنگی با کیفیت عالی",
                "تهران، خیابان ولیعصر، کوچه شقایق، پلاک ۱۲",
                "021-22334455",
                "10:00 صبح تا 22:00 شب",
                "/assets/images/restaurant1.png",
                "4.5"
        ));

        restaurants.add(new Restaurant(
                "2",
                "3", 
                "کافه رستوران بهشت",
                "محیطی آرام با موسیقی زنده و منوی متنوع از غذاهای ایرانی و اروپایی",
                "اصفهان، خیابان چهارباغ، جنب پل خواجو",
                "031-11223344",
                "8:00 صبح تا 24:00",
                "/assets/images/restaurant2.png",
                "4.8"
        ));

        restaurants.add(new Restaurant(
                "3",
                "3", 
                "فست فود ایران برگر",
                "برگرهای ویژه با گوشت گوساله ارگانیک و سیب زمینی سرخ کرده طلایی",
                "کرج، بلوار دانشجو، نبش شقایق",
                "026-33445566",
                "11:00 صبح تا 23:00 شب",
                "/assets/images/restaurant3.png",
                "4.2"
        ));
    }

    public Restaurant findById(String id) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Restaurant> findByOwnerId(String ownerId) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .toList();
    }

    public List<Restaurant> getAllRestaurants() {
        return new ArrayList<>(restaurants);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void updateRestaurant(Restaurant updatedRestaurant) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getId().equals(updatedRestaurant.getId())) {
                restaurants.set(i, updatedRestaurant);
                return;
            }
        }
    }

    public void removeRestaurant(String id) {
        restaurants.removeIf(restaurant -> restaurant.getId().equals(id));
    }
}
