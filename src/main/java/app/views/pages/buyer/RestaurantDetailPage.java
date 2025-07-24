package app.views.pages.buyer;

import app.mock.FoodRepo;
import app.models.Food;
import app.models.Restaurant;
import app.views.component.CardComponent;
import app.views.component.FilterComponent;
import app.views.component.FoodDetailDialog;
import app.views.component.Typography;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class RestaurantDetailPage extends VBox {
    private final Restaurant restaurant;
    private final FoodRepo foodRepo = new FoodRepo();
    private final ObservableList<Food> foodData = FXCollections.observableArrayList();
    private final FilteredList<Food> filteredFoods;
    private TextField foodSearchField;
    private ComboBox<Double> foodRatingFilter;
    private ComboBox<String> foodCategoryFilter;
    private final VBox foodCardsContainer = new VBox(20);

    public RestaurantDetailPage(Restaurant restaurant) {
        this.restaurant = restaurant;
        filteredFoods = new FilteredList<>(foodData);
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(30);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.web("#f7f7f7"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox content = new VBox(30);
        content.setPadding(new Insets(10));

        
        VBox infoSection = createInfoSection();
        content.getChildren().add(infoSection);

        
        VBox foodSection = createFoodSection();
        content.getChildren().add(foodSection);

        scrollPane.setContent(content);
        getChildren().add(scrollPane);

        
        refreshFoodData();
    }

    private VBox createInfoSection() {
        VBox infoSection = new VBox(15);
        infoSection.setPadding(new Insets(20));
        infoSection.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

        
        ImageView imageView = new ImageView();
        try {
            String fullPath = getClass().getResource(restaurant.getLogo()) != null ?
                    Objects.requireNonNull(getClass().getResource(restaurant.getLogo())).toExternalForm() :
                    "file:" + System.getProperty("user.dir") + restaurant.getLogo();
            Image image = new Image(fullPath);
            imageView.setImage(image);
            imageView.setFitWidth(600);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(false);
        } catch (Exception e) {
            StackPane placeholder = new StackPane(new Label("تصویر موجود نیست"));
            placeholder.setStyle("-fx-background-color: #e0e0e0; -fx-min-width: 600; -fx-min-height: 300;");
            infoSection.getChildren().add(placeholder);
        }
        infoSection.getChildren().add(imageView);

        
        Typography nameLabel = new Typography(restaurant.getName(), Typography.Variant.H2);
        nameLabel.setAlignment(Pos.CENTER_RIGHT);
        infoSection.getChildren().add(nameLabel);

        
        HBox ratingBox = new HBox(5);
        ratingBox.setAlignment(Pos.CENTER_RIGHT);
        Label ratingIcon = new Label("★");
        ratingIcon.setStyle("-fx-text-fill: #FFD700; -fx-font-weight: bold;");
        Label ratingLabel = new Label(restaurant.getTotalRate());
        ratingBox.getChildren().addAll(ratingIcon, ratingLabel);
        infoSection.getChildren().add(ratingBox);

        
        Label descriptionLabel = new Label(restaurant.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(600);
        descriptionLabel.setStyle("-fx-font-size: 14;");
        infoSection.getChildren().add(descriptionLabel);

        return infoSection;
    }

    private VBox createFoodSection() {
        VBox foodSection = new VBox(20);
        foodSection.setPadding(new Insets(20));
        foodSection.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

        
        Typography sectionTitle = new Typography("منو غذاها", Typography.Variant.H2);
        foodSection.getChildren().add(sectionTitle);

        
        HBox filterBox = new HBox(20);
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setPadding(new Insets(10, 0, 20, 0));

        
        foodSearchField = new TextField();
        foodSearchField.setPromptText("جستجوی غذا...");
        foodSearchField.setPrefWidth(250);
        foodSearchField.textProperty().addListener((obs, oldText, newText) -> filterFoods());

        
        foodCategoryFilter = new ComboBox<>();
        foodCategoryFilter.setPromptText("دسته‌بندی");
        foodCategoryFilter.getItems().addAll("همه", "غذای اصلی", "پیش غذا", "فست فود", "دسر", "نوشیدنی");
        foodCategoryFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterFoods());

        
        foodRatingFilter = new ComboBox<>();
        foodRatingFilter.setPromptText("امتیاز");
        foodRatingFilter.getItems().addAll(0.0, 3.0, 3.5, 4.0, 4.5);
        foodRatingFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterFoods());

        
        filterBox.getChildren().addAll(
                new FilterComponent("جستجو", foodSearchField),
                new FilterComponent("دسته‌بندی", foodCategoryFilter),
                new FilterComponent("امتیاز", foodRatingFilter)
        );
        foodSection.getChildren().add(filterBox);

        
        foodCardsContainer.setPadding(new Insets(10));
        foodCardsContainer.setAlignment(Pos.TOP_CENTER);

        
        filteredFoods.addListener((InvalidationListener) observable -> updateFoodCards());

        
        ScrollPane scrollPane = new ScrollPane(foodCardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        foodSection.getChildren().add(scrollPane);

        return foodSection;
    }

    private void updateFoodCards() {
        foodCardsContainer.getChildren().clear();
        for (Food food : filteredFoods) {
            CardComponent card = createFoodCard(food);
            foodCardsContainer.getChildren().add(card);
        }
    }

    private CardComponent createFoodCard(Food food) {
        CardComponent card = new CardComponent();
        card.setImage(food.getImage());
        card.setTitle(food.getName());
        card.setRating(food.getTotalRate());

        
        String description = food.getDescription();
        if (description.length() > 80) {
            description = description.substring(0, 80) + "...";
        }
        String priceText = food.getPrice() + " تومان";

                card.setDescription( priceText);

        card.setAction("افزودن به سبد", () -> {
            System.out.println("Adding to cart: " + food.getName());
            showSuccessAlert("با موفقیت به سبد خرید اضافه شد.");
        });

        card.setAction2("مشاهده اطلاعات",()->{
            FoodDetailDialog dialog = new FoodDetailDialog(food, true); 
            dialog.showAndWait();
        });

        return card;
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void filterFoods() {
        filteredFoods.setPredicate(food -> {
            
            String searchText = foodSearchField.getText().toLowerCase();
            if (!searchText.isEmpty() &&
                    !food.getName().toLowerCase().contains(searchText) &&
                    !food.getDescription().toLowerCase().contains(searchText)) {
                return false;
            }

            
            String selectedCategory = foodCategoryFilter.getValue();
            if (selectedCategory != null && !selectedCategory.equals("همه") &&
                    !food.getCategoryId().equals(selectedCategory)) {
                return false;
            }

            
            Double minRating = foodRatingFilter.getValue();
            if (minRating != null && minRating > 0) {
                double foodRating = Double.parseDouble(food.getTotalRate());
                if (foodRating < minRating) {
                    return false;
                }
            }

            return true;
        });
    }

    private void refreshFoodData() {
        foodData.clear();
        foodData.addAll(foodRepo.findByRestaurantId(restaurant.getId()));
    }
}
