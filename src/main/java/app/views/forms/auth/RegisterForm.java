package app.views.forms.auth;

import app.models.User;
import app.models.UserType;
import app.states.StateManager;
import app.utils.NavigationController;
import javafx.geometry.Pos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import app.views.component.TextFieldComponent;
import app.views.component.PasswordFieldComponent;
import app.views.component.ComboBoxComponent;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class RegisterForm extends VBox {
    public final TextFieldComponent fullNameField;
    public final TextFieldComponent phoneField;
    public final TextFieldComponent emailField;
    public final PasswordFieldComponent passField;
    public final TextFieldComponent addressField;
    public final Typography errorLabel;
    public final ButtonComponent registerBtn;
    public final ButtonComponent switchToLogin;
    public final ComboBoxComponent userTypeBox;
    private  final StateManager stateManger = StateManager.getInstance();
    private final NavigationController navControl=stateManger.getNavigationController();

    public RegisterForm(Runnable onSwitchToLogin) {
        super(10);
        setAlignment(Pos.CENTER_LEFT);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setStyle("-fx-padding: 40;");

        
        userTypeBox = new ComboBoxComponent("", "خریدار عادی", "رستوران دار", "پیک");
        userTypeBox.setValue("خریدار عادی");
  

        fullNameField = new TextFieldComponent();
        fullNameField.setPromptText("نام و نام خانوادگی");
        phoneField = new TextFieldComponent();
        phoneField.setPromptText("شماره تماس");
        emailField = new TextFieldComponent();
        emailField.setPromptText("ایمیل (اختیاری)");
        passField = new PasswordFieldComponent();
        passField.setPromptText("رمز عبور");
        addressField = new TextFieldComponent();
        addressField.setPromptText("آدرس");

        errorLabel = new Typography("", Typography.Variant.LABEL);
        errorLabel.setStyle("-fx-text-fill: red;");

        registerBtn = new ButtonComponent("ثبت‌نام", ButtonComponent.Variation.CONTAINED);
        setupRegisterValidation();

        switchToLogin = new ButtonComponent("حساب دارید؟ ورود", ButtonComponent.Variation.TEXT);
        switchToLogin.getStyleClass().add("text-button");
        switchToLogin.setOnAction(e -> onSwitchToLogin.run());

        HBox buttonRow = new HBox(10);
        buttonRow.setPadding(new Insets(10));
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        buttonRow.getChildren().addAll(registerBtn, switchToLogin);

        // Show/hide address based on type
        userTypeBox.setOnAction(e -> {
            String selected = userTypeBox.getValue();
            addressField.setVisible(!selected.equals("پیک"));
        });

        getChildren().addAll(
            new Typography("ثبت‌نام", Typography.Variant.H2){{
                setStyle("-fx-padding: 0 0 30 0;");
            }},
            fullNameField,
            phoneField,
            emailField,
            passField,
            addressField,
            userTypeBox,
            errorLabel,
            buttonRow
        );
    }

    private void setupRegisterValidation() {
        registerBtn.setOnAction(e -> {
            errorLabel.setText("");
            String selectedType = userTypeBox.getValue();
            List<String> errors = validateRegistration(
                fullNameField.getText().trim(),
                phoneField.getText().trim(),
                passField.getText().trim(),
                addressField.getText().trim(),
                selectedType
            );
            if (!errors.isEmpty()) {
                errorLabel.setText(String.join("\n", errors));
            } else {
                String newUserId = UUID.randomUUID().toString();
                UserType userType = selectedType =="خریدار عادی" ? UserType.BUYER: selectedType =="پیک"? UserType.DELIVERY: UserType.SELLER;
                User newUser = new User(newUserId,fullNameField.getText(),phoneField.getText(),passField.getText(),addressField.getText(),emailField.getText(),userType,"","",false);
                stateManger.userState.setCurrentUser(newUser);
                stateManger.userState.login(newUser);
                navControl.loginSuccess();
                System.out.println("Credential is correct. move to the dashboard..."+newUser);
                System.out.println("Validated! ارسال اطلاعات به API...");
            }
        });
    }

    private List<String> validateRegistration(String name, String phone, String pass, String address, String userType) {
        List<String> errors = new ArrayList<>();
        if (name.isEmpty()) errors.add("نام و نام خانوادگی نمی‌تواند خالی باشد.");
        if (pass.isEmpty()) errors.add("پسورد نمی‌تواند خالی باشد.");
        if (!userType.equals("پیک") && address.isEmpty()) errors.add("آدرس نمیتواند خالی باشد."); // Only check address for non-delivery
        if (phone.isEmpty()) {
            errors.add("شماره تماس نمی‌تواند خالی باشد.");
        } else if (!phone.matches("^09\\d{9}$")) {
            errors.add("شماره تماس باید با 09 شروع شده و 11 رقم باشد.");
        }
        return errors;
    }
}