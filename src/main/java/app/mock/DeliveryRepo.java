package app.mock;

import app.models.DeliveryPerson;
import java.util.Arrays;
import java.util.List;

public class DeliveryRepo {
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return Arrays.asList(
                new DeliveryPerson("پیک موتوری حسین خلیلی", "09121234567", true, 2),
                new DeliveryPerson("پیک موتوری عباس پورشفق", "09129876543", true, 1),
                new DeliveryPerson("پیک موتوری حسن علی قاسمی", "09123456789", false, 5),
                new DeliveryPerson("پیک موتوری علی مرادی", "09127778899", true, 0),
                new DeliveryPerson("پیک موتوری محمد رضایی", "09106543210", false, 3)
        );
    }
}
