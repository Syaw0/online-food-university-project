package app.mock;

import app.models.User;
import app.models.UserType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserRepo {
    private  final List<User> users;
    private final List<String> fakeAddresses = new ArrayList<>();
    private final Random random = new Random();

    public UserRepo() {
        users = new ArrayList<>();
        initializeFakeData();
        initializeMockData();
    }
    private void initializeFakeData() {


        // Fake addresses
        fakeAddresses.addAll(Arrays.asList(
                "تهران، خیابان آزادی، کوچه گلستان، پلاک ۱۲",
                "اصفهان، خیابان ولیعصر، مجتمع ولیعصر، طبقه ۳",
                "مشهد، بلوار وکیل آباد، خیابان امامت، پلاک ۴۵",
                "شیراز، خیابان ملاصدرا، کوچه ۲۰، پلاک ۸",
                "تبریز، خیابان بهار، کوچه ۱۵، پلاک ۲",
                "کرج، فلکه اول، بلوار موذن، پلاک ۷۲",
                "رشت، میدان شهرداری، خیابان مطهری، ساختمان ۱۲",
                "یزد، خیابان کاشانی، کوچه ۳۳، پلاک ۵",
                "اهواز، کیانپارس، خیابان ۱۶ شرقی، پلاک ۲۱",
                "قم، بلوار امین، مجتمع تجاری نگین، طبقه ۴"
        ));
    }

    public String giveFakeAddress(){
        int addressIndex = random.nextInt(fakeAddresses.size());
        return fakeAddresses.get(addressIndex);
    }

    private void initializeMockData() {
        users.add(new User(
                "0",
                "سیاوش رضایی",
                "09001234567",
                "adminPass",
                "",
                "admin@example.com",
                UserType.ADMIN,
                "/assets/images/default-profile.png",
                "",false
        ));

        users.add(new User(
                "1",
                "اصغر قاسمی",
                "09001234367",
                "123",
                "",
                "admin@example.com",
                UserType.BUYER,
                "/assets/images/default-profile.png",
                "",false
        ));

        users.add(new User(
                "2",
                "پیک موتوری",
                "09129876543",
                "deliveryPass",
                null,
                "delivery@example.com",
                UserType.DELIVERY,
                "/assets/images/default-profile.png",
                "",false

        ));


        users.add(new User(
                "3",
                "رضا افشار",
                "09129876544",
                "123",
                null,
                "delivery@example.com",
                UserType.SELLER,
                "/assets/images/default-profile.png","",
                false
        ));

    }

    public User findByPhone(String phone) {
        return users.stream()
                .filter(user -> user.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    public boolean validateCredentials(String phone, String password) {
        return users.stream()
                .anyMatch(user ->
                        user.getPhone().equals(phone) &&
                                user.getPassword().equals(password)
                );
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public  List<User> findByUserType(UserType userType) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType() == userType) {
                result.add(user);
            }
        }
        return result;
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                return;
            }
        }
    }

    public List<User> findAvailableDeliveryPersons() {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType() == UserType.DELIVERY && user.getAvailability()) {
                result.add(user);
            }
        }
        return result;
    }
}
