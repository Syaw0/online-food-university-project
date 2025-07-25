package app.views.pages.shared;

import app.models.User;
import app.models.UserType;
import app.models.Notification;
import app.models.Notification.NotificationType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NotificationPage extends VBox {
    private final User user;
    private final Random random = new Random();

    
    private final List<String> restaurantNames = Arrays.asList(
            "رستوران سنتی ایرانی", "فست فود برگر هاوس", "پیتزا ایتالیایی",
            "رستوران چینی", "کباب کوبیده", "غذای خانگی مامان"
    );

    private final List<String> deliveryPersons = Arrays.asList(
            "علی رضایی", "محمد حسینی", "حسن احمدی", "رضا کریمی"
    );

    public NotificationPage(User user) {
        this.user = user;
        this.getStyleClass().add("notification-page-root");
        initializeUI();
    }

    private void initializeUI() {
        
        VBox header = new VBox();
        header.getStyleClass().add("notification-page-header");

        Label title = new Label("اعلان‌های شما");
        title.getStyleClass().add("notification-page-title");
        header.getChildren().add(title);

        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("notification-scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox notificationContainer = new VBox();
        notificationContainer.setSpacing(16);
        notificationContainer.setPadding(new Insets(0, 8, 0, 0));

        List<Notification> notifications = generateNotificationsForUser(user);

        if (notifications.isEmpty()) {
            VBox emptyState = createEmptyState();
            notificationContainer.getChildren().add(emptyState);
        } else {
            for (Notification notification : notifications) {
                VBox notificationCard = createNotificationCard(notification);
                notificationContainer.getChildren().add(notificationCard);
            }
        }

        scrollPane.setContent(notificationContainer);

        this.getChildren().addAll(header, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private List<Notification> generateNotificationsForUser(User user) {
        List<Notification> notifications = new ArrayList<>();
        String currentDate = getCurrentPersianDate();
        String currentTime = getCurrentTime();

        switch (user.getUserType()) {
            case BUYER -> notifications.addAll(generateBuyerNotifications(currentDate, currentTime));
            case SELLER -> notifications.addAll(generateSellerNotifications(currentDate, currentTime));
            case DELIVERY -> notifications.addAll(generateDeliveryNotifications(currentDate, currentTime));
            case ADMIN -> notifications.addAll(generateAdminNotifications(currentDate, currentTime));
        }

        return notifications;
    }

    private List<Notification> generateBuyerNotifications(String date, String time) {
        return Arrays.asList(
                new Notification("1", "سفارش شما تایید شد",
                        "سفارش شماره #ORD-۱۲۳۴ توسط " + getRandomRestaurant() + " تایید شد و در حال آماده‌سازی است.",
                        date, time, NotificationType.ORDER_UPDATE),

                new Notification("2", "سفارش آماده تحویل",
                        "سفارش شما آماده شده و تحویل پیک داده شد. تا ۱۵ دقیقه دیگر در درب منزل شما خواهد بود.",
                        date, time, NotificationType.DELIVERY),

                new Notification("3", "پرداخت موفق",
                        "پرداخت سفارش شماره #ORD-۱۲۳۴ به مبلغ ۲۵۰,۰۰۰ تومان با موفقیت انجام شد.",
                        date, time, NotificationType.PAYMENT),

                new Notification("4", "تحویل سفارش",
                        "سفارش شما با موفقیت در آدرس مشخص شده تحویل داده شد. از خرید شما متشکریم!",
                        date, time, NotificationType.DELIVERY)
        );
    }

    private List<Notification> generateSellerNotifications(String date, String time) {
        return Arrays.asList(
                new Notification("1", "سفارش جدید دریافت شد",
                        "سفارش جدید شماره #ORD-۵۶۷۸ از مشتری '" + user.getFullName() + "' دریافت شد. لطفاً بررسی کنید.",
                        date, time, NotificationType.ORDER_UPDATE),

                new Notification("2", "تایید پرداخت",
                        "پرداخت سفارش #ORD-۵۶۷۸ به مبلغ ۱۸۰,۰۰۰ تومان تایید شد. شروع به آماده‌سازی کنید.",
                        date, time, NotificationType.PAYMENT),

                new Notification("3", "پیک در انتظار",
                        "پیک " + getRandomDeliveryPerson() + " برای دریافت سفارش #ORD-۵۶۷۸ در رستوران حاضر است.",
                        date, time, NotificationType.DELIVERY),

                new Notification("4", "تحویل موفق سفارش",
                        "سفارش #ORD-۵۶۷۸ با موفقیت به مشتری تحویل داده شد. درآمد به حساب شما واریز خواهد شد.",
                        date, time, NotificationType.DELIVERY)
        );
    }

    private List<Notification> generateDeliveryNotifications(String date, String time) {
        return Arrays.asList(
                new Notification("1", "مأموریت جدید",
                        "مأموریت تحویل جدید از " + getRandomRestaurant() + " در دسترس است. آیا می‌پذیرید؟",
                        date, time, NotificationType.ORDER_UPDATE),

                new Notification("2", "آدرس مقصد",
                        "آدرس تحویل: تهران، خیابان ولیعصر، پلاک ۱۲۳. تلفن مشتری: ۰۹۱۲۱۲۳۴۵۶۷",
                        date, time, NotificationType.DELIVERY),

                new Notification("3", "تأیید دریافت سفارش",
                        "سفارش #ORD-۹۰۱۲ را از رستوران دریافت کردید. به سمت آدرس مشتری حرکت کنید.",
                        date, time, NotificationType.ORDER_UPDATE),

                new Notification("4", "پرداخت مأموریت",
                        "حق‌الزحمه مأموریت شماره #DEL-۳۴۵۶ به مبلغ ۲۵,۰۰۰ تومان به حساب شما واریز شد.",
                        date, time, NotificationType.PAYMENT)
        );
    }

    private List<Notification> generateAdminNotifications(String date, String time) {
        return Arrays.asList(
                new Notification("1", "کاربر جدید ثبت‌نام شد",
                        "کاربر جدیدی با نام 'سارا احمدی' در سیستم ثبت‌نام کرده است.",
                        date, time, NotificationType.SYSTEM),

                new Notification("2", "گزارش فروش روزانه",
                        "تعداد سفارشات امروز: ۱۲۵ | درآمد کل: ۱۵,۲۵۰,۰۰۰ تومان | رشد ۱۲٪ نسبت به دیروز",
                        date, time, NotificationType.SYSTEM),

                new Notification("3", "رستوران جدید",
                        "رستوران 'کباب کوبیده اصفهان' درخواست عضویت در پلتفرم داده است.",
                        date, time, NotificationType.SYSTEM),

                new Notification("4", "بروزرسانی سیستم",
                        "بروزرسانی ورژن ۲.۱.۵ با موفقیت نصب شد. ویژگی‌های جدید فعال گردیدند.",
                        date, time, NotificationType.SYSTEM)
        );
    }

    private VBox createNotificationCard(Notification notification) {
        VBox card = new VBox();
        card.getStyleClass().add("notification-card");

        
        HBox header = new HBox();
        header.getStyleClass().add("notification-header");

        
        VBox iconContainer = new VBox();
        iconContainer.getStyleClass().addAll("notification-icon", "notification-icon-" +
                notification.getType().toString().toLowerCase().replace("_", "-"));

        Label iconText = new Label(getIconText(notification.getType()));
        iconText.getStyleClass().add("notification-icon-text");
        iconContainer.getChildren().add(iconText);

        
        VBox content = new VBox();
        content.getStyleClass().add("notification-content");
        HBox.setHgrow(content, Priority.ALWAYS);

        Label title = new Label(notification.getTitle());
        title.getStyleClass().add("notification-title");

        Label message = new Label(notification.getMessage());
        message.getStyleClass().add("notification-message");

        content.getChildren().addAll(title,message);

        
        Label timeLabel = new Label(notification.getTime());
        timeLabel.getStyleClass().add("notification-time");

        header.getChildren().addAll(iconContainer, content, timeLabel);
        card.getChildren().add(header);

        return card;
    }

    private String getIconText(NotificationType type) {
        return switch (type) {
            case ORDER_UPDATE -> "🍽";
            case PAYMENT -> "💳";
            case DELIVERY -> "🚚";
            case SYSTEM -> "⚙";
        };
    }

    private VBox createEmptyState() {
        VBox emptyState = new VBox();
        emptyState.getStyleClass().add("empty-state");

        Label emptyIcon = new Label("🔔");
        emptyIcon.setStyle("-fx-font-size: 48px;");

        Label emptyText = new Label("هیچ اعلانی موجود نیست");
        emptyText.getStyleClass().add("empty-state-text");

        emptyState.getChildren().addAll(emptyIcon, emptyText);
        return emptyState;
    }

    private String getCurrentPersianDate() {
        return "۱۴۰۳/۰۵/۰۴"; 
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String getRandomRestaurant() {
        return restaurantNames.get(random.nextInt(restaurantNames.size()));
    }

    private String getRandomDeliveryPerson() {
        return deliveryPersons.get(random.nextInt(deliveryPersons.size()));
    }
}
