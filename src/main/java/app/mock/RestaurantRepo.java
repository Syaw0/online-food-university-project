package app.mock;

import app.models.Restaurant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RestaurantRepo {
    private final List<Restaurant> restaurants;
    private final Random random = new Random();

    
    private final String[] names = {
            "رستوران شایان",
            "کافه رستوران بهشت",
            "فست فود ایران برگر",
            "رستوران سنتی گل یاس",
            "رستوران دریایی ماهی تی تی",
            "رستوران ایتالیایی ویونا",
            "فست فود مک دونالد",
            "رستوران فرانسوی پاریس",
            "رستوران چینی شانگهای",
            "کافه رستوران مدرن"
    };

    private final String[] descriptions = {
            "رستوران شایان با فضایی گرم و سنتی پذیرای شماست. غذای ایرانی و فرنگی با کیفیت عالی",
            "محیطی آرام با موسیقی زنده و منوی متنوع از غذاهای ایرانی و اروپایی",
            "برگرهای ویژه با گوشت گوساله ارگانیک و سیب زمینی سرخ کرده طلایی",
            "رستورانی با فضای سنتی و غذاهای ایرانی اصیل",
            "رستورانی تخصصی در زمینه غذاهای دریایی با مواد اولیه تازه",
            "رستورانی با محیطی دلنشین و غذاهای ایتالیایی اصل",
            "فست فودی با سرویس سریع و منوی متنوع",
            "رستورانی با فضای رمانتیک و غذاهای فرانسوی",
            "رستورانی با منوی کامل غذاهای چینی",
            "کافه رستورانی با طراحی مدرن و منوی بین المللی"
    };

    private final String[] addresses = {
            "تهران، خیابان ولیعصر، کوچه شقایق، پلاک ۱۲",
            "اصفهان، خیابان چهارباغ، جنب پل خواجو",
            "کرج، بلوار دانشجو، نبش شقایق",
            "مشهد، بلوار وکیل آباد، پلاک ۱۲۳",
            "رشت، میدان شهرداری، ساختمان معین",
            "شیراز، خیابان زند، جنب عمارت کلاه فرنگی",
            "تبریز، خیابان امام، بازار بزرگ",
            "قم، خیابان امام خمینی، کوچه شهیدان",
            "اهواز، کیانپارس، خیابان ۱۲",
            "ارومیه، خیابان امام، جنب پارک ملت"
    };

    private final String[] phones = {
            "021-22334455", "031-11223344", "026-33445566",
            "051-44556677", "013-55667788", "071-66778899",
            "041-77889900", "025-88990011", "061-99001122", "044-00112233"
    };

    private final String[] workingHours = {
            "10:00 صبح تا 22:00 شب",
            "8:00 صبح تا 24:00",
            "11:00 صبح تا 23:00 شب",
            "7:00 صبح تا 23:00 شب",
            "9:00 صبح تا 22:00 شب",
            "12:00 ظهر تا 23:00 شب",
            "10:00 صبح تا 1:00 بامداد",
            "8:00 صبح تا 22:00 شب",
            "11:00 صبح تا 24:00",
            "10:00 صبح تا 23:00 شب"
    };

    private final String[] images = {
            "/assets/images/restaurant/restaurant1.png",
            "/assets/images/restaurant/restaurant2.png",
            "/assets/images/restaurant/restaurant3.png",
            "/assets/images/restaurant/restaurant4.png",
            "/assets/images/restaurant/restaurant5.png",
            "/assets/images/restaurant/restaurant6.png",
            "/assets/images/restaurant/restaurant7.png",
            "/assets/images/restaurant/restaurant8.png",
            "/assets/images/restaurant/restaurant9.png",
            "/assets/images/restaurant/restaurant10.png"
    };

    public RestaurantRepo() {
        restaurants = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        
        for (int i = 1; i <= 10; i++) {
            String id = String.valueOf(i);
            String ownerId = "3"; 

            
            String name = names[i-1];
            String description = descriptions[i-1];
            String address = addresses[i-1];
            String phone = phones[i-1];
            String hours = workingHours[i-1];
            String image = images[i-1];
            String rating = String.format("%.1f", 4.0 + random.nextDouble()); 

            restaurants.add(new Restaurant(
                    id,
                    ownerId,
                    name,
                    description,
                    address,
                    phone,
                    hours,
                    image,
                    rating
            ));
        }
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
