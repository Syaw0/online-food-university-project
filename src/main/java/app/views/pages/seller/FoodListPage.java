package app.views.pages.seller;

import app.mock.FoodRepo;
import app.models.Food;
import app.models.Restaurant;
import app.states.StateManager;
import app.views.component.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class FoodListPage extends VBox {
    private final TableView<Food> table = new TableView<>();
    private final ObservableList<Food> foodData = FXCollections.observableArrayList();
    private final FoodRepo foodRepo = new FoodRepo();
    private final Restaurant currentRestaurant;

    public FoodListPage() {
        currentRestaurant = StateManager.getInstance().restaurantState.getCurrentRestaurant();
        initializeUI();
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);

        // Title and add button
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Typography title = new Typography("لیست غذاها", Typography.Variant.H1);
        ButtonComponent addButton = new ButtonComponent("افزودن غذا جدید", ButtonComponent.Variation.CONTAINED);
        addButton.setOnAction(e -> showFoodDialog(null));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title,spacer, addButton);

        // Create table columns
        TableColumn<Food, String> nameCol = new TableColumn<>("نام");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(120);

        TableColumn<Food, String> descCol = new TableColumn<>("توضیح");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(200);

        TableColumn<Food, String> priceCol = new TableColumn<>("قیمت");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(80);

        TableColumn<Food, String> stockCol = new TableColumn<>("موجودی");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockCol.setPrefWidth(70);

        TableColumn<Food, String> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final ButtonComponent viewBtn = new ButtonComponent("مشاهده", ButtonComponent.Variation.TEXT);
            private final ButtonComponent editBtn = new ButtonComponent("ویرایش", ButtonComponent.Variation.TEXT);
            private final ButtonComponent deleteBtn = new ButtonComponent("حذف", ButtonComponent.Variation.TEXT);
            private final HBox container = new HBox(5, viewBtn, editBtn, deleteBtn);

            {
                container.setAlignment(Pos.CENTER);

                viewBtn.setOnAction(event -> {
                    Food food = getTableView().getItems().get(getIndex());
                    showFoodDetail(food);
                });

                editBtn.setOnAction(event -> {
                    Food food = getTableView().getItems().get(getIndex());
                    showFoodDialog(food);
                });

                deleteBtn.setOnAction(event -> {
                    Food food = getTableView().getItems().get(getIndex());
                    deleteFood(food);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
        actionsCol.setPrefWidth(200);

        // Add columns to table
        table.getColumns().addAll(nameCol, descCol, priceCol, stockCol, actionsCol);
        table.setItems(foodData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Load initial data
        refreshTable();

        getChildren().addAll(header, table);
    }

    private void showFoodDetail(Food food) {
        FoodDetailDialog dialog = new FoodDetailDialog(food);
        dialog.showAndWait();
    }

    private void showFoodDialog(Food food) {
        FoodDialog dialog = new FoodDialog(food, currentRestaurant.getId());
        Optional<Food> result = dialog.showAndWait();

        result.ifPresent(updatedFood -> {
            if (food == null) {
                // Add new food
                foodRepo.addFood(updatedFood);
                showSuccessAlert("غذای جدید با موفقیت اضافه شد");
            } else {
                // Update existing food
                foodRepo.updateFood(updatedFood);
                showSuccessAlert("اطلاعات غذا با موفقیت به‌روزرسانی شد");
            }
            refreshTable();
        });
    }

    private void deleteFood(Food food) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تأیید حذف");
        alert.setHeaderText("آیا از حذف این غذا مطمئن هستید؟");
        alert.setContentText(food.getName() + " - این عمل قابل بازگشت نیست");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            foodRepo.removeFood(food.getId());
            showSuccessAlert("غذا با موفقیت حذف شد");
            refreshTable();
        }
    }

    private void refreshTable() {
        foodData.clear();
        foodData.addAll(foodRepo.findByRestaurantId(currentRestaurant.getId()));
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
