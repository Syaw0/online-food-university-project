package app.views.component;

import app.models.Comment;
import app.models.Food;
import app.mock.CommentRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class FoodDetailDialog extends Dialog<Void> {
    public FoodDetailDialog(Food food) {
        getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setTitle("مشخصات غذا");
        setHeaderText("جزئیات کامل غذا");

        // Create image view
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(food.getImage()));
        } catch (Exception e) {
            imageView.setImage(new Image("file:src/main/resources/assets/images/default-food.png"));
        }
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("food-image");

        // Create detail grid
        GridPane grid = new GridPane();
        grid.getStyleClass().add("detail-grid");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(createLabel("نام:"), 0, 0);
        grid.add(createValueLabel(food.getName()), 1, 0);
        grid.add(createLabel("توضیحات:"), 0, 1);
        grid.add(createValueLabel(food.getDescription()), 1, 1);
        grid.add(createLabel("قیمت:"), 0, 2);
        grid.add(createValueLabel(food.getPrice()), 1, 2);
        grid.add(createLabel("موجودی:"), 0, 3);
        grid.add(createValueLabel(food.getStock()), 1, 3);
        grid.add(createLabel("دسته‌بندی:"), 0, 4);
        grid.add(createValueLabel(food.getCategoryId()), 1, 4);
        grid.add(createLabel("امتیاز:"), 0, 5);
        grid.add(createValueLabel(food.getTotalRate()), 1, 5);

        // Comments section
        Label commentsHeader = new Label("نظرات کاربران");
        commentsHeader.getStyleClass().add("comments-header");

        VBox commentsContainer = new VBox(15);
        commentsContainer.getStyleClass().add("comments-container");

        // Load comments
        CommentRepo commentRepo = new CommentRepo();
        List<Comment> comments = commentRepo.findByFoodId(food.getId());

        if (comments.isEmpty()) {
            Label noComments = new Label("هنوز نظری ثبت نشده است");
            noComments.getStyleClass().add("no-comments");
            commentsContainer.getChildren().add(noComments);
        } else {
            for (Comment comment : comments) {
                commentsContainer.getChildren().add(createCommentCard(comment));
            }
        }

        ScrollPane commentsScroll = new ScrollPane(commentsContainer);
        commentsScroll.setFitToWidth(true);
        commentsScroll.getStyleClass().add("comments-scroll");

        VBox commentsSection = new VBox(10, commentsHeader, commentsScroll);
        commentsSection.getStyleClass().add("comments-section");

        // Main layout
        VBox mainContent = new VBox(20, imageView, grid, commentsSection);
        mainContent.getStyleClass().add("main-content");
        mainContent.setPadding(new Insets(15));

        getDialogPane().setContent(mainContent);

        // Add close button
        ButtonType closeButton = new ButtonType("بستن", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(closeButton);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("detail-label");
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("detail-value");
        return label;
    }

    private VBox createCommentCard(Comment comment) {
        VBox card = new VBox(10);
        card.getStyleClass().add("comment-card");

        HBox header = new HBox(10);
        header.getStyleClass().add("comment-header");

        // User image
        ImageView userImage = new ImageView();
        try {
            if (comment.getPicture() != null && !comment.getPicture().isEmpty()) {
                userImage.setImage(new Image(comment.getPicture()));
            } else {
                userImage.setImage(new Image("file:src/main/resources/assets/images/default-user.png"));
            }
        } catch (Exception e) {
            userImage.setImage(new Image("file:src/main/resources/assets/images/default-user.png"));
        }
        userImage.setFitHeight(40);
        userImage.setFitWidth(40);
        userImage.setPreserveRatio(true);
        userImage.getStyleClass().add("user-image");

        // User info
        VBox userInfo = new VBox(5);
        Label userName = new Label("کاربر " + comment.getUserId());
        userName.getStyleClass().add("user-name");

        // Rating stars
        HBox ratingBox = new HBox(5);
        int stars = Integer.parseInt(comment.getStars());
        for (int i = 0; i < 5; i++) {
            Label star = new Label(i < stars ? "★" : "☆");
            star.getStyleClass().add(i < stars ? "star-filled" : "star-empty");
            ratingBox.getChildren().add(star);
        }

        userInfo.getChildren().addAll(userName, ratingBox);
        userInfo.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(userInfo, Priority.ALWAYS);

        header.getChildren().addAll(userInfo, userImage);

        // Comment content
        Label title = new Label(comment.getTitle());
        title.getStyleClass().add("comment-title");
        title.setWrapText(true);

        Label description = new Label(comment.getDescription());
        description.getStyleClass().add("comment-description");
        description.setWrapText(true);

        card.getChildren().addAll(header, title, description);
        return card;
    }
}
