package app.views.pages.admin;

import app.mock.UserRepo;
import app.models.User;
import app.models.UserType;
import app.views.component.Typography;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.*;

public class AdminUserListPage extends VBox {
    private final TableView<User> table = new TableView<>();
    private final ObservableList<User> userData = FXCollections.observableArrayList();
    private final UserRepo userRepo = new UserRepo();
    private final Random random = new Random();
    private final List<String> fakeNames = List.of(
            "محمد حسینی", "فاطمه محمدی", "علی رضایی", "زهرا کریمی", "حسین احمدی",
            "نرگس جعفری", "امیر کاظمی", "سارا موسوی", "مهدی نجفی", "پریسا قاسمی",
            "حمید رحیمی", "مریم صادقی", "رضا زمانی", "لیلا محمودی", "حسن علیخانی"
    );
    private final List<String> fakePhones = List.of(
            "09123456789", "09123456780", "09123456781", "09123456782", "09123456783",
            "09123456784", "09123456785", "09123456786", "09123456787", "09123456788"
    );
    private final List<String> fakeEmails = List.of(
            "user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com",
            "user6@example.com", "user7@example.com", "user8@example.com", "user9@example.com", "user10@example.com"
    );
    private final Set<String> approvedUsers = new HashSet<>();

    public AdminUserListPage() {
        initializeUI();
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setSpacing(20);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);
        Typography title = new Typography("لیست کاربران", Typography.Variant.H1);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        TableColumn<User, String> nameCol = new TableColumn<>("نام کامل");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameCol.setPrefWidth(200);

        TableColumn<User, String> phoneCol = new TableColumn<>("تلفن");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setPrefWidth(150);

        TableColumn<User, String> emailCol = new TableColumn<>("ایمیل");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);

        TableColumn<User, String> typeCol = new TableColumn<>("نوع کاربر");
        typeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().roleToPersian())
        );
        typeCol.setPrefWidth(150);

        
        TableColumn<User, Void> actionsCol = new TableColumn<>("عملیات");
        actionsCol.setPrefWidth(200);
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button approveBtn = new Button("تایید");
            private final Button removeBtn = new Button("حذف");
            private final HBox container = new HBox(10);

            {
                container.setAlignment(Pos.CENTER);
                approveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                removeBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

                approveBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleApproveUser(user);
                });

                removeBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleRemoveUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    container.getChildren().clear();
                    User user = getTableView().getItems().get(getIndex());

                    
                    if (user.getUserType() != UserType.ADMIN &&
                            !approvedUsers.contains(user.getId())) {
                        container.getChildren().add(approveBtn);
                    }

                    container.getChildren().add(removeBtn);
                    setGraphic(container);
                }
            }
        });

        table.getColumns().addAll(nameCol, phoneCol, emailCol, typeCol, actionsCol);
        table.setItems(userData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getChildren().addAll(header, table);
        refreshTable();
    }

    private void refreshTable() {
        userData.clear();
        userData.addAll(generateFakeUsers());
    }

    private List<User> generateFakeUsers() {
        List<User> fakeUsers = userRepo.getAllUsers();

        
        for (int i = 0; i < 15; i++) {
            UserType type = UserType.values()[i % UserType.values().length];
            fakeUsers.add(new User(
                    String.valueOf(i + 100),
                    fakeNames.get(i % fakeNames.size()),
                    fakePhones.get(i % fakePhones.size()),
                    "password123",
                    userRepo.giveFakeAddress(),
                    fakeEmails.get(i % fakeEmails.size()),
                    type,
                    "/assets/images/default-profile.png",
                    (type == UserType.SELLER || type == UserType.DELIVERY) ? "IR" + (1000000 + i) : "",
                    random.nextBoolean()
            ));
        }

        return fakeUsers;
    }

    private void handleApproveUser(User user) {
        approvedUsers.add(user.getId());

        
        User prev = table.getItems().set(table.getItems().indexOf(user), user);
        table.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("تایید کاربر");
        alert.setHeaderText(null);
        alert.setContentText("کاربر " + user.getFullName() + " با موفقیت تایید شد!");
        alert.showAndWait();
    }

    private void handleRemoveUser(User user) {
        userData.remove(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("حذف کاربر");
        alert.setHeaderText(null);
        alert.setContentText("کاربر " + user.getFullName() + " با موفقیت حذف شد!");
        alert.showAndWait();
    }
}
