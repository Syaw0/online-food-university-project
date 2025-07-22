package app.views.form;

import javafx.geometry.Pos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import app.views.component.ButtonComponent;
import app.views.component.Typography;
import app.views.component.TextFieldComponent;
import app.views.component.PasswordFieldComponent;

import java.util.List;
import java.util.ArrayList;

public class LoginForm extends VBox {
    public final TextFieldComponent phoneField;
    public final PasswordFieldComponent passField;
    public final Typography errorLabel;
    public final ButtonComponent loginBtn;
    public final ButtonComponent switchToRegister;

    public LoginForm(Runnable onSwitchToRegister) {
        super(10);
        setAlignment(Pos.CENTER_LEFT);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setStyle("-fx-padding: 40;");

        phoneField = new TextFieldComponent();
        phoneField.setPromptText("شماره تماس");

        passField = new PasswordFieldComponent();
        passField.setPromptText("رمز عبور");

        errorLabel = new Typography("", Typography.Variant.LABEL);
        errorLabel.setStyle("-fx-text-fill: red;");

        loginBtn = new ButtonComponent("ورود", ButtonComponent.Variation.CONTAINED);
        setupLoginValidation();

        switchToRegister = new ButtonComponent("حساب ندارید؟ ثبت‌نام", ButtonComponent.Variation.TEXT);
        switchToRegister.getStyleClass().add("text-button");
        switchToRegister.setOnAction(e -> onSwitchToRegister.run());

        HBox buttonRow = new HBox(10);
        buttonRow.setPadding(new Insets(10));
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        buttonRow.getChildren().addAll(loginBtn, switchToRegister);

        getChildren().addAll(
            new Typography("ورود به حساب", Typography.Variant.H2){{
                setStyle("-fx-padding: 0 0 30 0;");
            }},
            phoneField,
            passField,
            errorLabel,
            buttonRow
        );
    }

    private void setupLoginValidation() {
        loginBtn.setOnAction(e -> {
            errorLabel.setText("");
            List<String> errors = validateLogin(phoneField.getText().trim(), passField.getText().trim());
            if (!errors.isEmpty()) {
                errorLabel.setText(String.join("\n", errors));
            } else {
                System.out.println("Validated! ارسال اطلاعات به API...");
            }
        });
    }

    private List<String> validateLogin(String phone, String pass) {
        List<String> errors = new ArrayList<>();
        if (pass.isEmpty()) errors.add("پسورد نمی‌تواند خالی باشد.");
        if (phone.isEmpty()) {
            errors.add("شماره تماس نمی‌تواند خالی باشد.");
        } else if (!phone.matches("^09\\d{9}$")) {
            errors.add("شماره تماس باید با 09 شروع شده و 11 رقم باشد.");
        }
        return errors;
    }
}