package app.views.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import java.net.URL;

public class LogoComponent extends HBox {
    public LogoComponent() {
        this(60); // Default size 60x60 if not provided
    }

    public LogoComponent(double size) {
        super(10);
        setAlignment(Pos.CENTER_LEFT);
        URL logoUrl = getClass().getResource("/assets/images/logo.png");
        Typography appName = new Typography("", Typography.Variant.LABEL);
        if (logoUrl != null) {
            ImageView logo = new ImageView(new Image(logoUrl.toExternalForm()));
            logo.setFitHeight(size);
            logo.setFitWidth(size);
            logo.setPreserveRatio(false); // Use square sizing
            getChildren().addAll(logo, appName);
        } else {
            getChildren().add(appName);
        }
    }
}