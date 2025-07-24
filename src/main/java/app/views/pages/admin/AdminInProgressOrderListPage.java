package app.views.pages.admin;

import app.mock.OrderRepo;
import app.models.Order;
import app.models.Order.Status;
import app.views.component.DeliveryAssignmentDialog;
import app.views.component.OrderDetailDialog;
import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AdminInProgressOrderListPage extends VBox {
    private final TableView<Order> table = new TableView<>();
    private final ObservableList<Order> orderData = FXCollections.observableArrayList();
    private final OrderRepo orderRepo = new OrderRepo();

    public AdminInProgressOrderListPage() {
        initializeUI();
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);

        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        Typography title = new Typography("سفارشات در حال انجام", Typography.Variant.H1);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        
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

        
        TableColumn<Order, String> statusCol = new TableColumn<>("وضعیت");
        statusCol.setCellValueFactory(cellData -> {
            String status = cellData.getValue().statusToPersian();
            return new javafx.beans.property.SimpleStringProperty(status);
        });
        statusCol.setPrefWidth(150);

        
        TableColumn<Order, String> deliveryCol = new TableColumn<>("پیک");
        deliveryCol.setCellValueFactory(cellData -> {
            String deliveryName = cellData.getValue().getDeliveryName();
            return new javafx.beans.property.SimpleStringProperty(
                    (deliveryName != null && !deliveryName.isEmpty()) ? deliveryName : "تخصیص داده نشده"
            );
        });
        deliveryCol.setPrefWidth(150);

        
        TableColumn<Order, Void> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setPrefWidth(300);
        actionsCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                return new TableCell<>() {
                    private final Button viewBtn = new Button("مشاهده");
//                    private final Button statusBtn = new Button("تغییر وضعیت");
//                    private final Button assignBtn = new Button("تخصیص پیک");
                    private final HBox container = new HBox(5);

                    {
                        container.setAlignment(Pos.CENTER);

                        viewBtn.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            showOrderDetails(order);
                        });
//
//                        statusBtn.setOnAction(event -> {
//                            Order order = getTableView().getItems().get(getIndex());
//                            handleNextStatus(order);
//                        });
//
//                        assignBtn.setOnAction(event -> {
//                            Order order = getTableView().getItems().get(getIndex());
//                            handleAssignDelivery(order);
//                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        container.getChildren().clear();
                        container.getChildren().add(viewBtn);

                        if (empty || orderData.isEmpty()) {
                            setGraphic(null);
                        } else {
                            int index = getIndex();
                            if (index >= 0 && index < orderData.size()) {
                                Order order = orderData.get(index);
                                Status status = order.getStatus();

                                
//                                if (status == Status.ACCEPTED_BY_SELLER || status == Status.PREPARING) {
//                                    container.getChildren().add(statusBtn);
//
//                                    if (status == Status.ACCEPTED_BY_SELLER) {
//                                        statusBtn.setText("آماده سازی");
//                                        statusBtn.setDisable(false);
//                                    } else if (status == Status.PREPARING) {
//                                        statusBtn.setText("تحویل به پیک");
//
//                                        boolean noDelivery = order.getDeliveryName() == null || order.getDeliveryName().isEmpty();
//                                        statusBtn.setDisable(noDelivery);
//                                    }
//                                }
//
//
//                                if (order.getDeliveryName() == null || order.getDeliveryName().isEmpty()) {
//                                    container.getChildren().add(assignBtn);
//                                }
                            }
                            setGraphic(container);
                        }
                    }
                };
            }
        });

        table.getColumns().addAll(idCol, nameCol, phoneCol, itemsCol, statusCol, deliveryCol, actionsCol);
        table.setItems(orderData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        refreshTable();
        getChildren().addAll(header, table);
    }

    private void showOrderDetails(Order order) {
        OrderDetailDialog dialog = new OrderDetailDialog(order);
        dialog.showAndWait();
    }

    private void refreshTable() {
        orderData.clear();
        orderData.addAll(orderRepo.getInProgressOrders());
    }

    private void handleNextStatus(Order order) {
        Status currentStatus = order.getStatus();

        if (currentStatus == Status.ACCEPTED_BY_SELLER) {
            order.setStatus(Status.PREPARING);
            orderRepo.updateOrder(order);
            table.refresh();
        } else if (currentStatus == Status.PREPARING) {
            
            if (order.getDeliveryName() == null || order.getDeliveryName().isEmpty()) {
                showAlert("خطا در تغییر وضعیت", "برای تحویل سفارش به پیک، ابتدا پیک را تخصیص دهید");
                return;
            }

            order.setStatus(Status.RECEIVED_BY_DELIVERY);
            orderRepo.updateOrder(order);
            table.refresh();
        }
    }

    private void handleAssignDelivery(Order order) {
        DeliveryAssignmentDialog dialog = new DeliveryAssignmentDialog();
        dialog.showAndWait().ifPresent(deliveryPerson -> {
            if (deliveryPerson != null) {
                order.setDeliveryName(deliveryPerson.getName());
                orderRepo.updateOrder(order);
                table.refresh();
            }
        });
    }

    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
