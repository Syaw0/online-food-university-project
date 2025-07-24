package app.views.pages.admin;

import app.views.component.Typography;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TicketListPage extends VBox {

    public enum TicketStatus {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED
    }

    public enum TicketPriority {
        HIGH, MEDIUM, LOW
    }

    public static class Ticket {
        private final String id;
        private final String subject;
        private final String customerName;
        private final String date;
        private final TicketStatus status;
        private final TicketPriority priority;

        public Ticket(String id, String subject, String customerName,
                      String date, TicketStatus status, TicketPriority priority) {
            this.id = id;
            this.subject = subject;
            this.customerName = customerName;
            this.date = date;
            this.status = status;
            this.priority = priority;
        }

        public String getId() { return id; }
        public String getSubject() { return subject; }
        public String getCustomerName() { return customerName; }
        public String getDate() { return date; }
        public TicketStatus getStatus() { return status; }
        public TicketPriority getPriority() { return priority; }

        public String getStatusPersian() {
            return switch (status) {
                case OPEN -> "باز";
                case IN_PROGRESS -> "در دست بررسی";
                case RESOLVED -> "حل شده";
                case CLOSED -> "بسته شده";
            };
        }

        public String getPriorityPersian() {
            return switch (priority) {
                case HIGH -> "بالا";
                case MEDIUM -> "متوسط";
                case LOW -> "پایین";
            };
        }
    }

    private final TableView<Ticket> table = new TableView<>();
    private final ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
    private final Random random = new Random();

    private final List<String> fakeSubjects = Arrays.asList(
            "مشکل در پرداخت سفارش",
            "آیتم گمشده در سفارش",
            "مشکل در ثبت سفارش",
            "سفارش با تأخیر تحویل داده شد",
            "محصول نادرست تحویل داده شد",
            "درخواست بازگشت وجه",
            "مشکل در حساب کاربری",
            "سفارش لغو شده",
            "مشکل در تخفیف",
            "سؤال درباره منو"
    );

    private final List<String> fakeNames = Arrays.asList(
            "محمد حسینی", "فاطمه محمدی", "علی رضایی",
            "زهرا کریمی", "حسین احمدی", "نرگس جعفری"
    );

    public TicketListPage() {
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(20));
        setBackground(new Background(new BackgroundFill(Color.web("#f7f9fc"), CornerRadii.EMPTY, Insets.EMPTY)));

        
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Typography title = new Typography("لیست تیکت‌های پشتیبانی", Typography.Variant.H1);

        
        HBox filters = createFilterControls();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer, filters);

        
        setupTable();

        
        Button createButton = new Button("ایجاد تیکت جدید");
        createButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");

        getChildren().addAll(header, table, createButton);
        loadTicketData();
    }

    private HBox createFilterControls() {
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);

        
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("همه", "باز", "در دست بررسی", "حل شده", "بسته شده");
        statusCombo.setValue("همه");
        statusCombo.setPromptText("وضعیت");
        statusCombo.setStyle("-fx-font-size: 14;");

        
        ComboBox<String> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll("همه", "بالا", "متوسط", "پایین");
        priorityCombo.setValue("همه");
        priorityCombo.setPromptText("اولویت");
        priorityCombo.setStyle("-fx-font-size: 14;");

        
        TextField searchField = new TextField();
        searchField.setPromptText("جستجوی تیکت...");
        searchField.setStyle("-fx-font-size: 14; -fx-pref-width: 200;");

        filterBox.getChildren().addAll(searchField, priorityCombo, statusCombo);
        return filterBox;
    }

    private void setupTable() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("هیچ تیکتی یافت نشد"));
        table.setStyle("-fx-font-size: 14px;");

        
        TableColumn<Ticket, String> idCol = new TableColumn<>("شناسه");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(80);

        
        TableColumn<Ticket, String> subjectCol = new TableColumn<>("موضوع");
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subjectCol.setPrefWidth(250);

        
        TableColumn<Ticket, String> customerCol = new TableColumn<>("مشتری");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerCol.setPrefWidth(150);

        
        TableColumn<Ticket, String> dateCol = new TableColumn<>("تاریخ");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(120);

        
        TableColumn<Ticket, String> statusCol = new TableColumn<>("وضعیت");
        statusCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getStatusPersian()
                )
        );
        statusCol.setPrefWidth(120);
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status) {
                        case "باز" -> setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;");
                        case "در دست بررسی" -> setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
                        case "حل شده" -> setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                        case "بسته شده" -> setStyle("-fx-text-fill: #9E9E9E; -fx-font-weight: bold;");
                    }
                }
            }
        });

        
        TableColumn<Ticket, String> priorityCol = new TableColumn<>("اولویت");
        priorityCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getPriorityPersian()
                )
        );
        priorityCol.setPrefWidth(100);
        priorityCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(priority);
                    switch (priority) {
                        case "بالا" -> setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;");
                        case "متوسط" -> setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
                        case "پایین" -> setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    }
                }
            }
        });

        
        TableColumn<Ticket, Void> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setPrefWidth(150);
        actionsCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Ticket, Void> call(TableColumn<Ticket, Void> param) {
                return new TableCell<>() {
                    private final Button viewBtn = new Button("مشاهده");
                    private final Button closeBtn = new Button("بستن");
                    private final HBox container = new HBox(5);

                    {
                        container.setAlignment(Pos.CENTER);
                        viewBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        closeBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

                        viewBtn.setOnAction(event -> {
                            Ticket ticket = getTableView().getItems().get(getIndex());
                            viewTicket(ticket);
                        });

                        closeBtn.setOnAction(event -> {
                            Ticket ticket = getTableView().getItems().get(getIndex());
                            closeTicket(ticket);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Ticket ticket = getTableView().getItems().get(getIndex());
                            container.getChildren().clear();
                            container.getChildren().add(viewBtn);

                            
                            if (ticket.getStatus() == TicketStatus.OPEN ||
                                    ticket.getStatus() == TicketStatus.IN_PROGRESS) {
                                container.getChildren().add(closeBtn);
                            }

                            setGraphic(container);
                        }
                    }
                };
            }
        });

        table.getColumns().addAll(idCol, subjectCol, customerCol, dateCol, statusCol, priorityCol, actionsCol);
    }

    private void loadTicketData() {
        ticketData.clear();

        
        for (int i = 1; i <= 50; i++) {
            String subject = fakeSubjects.get(random.nextInt(fakeSubjects.size()));
            String customer = fakeNames.get(random.nextInt(fakeNames.size()));
            String date = generateRandomDate();
            TicketStatus status = TicketStatus.values()[random.nextInt(TicketStatus.values().length)];
            TicketPriority priority = TicketPriority.values()[random.nextInt(TicketPriority.values().length)];

            ticketData.add(new Ticket(
                    "T-" + String.format("%04d", i),
                    subject,
                    customer,
                    date,
                    status,
                    priority
            ));
        }

        table.setItems(ticketData);
    }

    private String generateRandomDate() {
        LocalDate date = LocalDate.now()
                .minusDays(random.nextInt(30))
                .minusMonths(random.nextInt(6));

        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd", new Locale("fa")));
    }

    private void viewTicket(Ticket ticket) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("مشاهده تیکت #" + ticket.getId());
        alert.setHeaderText(ticket.getSubject());
        alert.setContentText(
                "مشتری: " + ticket.getCustomerName() + "\n" +
                        "تاریخ: " + ticket.getDate() + "\n" +
                        "وضعیت: " + ticket.getStatusPersian() + "\n" +
                        "اولویت: " + ticket.getPriorityPersian() + "\n\n" +
                        "متن تیکت:\n\n" +
                        "سلام،\n" +
                        "با مشکل در سفارش شماره #ORD-1234 مواجه شدم. آیتم‌های سفارش تحویل داده شده " +
                        "با موارد درخواستی مطابقت ندارد. لطفاً راهنمایی بفرمایید.\n\n" +
                        "با تشکر\n" +
                        ticket.getCustomerName()
        );
        alert.showAndWait();
    }

    private void closeTicket(Ticket ticket) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("بستن تیکت");
        confirmation.setHeaderText("آیا مایل به بستن تیکت #" + ticket.getId() + " هستید؟");
        confirmation.setContentText("این عملیات قابل بازگشت نیست.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                
                ticketData.remove(ticket);

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("تیکت بسته شد");
                success.setHeaderText(null);
                success.setContentText("تیکت #" + ticket.getId() + " با موفقیت بسته شد.");
                success.showAndWait();
            }
        });
    }
}
