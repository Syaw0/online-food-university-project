package app.mock;

import app.models.User;
import app.models.UserType;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private  final List<User> users;

    public UserRepo() {
        users = new ArrayList<>();
        initializeMockData();
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
