package app.utils;

public class Validator {
 
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidPhone(String phone) {
        // شماره با ۰۹ شروع بشه و ۱۱ رقم باشه
        return phone != null && phone.matches("^09\\d{9}$");
    }

    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return true; // چون ایمیل اختیاریه
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidFullName(String name) {
        return name != null && name.trim().length() >= 3;
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.trim().length() >= 5;
    }
}
