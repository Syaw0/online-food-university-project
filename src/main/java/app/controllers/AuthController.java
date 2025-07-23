package app.controllers;

import app.models.User;
import app.mock.UserRepo;

public class AuthController {
    private final UserRepo userRepo;
    
    public AuthController() {
        this.userRepo = new UserRepo();
    }
    
    public User login(String phone, String password) {
        User user = userRepo.findByPhone(phone);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User getUserByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }
}
