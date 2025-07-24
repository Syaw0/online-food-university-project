package app.views.pages.admin;

import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;

public class SystemHealthPage extends VBox {
    private final Random random = new Random();

    public SystemHealthPage() {
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(30);
        setPadding(new Insets(25));
        setBackground(new Background(new BackgroundFill(Color.web("#f7f9fc"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        Typography title = new Typography("وضعیت سلامت سیستم", Typography.Variant.H1);
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        
        HBox systemStatusBox = createSystemStatus();

        
        HBox statsContainer = createStatsCards();

        
        VBox resourceSection = createResourceUsageSection();

        
        HBox chartsContainer = new HBox(20);
        chartsContainer.setAlignment(Pos.CENTER);

        
        VBox ordersChartBox = createOrdersTimelineChart();
        
        VBox errorsChartBox = createErrorsChart();

        chartsContainer.getChildren().addAll(ordersChartBox, errorsChartBox);

        getChildren().addAll(titleBox, systemStatusBox, statsContainer, resourceSection, chartsContainer);
    }

    private HBox createSystemStatus() {
        HBox statusBox = new HBox(20);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setPadding(new Insets(10));

        
        VBox statusIndicator = new VBox(10);
        statusIndicator.setAlignment(Pos.CENTER);

        Label statusLabel = new Label("وضعیت سیستم: در حال اجرا");
        statusLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        
        Label uptimeLabel = new Label("زمان فعالیت: ۲۴ روز ۱۲:۴۵:۳۲");
        uptimeLabel.setStyle("-fx-text-fill: #555;");

        statusIndicator.getChildren().addAll(statusLabel, uptimeLabel);

        
        VBox metricsBox = new VBox(5);
        metricsBox.setAlignment(Pos.CENTER_LEFT);

        metricsBox.getChildren().add(createMetricItem("نسخه سیستم", "2.8.4"));
        metricsBox.getChildren().add(createMetricItem("تاریخ بروزرسانی", "۱۴۰۳/۰۵/۱۵"));
        metricsBox.getChildren().add(createMetricItem("تعداد درخواست‌ها", "۴۵۲,۱۲۱"));

        statusBox.getChildren().addAll(statusIndicator, metricsBox);
        return statusBox;
    }

    private HBox createMetricItem(String title, String value) {
        HBox item = new HBox(10);

        Label titleLabel = new Label(title + ":");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 120;");

        Label valueLabel = new Label(value);

        item.getChildren().addAll(valueLabel, titleLabel);
        return item;
    }

    private HBox createStatsCards() {
        HBox statsContainer = new HBox(20);
        statsContainer.setAlignment(Pos.CENTER);

        
        VBox usersCard = createStatCard("۴,۵۸۹", "کاربر", "کاربران فعال", "#2196F3");

        
        VBox ordersCard = createStatCard("۱۲,۴۵۶", "سفارش", "سفارشات ماه", "#4CAF50");

        
        VBox pendingCard = createStatCard("۱۲", "سفارش", "سفارشات در انتظار", "#FFC107");

        
        VBox completionCard = createStatCard("۹۸%", "نرخ تکمیل", "نرخ موفقیت", "#9C27B0");

        
        VBox trafficCard = createStatCard("۱۲۴,۵۶۹", "بازدید", "بازدید امروز", "#E91E63");

        statsContainer.getChildren().addAll(usersCard, ordersCard, pendingCard, completionCard, trafficCard);
        return statsContainer;
    }

    private VBox createStatCard(String value, String unit, String title, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        card.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(180);

        
        HBox valueBox = new HBox(5);
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font(20));
        valueLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label unitLabel = new Label(unit);
        unitLabel.setStyle("-fx-text-fill: #777;");

        valueBox.getChildren().addAll(valueLabel, unitLabel);
        valueBox.setAlignment(Pos.CENTER);

        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #555;");

        card.getChildren().addAll(valueBox, titleLabel);
        return card;
    }

    private VBox createResourceUsageSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(20));
        section.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        section.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        section.setMinWidth(800);

        Label title = new Label("مصرف منابع سیستم");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        VBox resourcesBox = new VBox(15);

        resourcesBox.getChildren().add(createResourceBar("پردازنده (CPU)", 75, "#2196F3"));
        resourcesBox.getChildren().add(createResourceBar("حافظه رم (RAM)", 62, "#4CAF50"));
        resourcesBox.getChildren().add(createResourceBar("فضای ذخیره‌سازی", 45, "#FF9800"));
        resourcesBox.getChildren().add(createResourceBar("پهنای باند شبکه", 28, "#E91E63"));

        section.getChildren().addAll(title, resourcesBox);
        return section;
    }

    private HBox createResourceBar(String title, int usage, String color) {
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 180;");

        ProgressBar progressBar = new ProgressBar(usage / 100.0);
        progressBar.setStyle("-fx-accent: " + color + ";");
        progressBar.setMinWidth(400);
        progressBar.setMinHeight(15);

        Label percentLabel = new Label(usage + "%");
        percentLabel.setStyle("-fx-min-width: 50;");

        container.getChildren().addAll(titleLabel, progressBar, percentLabel);
        return container;
    }

    private VBox createOrdersTimelineChart() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        container.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        container.setMinSize(500, 350);

        Label title = new Label("روند سفارشات (۷ روز گذشته)");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("روزهای هفته");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("تعداد سفارشات");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(null);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setCreateSymbols(true);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        
        LocalDate today = LocalDate.now();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("fa"));
            int orders = 150 + random.nextInt(250); 

            series.getData().add(new XYChart.Data<>(dayName, orders));
        }

        chart.getData().add(series);

        
        series.getNode().setStyle("-fx-stroke: #4CAF50; -fx-stroke-width: 2px;");

        container.getChildren().addAll(title, chart);
        return container;
    }

    private VBox createErrorsChart() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        container.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        container.setMinSize(400, 350);

        Label title = new Label("آمار خطاهای سیستم");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("نوع خطا");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("تعداد");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(null);
        chart.setLegendVisible(false);
        chart.setCategoryGap(10);
        chart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String[] errorTypes = {"سرور", "پایگاه داده", "اتصال", "احراز هویت", "پرداخت"};
        int[] errorCounts = {12, 8, 5, 3, 7};

        for (int i = 0; i < errorTypes.length; i++) {
            series.getData().add(new XYChart.Data<>(errorTypes[i], errorCounts[i]));
        }

        chart.getData().add(series);

        
        String[] colors = {"#E91E63", "#9C27B0", "#2196F3", "#FF9800", "#4CAF50"};
        int colorIndex = 0;
        for (XYChart.Data<String, Number> data : series.getData()) {
            int finalColorIndex = colorIndex;
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + colors[finalColorIndex] + ";");
                }
            });
            colorIndex = (colorIndex + 1) % colors.length;
        }

        container.getChildren().addAll(title, chart);
        return container;
    }
}
