package app.mock;

import app.models.Food;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FoodRepo {
    private final List<Food> foods;
    private final Random random = new Random();

    
    private final String[] foodNames = {
            "کباب کوبیده", "کباب برگ", "کباب بختیاری", "جوجه کباب", "کباب شیشلیک",
            "قورمه سبزی", "قیمه بادمجان", "فسنجان", "خورشت کرفس", "آش رشته",
            "برگر کلاسیک", "برگر چیز", "برگر مخصوص", "ساندویچ کباب", "هات داگ",
            "پیتزا مارگاریتا", "پیتزا پپرونی", "پیتزا مخصوص", "پیتزا سبزیجات", "پیتزا گوشت",
            "پاستا کربونارا", "پاستا آلفردو", "لازانیا", "اسپاگتی بولونز", "ماکارونی پنیر",
            "سوشی سالمون", "سوشی تن", "ساشیمی", "رول کالیفرنیا", "تمپورا میگو",
            "استیک گوساله", "استیک مرغ", "ماهی سالمون", "میگو سرخ شده", "کباب ماهی",
            "سالاد سزار", "سالاد یونانی", "سالاد فصل", "سوپ جو", "سوپ قارچ",
            "چلو مرغ", "زرشک پلو", "باقلا پلو", "لوبیا پلو", "عدس پلو",
            "کوکو سبزی", "میرزا قاسمی", "کشک بادمجان", "بورانی اسفناج", "آبگوشت"
    };

    private final String[] foodDescriptions = {
            "طعم اصیل و سنتی با بهترین مواد اولیه",
            "پخت شده با دقت و مهارت آشپزان ماهر",
            "ترکیب عالی از طعم و کیفیت",
            "با مواد تازه و ارگانیک تهیه شده",
            "دستور پخت خانگی و اصیل",
            "ویژه رستوران با طعمی بی‌نظیر",
            "محبوب‌ترین غذای منو",
            "با ادویه‌های درجه یک و معطر",
            "تازه پخت شده و داغ سرو می‌شود",
            "طعم خوشمزه و فراموش‌نشدنی"
    };

    private final String[] categories = {
            "غذای اصلی", "پیش غذا", "فست فود", "دسر", "نوشیدنی", "سالاد"
    };

    private final int[] priceRanges = {
            25000, 35000, 45000, 55000, 65000, 75000, 85000, 95000, 105000, 125000
    };

    public FoodRepo() {
        foods = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        
        addClassicFoods();

        
        generateRandomFoods();
    }

    private void addClassicFoods() {
        
        foods.add(new Food("1", "1", "کباب بختیاری",
                "کباب بختیاری با برنج، گوجه و سبزیجات تازه همراه با دوغ محلی",
                "85000", "50", "غذای اصلی", "/assets/images/foods/food2.png", "4.7"));

        foods.add(new Food("2", "1", "قورمه سبزی",
                "قورمه سبزی اصیل با گوشت گوسفندی، لوبیا قرمز و لیمو عمانی",
                "75000", "35", "غذای اصلی", "/assets/images/foods/food1.png", "4.5"));

        foods.add(new Food("3", "2", "استیک راسته",
                "استیک راسته گوساله ۳۰۰ گرمی با سس قارچ و سیب زمینی دودی",
                "125000", "20", "غذای اصلی", "/assets/images/foods/food3.png", "4.8"));

        foods.add(new Food("4", "2", "سالاد سزار",
                "سالاد سزار با سس ویژه و نان سیر خانگی",
                "45000", "40", "پیش غذا", "/assets/images/foods/food4.png", "4.3"));

        foods.add(new Food("5", "3", "برگر ویژه",
                "برگر با گوشت ۱۸۰ گرمی، پنیر چدار، قارچ و سیب زمینی",
                "65000", "60", "فست فود", "/assets/images/foods/food5.png", "4.6"));

        foods.add(new Food("6", "3", "پیتزا مخصوص",
                "پیتزا با پنیر موزارلا، ژامبون، قارچ و زیتون",
                "70000", "30", "فست فود", "/assets/images/foods/food6.png", "4.4"));

        foods.add(new Food("7", "1", "جوجه کباب",
                "جوجه کباب ترش با برنج سبزی پلو و دنبلان",
                "80000", "40", "غذای اصلی", "/assets/images/foods/food7.png", "4.6"));

        foods.add(new Food("8", "2", "سوشی سالمون",
                "سوشی سالمون تازه با سس مخصوص",
                "90000", "25", "پیش غذا", "/assets/images/foods/food8.png", "4.9"));

        foods.add(new Food("9", "3", "پاستا آلفردو",
                "پاستا با سس آلفردو و مرغ گریل شده",
                "60000", "35", "فست فود", "/assets/images/foods/food9.png", "4.5"));

        foods.add(new Food("10", "1", "دلمه برگ مو",
                "دلمه برگ مو با گوشت چرخ کرده و برنج",
                "55000", "45", "غذای اصلی", "/assets/images/foods/food10.png", "4.7"));
    }

    private void generateRandomFoods() {
        int currentId = 11; 

        
        for (int restaurantId = 1; restaurantId <= 10; restaurantId++) {
            
            int numberOfFoods = random.nextInt(5) + 3; 

            for (int foodIndex = 0; foodIndex < numberOfFoods; foodIndex++) {
                
                String foodName = foodNames[random.nextInt(foodNames.length)];

                
                String finalFoodName = foodName;
                String finalRestaurantId = String.valueOf(restaurantId);
                boolean exists = foods.stream()
                        .anyMatch(food -> food.getName().equals(finalFoodName) &&
                                food.getRestaurantId().equals(finalRestaurantId));

                if (exists) {
                    continue; 
                }

                
                String description = foodDescriptions[random.nextInt(foodDescriptions.length)];

                
                int price = priceRanges[random.nextInt(priceRanges.length)];

                
                int stock = random.nextInt(91) + 10; 

                
                String category = categories[random.nextInt(categories.length)];

                
                String imagePath = "/assets/images/foods/food" + ((currentId % 20) + 1) + ".png";

                
                double rating = 3.5 + (random.nextDouble() * 1.5);
                String ratingStr = String.format("%.1f", rating);

                
                foods.add(new Food(
                        String.valueOf(currentId),
                        String.valueOf(restaurantId),
                        foodName,
                        description,
                        String.valueOf(price),
                        String.valueOf(stock),
                        category,
                        imagePath,
                        ratingStr
                ));

                currentId++;
            }
        }

        System.out.println("تعداد کل غذاهای تولید شده: " + foods.size());
    }

    
    public Food findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.err.println("خطا: ID غذا null یا خالی است");
            return null;
        }

        Food food = foods.stream()
                .filter(f -> f != null && f.getId() != null && f.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (food == null) {
            System.err.println("هشدار: غذا با ID " + id + " پیدا نشد");
        }

        return food;
    }

    public List<Food> findByRestaurantId(String restaurantId) {
        if (restaurantId == null || restaurantId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return foods.stream()
                .filter(food -> food != null &&
                        food.getRestaurantId() != null &&
                        food.getRestaurantId().equals(restaurantId))
                .toList();
    }

    public List<Food> findByCategoryId(String categoryId) {
        if (categoryId == null || categoryId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return foods.stream()
                .filter(food -> food != null &&
                        food.getCategoryId() != null &&
                        food.getCategoryId().equals(categoryId))
                .toList();
    }

    public List<Food> getAllFoods() {
        return foods.stream()
                .filter(food -> food != null)
                .collect(java.util.stream.Collectors.toList());
    }

    public void addFood(Food food) {
        if (food != null) {
            foods.add(food);
        }
    }

    public void updateFood(Food updatedFood) {
        if (updatedFood == null || updatedFood.getId() == null) {
            return;
        }

        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            if (food != null && food.getId() != null && food.getId().equals(updatedFood.getId())) {
                foods.set(i, updatedFood);
                return;
            }
        }
    }

    public void removeFood(String id) {
        if (id == null || id.trim().isEmpty()) {
            return;
        }

        foods.removeIf(food -> food != null && food.getId() != null && food.getId().equals(id));
    }

    
    public List<Food> getPopularFoods(int limit) {
        return foods.stream()
                .filter(food -> food != null && food.getTotalRate() != null)
                .sorted((f1, f2) -> {
                    try {
                        return Double.compare(
                                Double.parseDouble(f2.getTotalRate()),
                                Double.parseDouble(f1.getTotalRate())
                        );
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .limit(limit)
                .toList();
    }

    
    public List<Food> getExpensiveFoods(int limit) {
        return foods.stream()
                .filter(food -> food != null && food.getPrice() != null)
                .sorted((f1, f2) -> {
                    try {
                        return Integer.compare(
                                Integer.parseInt(f2.getPrice()),
                                Integer.parseInt(f1.getPrice())
                        );
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .limit(limit)
                .toList();
    }

    
    public List<Food> searchFoods(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return foods.stream()
                .filter(food -> food != null &&
                        ((food.getName() != null && food.getName().contains(query)) ||
                                (food.getDescription() != null && food.getDescription().contains(query)) ||
                                (food.getCategoryId() != null && food.getCategoryId().contains(query)))
                )
                .toList();
    }

    
    public void printStatistics() {
        System.out.println("=== آمار غذاها ===");
        System.out.println("تعداد کل غذاها: " + foods.size());

        for (int i = 1; i <= 10; i++) {
            String restaurantId = String.valueOf(i);
            long count = foods.stream()
                    .filter(food -> food != null &&
                            food.getRestaurantId() != null &&
                            food.getRestaurantId().equals(restaurantId))
                    .count();
            System.out.println("رستوران " + i + ": " + count + " غذا");
        }

        
        System.out.println("\n=== آمار دسته‌بندی‌ها ===");
        for (String category : categories) {
            long count = foods.stream()
                    .filter(food -> food != null &&
                            food.getCategoryId() != null &&
                            food.getCategoryId().equals(category))
                    .count();
            System.out.println(category + ": " + count + " غذا");
        }
    }
}
