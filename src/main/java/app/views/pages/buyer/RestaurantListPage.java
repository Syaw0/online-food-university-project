package app.views.pages.buyer;

import app.mock.RestaurantRepo;
import app.models.Restaurant;
import app.views.component.CardComponent;
import app.views.component.FilterComponent;
import app.views.component.Typography;
import app.views.layout.dashbaord.Main;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RestaurantListPage extends VBox {
    private final RestaurantRepo restaurantRepo = new RestaurantRepo();
    private final ObservableList<Restaurant> restaurantData = FXCollections.observableArrayList();
    private final FilteredList<Restaurant> filteredData;
    private final TextField searchField = new TextField();
    private final ComboBox<Double> ratingFilter = new ComboBox<>();
    private final ComboBox<String> categoryFilter = new ComboBox<>();
    private final VBox cardsContainer = new VBox(20);

    public RestaurantListPage() {
        filteredData = new FilteredList<>(restaurantData);
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(20));
        this.setBackground(new Background(new BackgroundFill(Color.web("#f7f7f7"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        Typography title = new Typography("رستوران‌های اطراف شما", Typography.Variant.H1);
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        
        HBox filterBox = new HBox(20);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(20));
        filterBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        
        searchField.setPromptText("جستجوی رستوران...");
        searchField.setPrefWidth(300);
        searchField.textProperty().addListener((obs, oldText, newText) -> filterRestaurants());

        
        ratingFilter.setPromptText("تعداد ستاره");
        ratingFilter.getItems().addAll(0.0, 3.0, 3.5, 4.0, 4.5);
        ratingFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterRestaurants());

        
        categoryFilter.setPromptText("دسته‌بندی غذاها");
        List<String> categories = Arrays.asList("همه", "فست فود", "ایرانی", "دریایی", "ایتالیایی", "چینی", "گیاهی");
        categoryFilter.getItems().addAll(categories);
        categoryFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterRestaurants());

        
        FilterComponent searchFilter = new FilterComponent("جستجو", searchField);
        FilterComponent ratingFilterComponent = new FilterComponent("امتیاز", ratingFilter);
        FilterComponent categoryFilterComponent = new FilterComponent("دسته‌بندی", categoryFilter);

        filterBox.getChildren().addAll(
                searchFilter,
                ratingFilterComponent,
                categoryFilterComponent
        );

        
        cardsContainer.setPadding(new Insets(20));
        cardsContainer.setAlignment(Pos.TOP_CENTER);

        
        filteredData.addListener((InvalidationListener) observable -> updateCards());

        
        ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        
        refreshData();

        
        getChildren().addAll(titleBox, filterBox, scrollPane);
    }

    private void updateCards() {
        cardsContainer.getChildren().clear();
        for (Restaurant restaurant : filteredData) {
            CardComponent card = createRestaurantCard(restaurant);
            cardsContainer.getChildren().add(card);
        }
    }

    private CardComponent createRestaurantCard(Restaurant restaurant) {
        CardComponent card = new CardComponent();

        
        card.setImage(restaurant.getLogo());

        
        card.setTitle(restaurant.getName());
        card.setRating(restaurant.getTotalRate());

        
        String description = restaurant.getDescription();
        if (description.length() > 20) {
            description = description.substring(0, 20) + "...";
        }


        card.setAction("مشاهده منو", () -> {
            System.out.println("Opening menu for: " + restaurant.getName());
            Main mainContent = Main.getInstance();
            mainContent.setContent(new RestaurantDetailPage(restaurant));
        });

        return card;
    }
    private void filterRestaurants() {
        filteredData.setPredicate(restaurant -> {
            
            String searchText = searchField.getText().toLowerCase();
            if (!searchText.isEmpty() &&
                    !restaurant.getName().toLowerCase().contains(searchText) &&
                    !restaurant.getDescription().toLowerCase().contains(searchText)) {
                return false;
            }

            
            Double minRating = ratingFilter.getValue();
            if (minRating != null && minRating > 0) {
                double restaurantRating = Double.parseDouble(restaurant.getTotalRate());
                if (restaurantRating < minRating) {
                    return false;
                }
            }

            
            String selectedCategory = categoryFilter.getValue();
            if (selectedCategory != null && !selectedCategory.equals("همه")) {
                List<String> categories = Arrays.asList("فست فود", "ایرانی", "دریایی", "ایتالیایی", "چینی", "گیاهی");
                String restaurantCategory = categories.get(new Random().nextInt(categories.size()));
                if (!restaurantCategory.equals(selectedCategory)) {
                    return false;
                }
            }

            return true;
        });
    }

    private void refreshData() {
        restaurantData.clear();
        restaurantData.addAll(restaurantRepo.getAllRestaurants());
    }
}
