package app.views.pages.buyer;

import app.mock.OrderRepo;
import app.models.Food;
import app.models.Order;
import app.views.component.ReceiptDialog;
import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class BuyerCompleteOrderListPage extends VBox {
    private final TableView<Order> table = new TableView<>();
    private final ObservableList<Order> orderData = FXCollections.observableArrayList();
    private final OrderRepo orderRepo = new OrderRepo();
    private final Map<String, String> fakeRestaurantNames = new HashMap<>();

    public BuyerCompleteOrderListPage() {
        initializeFakeRestaurantNames();
        initializeUI();
    }

    private void initializeFakeRestaurantNames() {
        
        fakeRestaurantNames.put("1", "رستوران پدیده");
        fakeRestaurantNames.put("2", "رستوران الماس");
        fakeRestaurantNames.put("3", "رستوران بهشت");
        fakeRestaurantNames.put("4", "رستوران طلایی");
        fakeRestaurantNames.put("5", "رستوران ستاره");
    }


    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);

        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        Typography title = new Typography("سفارشات تحویل داده شده", Typography.Variant.H1);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        
        TableColumn<Order, String> idCol = new TableColumn<>("شماره سفارش");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        
        TableColumn<Order, String> restaurantCol = new TableColumn<>("رستوران");
        restaurantCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Order order = getTableRow().getItem();
                    String restaurantId = order.getId().charAt(0) + "";
                    setText(fakeRestaurantNames.getOrDefault(restaurantId, "رستوران ناشناس"));
                }
            }
        });
        restaurantCol.setPrefWidth(150);

        
        TableColumn<Order, Integer> itemsCol = new TableColumn<>("تعداد غذاها");
        itemsCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(
                        cellData.getValue().getFoodItems().size()
                ).asObject()
        );
        itemsCol.setPrefWidth(100);

        
        TableColumn<Order, String> totalCol = new TableColumn<>("مبلغ کل");
        totalCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Order order = getTableRow().getItem();
                    
                    int subtotal = 0;
                    for (Order.FoodItem itemObj : order.getFoodItems()) {
                        Food food = itemObj.getFood();
                        subtotal += Integer.parseInt(food.getPrice()) * itemObj.getQuantity();
                    }
                    int tax = (int) (subtotal * 0.1);
                    int delivery = 15000;
                    int total = subtotal + tax + delivery;
                    setText(total + " تومان");
                }
            }
        });
        totalCol.setPrefWidth(120);

        
        TableColumn<Order, String> statusCol = new TableColumn<>("وضعیت");
        statusCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().statusToPersian()
                )
        );
        statusCol.setPrefWidth(150);

        
        TableColumn<Order, Void> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setPrefWidth(100);
        actionsCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button detailBtn = new Button("مشاهده");
                    {
                        detailBtn.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            showOrderDetails(order);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(detailBtn);
                        }
                    }
                };
            }
        });

        table.getColumns().addAll(idCol, restaurantCol, itemsCol, totalCol, statusCol, actionsCol);
        table.setItems(orderData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        refreshTable();
        getChildren().addAll(header, table);
    }

    private void showOrderDetails(Order order) {
        
        Map<Food, Integer> cartItems = new HashMap<>();
        for (Order.FoodItem item : order.getFoodItems()) {
            cartItems.put(item.getFood(), item.getQuantity());
        }

        
        int subtotal = 0;
        for (Order.FoodItem item : order.getFoodItems()) {
            Food food = item.getFood();
            subtotal += Integer.parseInt(food.getPrice()) * item.getQuantity();
        }

        int tax = (int) (subtotal * 0.1); 
        int delivery = 15000; 
        int total = subtotal + tax + delivery;

        
        ReceiptDialog dialog = new ReceiptDialog(cartItems, subtotal, tax, delivery, total, true);
        dialog.showAndWait();
    }

    private void refreshTable() {
        orderData.clear();
        orderData.addAll(orderRepo.getDeliveredOrders());
    }
}
