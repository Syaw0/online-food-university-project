package app.views.pages.admin;

import app.models.Food;
import app.mock.FoodRepo;
import app.mock.OrderRepo;
import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class SalesReportPage extends VBox {
    private final OrderRepo orderRepo = new OrderRepo();
    private final FoodRepo foodRepo = new FoodRepo();

    public SalesReportPage() {
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(30);
        setPadding(new Insets(25));
        setBackground(new Background(new BackgroundFill(Color.web("#f7f9fc"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        Typography title = new Typography("گزارش فروش", Typography.Variant.H1);
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        
        HBox filterBox = createFilterControls();

        
        HBox statsContainer = createStatsCards();

        
        HBox chartsContainer = new HBox(20);
        chartsContainer.setAlignment(Pos.CENTER);

        
        VBox salesChartBox = createSalesChart();
        
        VBox foodsChartBox = createTopFoodsChart();

        chartsContainer.getChildren().addAll(salesChartBox, foodsChartBox);

        getChildren().addAll(titleBox, filterBox, statsContainer, chartsContainer);
    }

    private HBox createFilterControls() {
        HBox filterBox = new HBox(15);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10));

        Label filterLabel = new Label("بازه زمانی:");
        filterLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        ComboBox<String> periodCombo = new ComboBox<>();
        periodCombo.getItems().addAll("هفته جاری", "ماه جاری", "سه ماه اخیر", "کل سال");
        periodCombo.setValue("ماه جاری");
        periodCombo.setStyle("-fx-font-size: 14;");

        filterBox.getChildren().addAll(periodCombo, filterLabel);
        return filterBox;
    }

    private HBox createStatsCards() {
        HBox statsContainer = new HBox(20);
        statsContainer.setAlignment(Pos.CENTER);

        
        VBox revenueCard = createStatCard("1.2 میلیون", "تومان", "درآمد کل", "icon-revenue");

        
        VBox ordersCard = createStatCard("۲۴۵", "سفارش", "تعداد سفارشات", "icon-orders");

        
        VBox avgOrderCard = createStatCard("۴۸,۰۰۰", "تومان", "میانگین سفارش", "icon-avg");

        
        VBox topRestaurantCard = createStatCard("رستوران پدیده", "", "پرفروش‌ترین رستوران", "icon-restaurant");

        statsContainer.getChildren().addAll(revenueCard, ordersCard, avgOrderCard, topRestaurantCard);
        return statsContainer;
    }

    private VBox createStatCard(String value, String unit, String title, String iconClass) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        card.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(200);

        
        HBox iconBox = new HBox();
        iconBox.setStyle("-fx-background-color: #e3f2fd; -fx-padding: 10; -fx-background-radius: 20;");
        iconBox.setMinSize(40, 40);

        
        HBox valueBox = new HBox(5);
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font(24));
        valueLabel.setStyle("-fx-font-weight: bold;");

        Label unitLabel = new Label(unit);
        unitLabel.setStyle("-fx-text-fill: #777;");

        valueBox.getChildren().addAll(valueLabel, unitLabel);
        valueBox.setAlignment(Pos.CENTER);

        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #555;");

        card.getChildren().addAll(iconBox, valueBox, titleLabel);
        return card;
    }

    private VBox createSalesChart() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        container.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        container.setMinSize(500, 350);

        Label title = new Label("فروش روزانه (هفته اخیر)");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("روزهای هفته");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("مبلغ (هزار تومان)");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(null);
        chart.setLegendVisible(false);
        chart.setCategoryGap(20);
        chart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        
        LocalDate today = LocalDate.now();
        Random random = new Random();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("fa"));
            int sales = 150 + random.nextInt(250); 

            series.getData().add(new XYChart.Data<>(dayName, sales));
        }

        chart.getData().add(series);

        
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: #2196F3;");
                }
            });
        }

        container.getChildren().addAll(title, chart);
        return container;
    }

    private VBox createTopFoodsChart() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        container.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        container.setMinSize(400, 350);

        Label title = new Label("پرفروش‌ترین غذاها");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        Map<Food, Integer> foodSales = foodRepo.getAllFoods().stream()
                .collect(Collectors.toMap(
                        food -> food,
                        food -> 50 + new Random().nextInt(200) 
                ));

        List<Map.Entry<Food, Integer>> topFoods = foodSales.entrySet().stream()
                .sorted(Map.Entry.<Food, Integer>comparingByValue().reversed())
                .limit(5)
                .toList();

        
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (Map.Entry<Food, Integer> entry : topFoods) {
            pieData.add(new PieChart.Data(
                    entry.getKey().getName() + " (" + entry.getValue() + ")",
                    entry.getValue()
            ));
        }

        PieChart pieChart = new PieChart(pieData);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(false);
        pieChart.setTitle(null);
        pieChart.setStartAngle(90);

        
        String[] colors = {"#4CAF50", "#2196F3", "#FFC107", "#E91E63", "#9C27B0"};
        int colorIndex = 0;
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setStyle("-fx-pie-color: " + colors[colorIndex++] + ";");
        }

        container.getChildren().addAll(title, pieChart);
        return container;
    }
}
