package app.views.pages.seller;

import app.mock.OrderRepo;
import app.models.Order;
import app.views.component.ButtonComponent;
import app.views.component.OrderDetailDialog;
import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PendingOrderListPage extends VBox {
    private final TableView<Order> table = new TableView<>();
    private final ObservableList<Order> orderData = FXCollections.observableArrayList();
    private final OrderRepo orderRepo = new OrderRepo();

    public PendingOrderListPage() {
        initializeUI();
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);

        // Title
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Typography title = new Typography("سفارشات در انتظار تأیید", Typography.Variant.H1);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer);

        // Create table columns
        TableColumn<Order, String> idCol = new TableColumn<>("شماره سفارش");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        TableColumn<Order, String> nameCol = new TableColumn<>("مشتری");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        nameCol.setPrefWidth(150);

        TableColumn<Order, String> phoneCol = new TableColumn<>("تلفن");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        phoneCol.setPrefWidth(120);

        TableColumn<Order, String> itemsCol = new TableColumn<>("تعداد آیتم‌ها");
        itemsCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(cellData.getValue().getFoodItems().size())
                )
        );
        itemsCol.setPrefWidth(100);

        // Actions column with buttons
        TableColumn<Order, Void> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setPrefWidth(250); // Increased width for 3 buttons
        actionsCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final ButtonComponent viewBtn = new ButtonComponent("مشاهده", ButtonComponent.Variation.TEXT);
                    private final ButtonComponent acceptBtn = new ButtonComponent("تأیید", ButtonComponent.Variation.CONTAINED);
                    private final ButtonComponent rejectBtn = new ButtonComponent("رد", ButtonComponent.Variation.OUTLINED);
                    private final HBox container = new HBox(5, viewBtn, acceptBtn, rejectBtn);

                    {
                        container.setAlignment(Pos.CENTER);

                        // Set button colors
                        acceptBtn.setButtonColors("#4CAF50", "#FFFFFF", "#4CAF50");
                        rejectBtn.setButtonColors("#F44336", "#FFFFFF", "#F44336");
                        viewBtn.setButtonColors("#2196F3", "#FFFFFF", "#2196F3");

                        viewBtn.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            showOrderDetails(order);
                        });

                        acceptBtn.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            handleAcceptOrder(order);
                        });

                        rejectBtn.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            handleRejectOrder(order);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || orderData.isEmpty()) {
                            setGraphic(null);
                        } else {
                            setGraphic(container);
                        }
                    }
                };
            }
        });


        // Add columns to table
        table.getColumns().addAll(idCol, nameCol, phoneCol, itemsCol, actionsCol);
        table.setItems(orderData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Load initial data
        refreshTable();

        getChildren().addAll(header, table);
    }

    // Add this method to show order details
    private void showOrderDetails(Order order) {
        OrderDetailDialog dialog = new OrderDetailDialog(order);
        dialog.showAndWait();
    }

    private void refreshTable() {
        orderData.clear();
        orderData.addAll(orderRepo.getPendingOrders());
    }

    private void handleAcceptOrder(Order order) {
        order.setStatus(Order.Status.ACCEPTED_BY_SELLER);
        orderRepo.updateOrder(order);
        orderData.remove(order);
    }

    private void handleRejectOrder(Order order) {
        order.setStatus(Order.Status.REJECTED_BY_SELLER);
        orderRepo.updateOrder(order);
        orderData.remove(order);
    }
}
